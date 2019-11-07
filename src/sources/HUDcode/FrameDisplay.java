package sources.HUDcode;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

public class FrameDisplay extends HUDElement {

    private int frameCount;

    public FrameDisplay(float x, float y, String name) {
        super(x, y, name);
    }

    @Override
    public void drawElement() {

        glPushMatrix();
            GL11.glOrtho(0, 1000, 1000, 0, -100, 100);
            HUD.hudFont.drawString(this.x, this.y, "Frames: " + this.frameCount);
        glPopMatrix();
    }

    public void setFrameCount(int f) {
        this.frameCount = f;
    }
}