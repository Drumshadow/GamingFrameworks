package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgColorUtil;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;

import java.nio.ByteBuffer;
import java.util.Map;

import static org.liquidengine.legui.style.color.ColorUtil.oppositeBlackOrWhite;
import static org.liquidengine.legui.style.util.StyleUtilities.getInnerContentRectangle;
import static org.liquidengine.legui.style.util.StyleUtilities.getPadding;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by ShchAlexander on 13.02.2017.
 */
public class NvgTextInputRenderer extends NvgDefaultComponentRenderer<TextInput> {

    public static final String PRATIO = "pratio";
    public static final String PALIGN = "palign";
    public static final String POFFSET = "poffset";
    private final Vector4f caretColor = new Vector4f(0, 0, 0, 0.5f);
    private final int maxGlyphCount = 1024;


    /**
     * Used to render textInput.
     *
     * @param component textInput to render.
     * @param context legui context.
     * @param nanovg nanovg context pointer.
     */
    @Override
    protected void renderSelf(TextInput component, Context context, long nanovg) {
        createScissor(nanovg, component);
        {
            Vector2f pos = component.getAbsolutePosition();
            Vector2f size = component.getSize();
            boolean enabled = component.isEnabled();
            Style style = component.getStyle();
            Vector4f bc = new Vector4f(style.getBackground().getColor());

            if (enabled && component.isFocused()) {
                bc.w *= 1.1f;
            } else if (!enabled) {
                bc.w *= 0.5f;
            }
            if (!component.isEditable()) {
                bc.w *= 0.3f;
            }
            renderBackground(component, context, nanovg);

            Vector4f padding = getPadding(component, style);
            Vector4f intersectRect = getInnerContentRectangle(pos, size, padding);
            intersectScissor(nanovg, new Vector4f(intersectRect).sub(1, 1, -2, -2));
            renderText(context, nanovg, component, size, intersectRect, bc);
        }
        resetScissor(nanovg);
    }

