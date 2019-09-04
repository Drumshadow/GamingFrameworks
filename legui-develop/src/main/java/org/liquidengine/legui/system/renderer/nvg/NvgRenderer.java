package org.liquidengine.legui.system.renderer.nvg;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetInteger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.style.Border;
import org.liquidengine.legui.style.font.Font;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.AbstractRenderer;
import org.liquidengine.legui.system.renderer.BorderRenderer;
import org.liquidengine.legui.system.renderer.ComponentRenderer;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL30;

/**
 * Created by ShchAlexander on 1/26/2017.
 */
public class NvgRenderer extends AbstractRenderer {

    public static final String NVG_CONTEXT = "NVG_CONTEXT";
    public static final String IMAGE_REFERENCE_MANAGER = "IMAGE_REFERENCE_MANAGER";
    protected Map<String, Font> loadedFonts = new ConcurrentHashMap<>();
    private long nvgContext;
    private NvgLoadableImageReferenceManager imageReferenceManager;
    private boolean isVersionNew;

    /**
     * Used to render border.
     *
     * @param component component for which should be rendered border.
     * @param context context.
     */
    public static void renderBorder(Component component, Context context) {
        Border border = component.getStyle().getBorder();
        if (border != null && border.isEnabled()) {
            // Render border
            BorderRenderer borderRenderer = RendererProvider.getInstance().getBorderRenderer(border.getClass());
            borderRenderer.render(border, component, context);
        }
    }

    /**
     * Used to render border with scissor.
     *
     * @param component component for which should be rendered border.
     * @param context context.
     * @param nanovg nanovg context.
     */
    public static void renderBorderWScissor(Component component, Context context, long nanovg) {
        NvgRenderUtils.createScissor(nanovg, component);
        {
            renderBorder(component, context);
        }
        NvgRenderUtils.resetScissor(nanovg);
    }

    /**
     * Used to render icon of component.
     *
     * @param icon icon to render.
     * @param component icon owner.
     * @param context context.
     */
    public static void renderIcon(Icon icon, Component component, Context context) {
        if (icon != null && component != null) {
            RendererProvider.getInstance().getIconRenderer(icon.getClass()).render(icon, component, context);
        }
    }

    /**
     * Used to render image of component.
     *
     * @param image image to render.
     * @param position image position.
     * @param size image size.
     * @param context context.
     */
    public static void renderImage(Image image, Vector2fc position, Vector2fc size, Map<String, Object> properties, Context context) {
        if (image != null) {
            RendererProvider.getInstance().getImageRenderer(image.getClass()).render(image, position, size, properties, context);
        }
    }

    @Override
    public void initialize() {
        isVersionNew = (glGetInteger(GL30.GL_MAJOR_VERSION) > 3) || (glGetInteger(GL30.GL_MAJOR_VERSION) == 3 && glGetInteger(GL30.GL_MINOR_VERSION) >= 2);

        if (isVersionNew) {
            int flags = NanoVGGL3.NVG_STENCIL_STROKES | NanoVGGL3.NVG_ANTIALIAS;
            nvgContext = NanoVGGL3.nvgCreate(flags);
        } else {
            int flags = NanoVGGL2.NVG_STENCIL_STROKES | NanoVGGL2.NVG_ANTIALIAS;
            nvgContext = NanoVGGL2.nvgCreate(flags);
        }
        imageReferenceManager = new NvgLoadableImageReferenceManager();
        RendererProvider.getInstance().getComponentRenderers().forEach(ComponentRenderer::initialize);
    }

    private void loadFontsToNvg() {
        Map<String, Font> fontRegister = FontRegistry.getFontRegister();
        for (Map.Entry<String, Font> fontDataEntry : fontRegister.entrySet()) {
            String fontName = fontDataEntry.getKey();
            Font font = fontDataEntry.getValue();
            if (loadedFonts.get(fontName) == null || !loadedFonts.get(fontName).equals(font)) {
                nvgCreateFontMem(nvgContext, fontName, fontDataEntry.getValue().getData(), 0);
                loadedFonts.put(fontName, font);
            }
        }
    }

    @Override
    protected void preRender(Context context) {
        loadFontsToNvg();
        context.getContextData().put(NVG_CONTEXT, nvgContext);
        context.getContextData().put(IMAGE_REFERENCE_MANAGER, imageReferenceManager);

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Vector2i windowSize = context.getWindowSize();
        nvgBeginFrame(nvgContext, windowSize.x, windowSize.y, context.getPixelRatio());
    }

    @Override
    protected void postRender(Context context) {
        nvgEndFrame(nvgContext);

        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);

        imageReferenceManager.removeOldImages(nvgContext);
        context.getContextData().remove(NVG_CONTEXT, nvgContext);
        context.getContextData().remove(IMAGE_REFERENCE_MANAGER, imageReferenceManager);
    }

    @Override
    public void destroy() {
        if (isVersionNew) {
            NanoVGGL3.nnvgDelete(nvgContext);
        } else {
            NanoVGGL2.nnvgDelete(nvgContext);
        }
        RendererProvider.getInstance().getComponentRenderers().forEach(ComponentRenderer::destroy);
        imageReferenceManager.destroy();
    }
}
