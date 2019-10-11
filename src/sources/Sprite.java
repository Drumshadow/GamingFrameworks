package sources;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;

public class Sprite {

    private String spritePath;
    private BufferedImage sprite;

    // dimensions of sprite
    private int width;
    private int height;

    private Texture texture;

    // default is visible
    private boolean isVisible;

    /*==================================================
                     Initialization
    ==================================================*/

    // copy constructor
    public Sprite(Sprite s) {

        this.spritePath = s.spritePath;
        this.sprite = s.sprite;

        this.width = s.width;
        this.height = s.height;

        this.isVisible = s.isVisible;

        this.texture = s.texture;
    }

    // value constructor (loads sprite from file)
    // dimensions are set by shrinkSprite
    public Sprite(String path) {

        this.spritePath = path;
        this.sprite = this.loadSprite(path);

        this.isVisible = true;

        this.shrinkSprite();

        if (path.equals(".\\sprites\\grass.png")) {
            this.isVisible = true;
        }

        this.texture = new Texture(this.sprite);
    }

    // value constructor for invisible sprites
    public Sprite(int width, int height) {

        this.spritePath = null;
        this.sprite = null;

        this.isVisible = false;

        this.width = width;
        this.height = height;

        this.texture = null;
    }

    // shrinks sprite down to smallest size (gets rid of empty pixels)
    // sets width and height
    private void shrinkSprite() {

        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        // cycle through each pixel
        for (int x = 0; x < this.sprite.getWidth(); x++) {
            for (int y = 0; y < this.sprite.getHeight(); y++) {

                // get the color of the current pixel
                int color = this.sprite.getRGB(x, y);
                int alpha = (color >> 24) & 0xFF;

                // resize sprite bounds if a non-empty pixel was found
                if (alpha != 0) {

                    xMin = Math.min(x, xMin);
                    xMax = Math.max(x, xMax);
                    yMin = Math.min(y, yMin);
                    yMax = Math.max(y, yMax);
                }
            }
        }

        // create new, smaller sprite
        this.width = xMax - xMin;
        this.height = yMax - yMin;

        BufferedImage smallSprite = new BufferedImage(this.width, this.height,
                BufferedImage.TYPE_INT_ARGB);

        // copy over non-empty pixels from original sprite
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                smallSprite.setRGB(x, y, this.sprite.getRGB(x + xMin,
                        y + yMin));
            }
        }

        // set new sprite
        this.sprite = smallSprite;
    }

    /*==================================================
                        Drawing
    ==================================================*/

    // draws object
    public void drawObject(double x, double y) {
        glPushMatrix();

        glEnable(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, this.texture.id);

        glTranslated(x + (getWidth() / 1000.0), y + (getHeight() / 1000.0), 0);

        glBegin(GL_QUADS);
        {
            glTexCoord2f(1.0f, 0.0f);
            glVertex2f((float)(width / 1000.0), (float)(height / 1000.0));

            glTexCoord2f(1.0f, 1.0f);
            glVertex2f((float)(width / 1000.0), -(float)(height / 1000.0));

            glTexCoord2f(0.0f, 1.0f);
            glVertex2f(-(float)(width / 1000.0), -(float)(height / 1000.0));

            glTexCoord2f(0.0f, 0.0f);
            glVertex2f(-(float)(width / 1000.0), (float)(height / 1000.0));
        }
        glEnd();

        glDisable(GL_TEXTURE_2D);

        glPopMatrix();
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // gets sprite from given path
    public BufferedImage loadSprite(String spritePath) {

        // get sprite from file
        try {
            return ImageIO.read(new File(this.spritePath));

        } catch (IOException e) {
            System.out.println("ERROR: bad path to sprite image");
            return null;
        }
    }

    public void setSpritePath(String path) {
        this.spritePath = path;

        // update sprite
        this.sprite = this.loadSprite(path);
        this.shrinkSprite();
    }

    public String getSpritePath() {
        return this.spritePath;
    }

    public void setSprite(BufferedImage i) {
        this.sprite = i;

        // shrink sprite
        this.shrinkSprite();
    }

    public BufferedImage getSprite() {
        return this.sprite;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setVisible(boolean v) {
        this.isVisible = v;
    }

    public boolean getVisible() {
        return this.isVisible;
    }
}