    private void renderText(Context leguiContext, long context, TextInput gui, Vector2f size, Vector4f rect, Vector4f bc) {

        try (
                NVGGlyphPosition.Buffer glyphs = NVGGlyphPosition.calloc(maxGlyphCount);
                NVGColor colorA = NVGColor.calloc()
        ) {
            TextState textState = gui.getTextState();
            String text = textState.getText();
            String font = textState.getFont();
            float fontSize = textState.getFontSize();
            Vector4f highlightColor = textState.getHighlightColor();
            HorizontalAlign halign = textState.getHorizontalAlign();
            VerticalAlign valign = textState.getVerticalAlign();
            Vector4f textColor = textState.getTextColor();
            int caretPosition = gui.getCaretPosition();
            Map<String, Object> metadata = gui.getMetadata();
            int startSelectionIndex = gui.getStartSelectionIndex();
            int endSelectionIndex = gui.getEndSelectionIndex();
            boolean focused = gui.isFocused();
            if (focused) {
                updateCaret(bc);
            }
            if (text == null || text.isEmpty()) {

                if (focused) {
                    // render caret
                    float nCaretX = rect.x + halign.index * rect.z / 2f;
                    NvgColorUtil.fillNvgColorWithRGBA(caretColor, colorA);
                    renderCaret(context, rect, nCaretX, colorA);
                }

                gui.setMouseCaretPosition(0);
                return;
            } else {

                // initially configure text rendering
                NvgColorUtil.fillNvgColorWithRGBA(textColor, colorA);
                alignTextInBox(context, halign, valign);
                nvgFontSize(context, fontSize);
                nvgFontFace(context, font);
                nvgFillColor(context, colorA);

                if (!focused) {
                    caretPosition = (halign == HorizontalAlign.LEFT ? 0 : (halign == HorizontalAlign.RIGHT ? text.length() : text.length() / 2));
                }

                float[] textBounds = calculateTextBoundsRect(context, rect, text, halign, valign, fontSize);

                // calculate caret coordinate and mouse caret coordinate
                float caretx;
                float startSelectionX;
                float endSelectionX;
                float mouseCaretX = 0;
                int mouseCaretPosition = 0;
                float ratio = size.y * size.x;
                ByteBuffer textBytes = null;
                try {
                    // allocate ofheap memory and fill it with text
                    textBytes = memUTF8(text);

                    // align text for calculations
                    alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
                    int ng = nnvgTextGlyphPositions(context, textBounds[4], 0, memAddress(textBytes), 0, memAddress(glyphs), maxGlyphCount);

                    // get caret position on screen based on caret position in text
                    // and get x position of first and last selection
                    caretx = calculateCaretPos(caretPosition, textBounds, ng, glyphs);
                    startSelectionX = calculateCaretPos(startSelectionIndex, textBounds, ng, glyphs);
                    endSelectionX = calculateCaretPos(endSelectionIndex, textBounds, ng, glyphs);

                    // calculate text offset in text field based on caret position on screen
                    // (caret always should be inside text field bounds)
                    float offsetX = getOffsetX(rect, caretx);

                    // get previous offset
                    Float poffset = (Float) metadata.getOrDefault(POFFSET, offsetX);

                    // get previous ratio
                    Float pratio = (Float) metadata.getOrDefault(PRATIO, ratio);

                    // get previous align to know if we need to recalculate offset
                    HorizontalAlign palign = (HorizontalAlign) metadata.getOrDefault(PALIGN, halign);

                    // we should recalculate offsets if ratio is changed
                    poffset = recalculateOffsetX(rect, halign, caretx, ratio, offsetX, poffset, pratio, palign);

                    // calculate mouse caret position
                    if (text.length() == 0) {
                        mouseCaretX = caretx;
                    } else {
                        float mx = Mouse.getCursorPosition().x + poffset;
                        if (mx <= glyphs.get(0).x()) {
                            mouseCaretPosition = 0;
                            mouseCaretX = glyphs.get(0).x();
                        } else if (mx >= glyphs.get(ng - 1).maxx()) {
                            mouseCaretPosition = ng;
                            mouseCaretX = glyphs.get(ng - 1).maxx();
                            // if window not minimized
                        } else if (!leguiContext.isIconified()) {
                            // binary search mouse caret position
                            int upper = ng;
                            int lower = 0;
                            boolean found = false;
                            do {
                                int index = (upper + lower) / 2;
                                float left = index == 0 ? glyphs.get(index).minx() : glyphs.get(index).x();
                                float right = index >= ng - 1 ? glyphs.get(ng - 1).maxx() : glyphs.get(index + 1).x();
                                float mid = (left + right) / 2f;
                                if (mx >= left && mx < right) {
                                    found = true;
                                    if (mx > mid) {
                                        mouseCaretPosition = index + 1;
                                        mouseCaretX = right;
                                    } else {
                                        mouseCaretPosition = index;
                                        mouseCaretX = left;
                                    }
                                } else if (mx >= right) {
                                    if (index != ng) {
                                        lower = index + 1;
                                    } else {
                                        found = true;
                                        mouseCaretPosition = ng;
                                        mouseCaretX = right;
                                    }
                                } else if (mx < left) {
                                    if (index != 0) {
                                        upper = index;
                                    } else {
                                        found = true;
                                        mouseCaretPosition = 0;
                                        mouseCaretX = left;
                                    }
                                }
                            } while (!found);
                        }
                    }
                    mouseCaretX -= poffset;
                    float nCaretX = caretx - poffset;

                    drawSelection(context, rect, highlightColor,
                                  startSelectionIndex, endSelectionIndex,
                                  focused, startSelectionX, endSelectionX, poffset);
                    // render text

                    NvgText.drawTextLineToRect(context, new Vector4f(textBounds[4] - poffset, textBounds[5], textBounds[6], textBounds[7]),
                                               false, HorizontalAlign.LEFT, VerticalAlign.MIDDLE, fontSize, font, text, textColor);

                    if (focused) {
                        // render caret
                        NvgColorUtil.fillNvgColorWithRGBA(caretColor, colorA);
                        renderCaret(context, rect, nCaretX, colorA);
                    }
                    // render mouse caret
                    if (leguiContext.isDebugEnabled()) {
                        Vector4f cc = new Vector4f(this.caretColor);
                        cc.x = 1;

                        NvgColorUtil.fillNvgColorWithRGBA(cc, colorA);
                        renderCaret(context, rect, mouseCaretX, colorA);
                    }

                    // put last offset and ration to metadata
                    updateMetadata(halign, metadata, ratio, poffset);
                } finally {
                    // free allocated memory
                    memFree(textBytes);
                }
                gui.setMouseCaretPosition(mouseCaretPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMetadata(HorizontalAlign halign, Map<String, Object> metadata, float ratio, Float poffset) {
        metadata.put(POFFSET, poffset);
        metadata.put(PALIGN, halign);
        metadata.put(PRATIO, ratio);
    }

    private void drawSelection(long context, Vector4f rect, Vector4f highlightColor,
                               int startSelectionIndex, int endSelectionIndex, boolean focused,
                               float startSelectionX, float endSelectionX, Float poffset) {
        if (focused && startSelectionIndex != endSelectionIndex) {
            Vector2f position = new Vector2f(startSelectionX - poffset, rect.y);
            Vector2f size = new Vector2f(endSelectionX - startSelectionX, rect.w);
            NvgShapes.drawRect(context, position, size, highlightColor);
        }
    }

    private void updateCaret(Vector4f bc) {
        oppositeBlackOrWhite(bc, caretColor);
        caretColor.w = (float) Math.abs(GLFW.glfwGetTime() % 1 * 2 - 1);
    }

    private Float recalculateOffsetX(Vector4f rect, HorizontalAlign halign,
                                     float caretx, float ratio, float offsetX,
                                     Float poffset, Float pratio, HorizontalAlign palign) {
        float newpoffset = poffset;
        if (pratio != ratio || palign != halign) {
            newpoffset = offsetX;
        } else {
            // and if ratio is the same we should check if we need to update offset
            if (caretx - poffset > rect.z + rect.x) {
                newpoffset = poffset + (caretx - poffset - rect.z - rect.x);
            } else if (caretx - poffset < rect.x) {
                newpoffset = poffset + (caretx - poffset - rect.x);
            }
        }
        return newpoffset;
    }

    private float getOffsetX(Vector4f rect, float caretx) {
        float offsetX = 0;
        if (caretx > rect.z + rect.x) {
            offsetX = caretx - rect.x - rect.z;
        } else if (caretx < rect.x) {
            offsetX = caretx - rect.x;
        }
        return offsetX;
    }

    private void renderCaret(long context, Vector4f rect, float nCaretX, NVGColor rgba) {
        nvgLineCap(context, NVG_ROUND);
        nvgLineJoin(context, NVG_ROUND);
        nvgStrokeWidth(context, 1);
        nvgStrokeColor(context, rgba);
        nvgBeginPath(context);
        nvgMoveTo(context, nCaretX, rect.y);
        nvgLineTo(context, nCaretX, rect.y + rect.w);
        nvgStroke(context);
    }

    private float calculateCaretPos(int caretPosition, float[] textBounds, int ng, NVGGlyphPosition.Buffer glyphs) {
        float caretx = 0;
        if (caretPosition < ng) {
            try {
                caretx = glyphs.get(caretPosition).x();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } else {
            if (ng > 0) {
                caretx = glyphs.get(ng - 1).maxx();
            } else {
                caretx = textBounds[4];
            }
        }
        return caretx;
    }
}
