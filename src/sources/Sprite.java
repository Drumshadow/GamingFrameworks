package sources;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;

public class Sprite {

    private String spritePath;
    private BufferedImage[] sprite;

    // dimensions of sprite
    private int width;
    private int height;

    private int frames;

    private int pointer;

    private Texture[] animation;

    // default is visible
    private boolean isVisible;

    private double elapsedTime;
    private double currentTime;
    private double lastTime;
    private double fps;

    /*==================================================
                     Initialization
    ==================================================*/

    // copy constructor
    public Sprite(Sprite s) {

        this.spritePath = s.spritePath;
        this.sprite = s.sprite;

        this.width = s.width;
        this.height = s.height;

        this.frames = s.frames;

        this.pointer = s.pointer;

        this.elapsedTime = s.elapsedTime;
        this.currentTime = s.currentTime;
        this.lastTime = s.lastTime;
        this.fps = s.fps;

        this.isVisible = s.isVisible;

        this.animation = s.animation;
    }

    // value constructor (loads sprite from file)
    // dimensions are set by shrinkSprite
    public Sprite(String path, int frames) {

        this.spritePath = path;
        this.sprite = new BufferedImage[frames];
        this.animation = new Texture[frames];

        this.frames = frames;
        this.pointer = 0;
        this.isVisible = true;

        this.elapsedTime = 0;
        this.currentTime = 0;
        this.lastTime = FPS.getTime();
        this.fps = 1.0 / frames;
        System.out.println(this.fps);

        this.loadAnimation(this.frames);
    }

    // value constructor for invisible sprites
    public Sprite(int width, int height) {

        this.spritePath = null;
        this.sprite = null;

        this.frames = 1;
        this.pointer = 0;

        this.isVisible = false;

        this.elapsedTime = 0;
        this.currentTime = 0;
        this.lastTime = FPS.getTime();
        this.fps = 1.0/60;

        this.width = width;
        this.height = height;

        this.animation = null;
    }

    private void loadAnimation(int frames) {
        if(frames == 1) {
            this.sprite[0] = this.loadSprite(spritePath + ".png");
            this.shrinkSprite(0);
            this.animation[0] = new Texture(this.sprite[0]);
        } else {
            for (int i = 0; i < frames; i++) {
                this.sprite[i] = this.loadSprite(spritePath + "-" + i +
                        ".png");
                this.shrinkSprite(i);
                this.animation[i] = new Texture(this.sprite[i]);
            }
        }
    }

    // shrinks sprite down to smallest size (gets rid of empty pixels)
    // sets width and height
    private void shrinkSprite(int i) {

        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        // cycle through each pixel
        for (int x = 0; x < this.sprite[i].getWidth(); x++) {
            for (int y = 0; y < this.sprite[i].getHeight(); y++) {

                // get the color of the current pixel
                int color = this.sprite[i].getRGB(x, y);
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
        this.width = Math.max(this.width, xMax - xMin);
        this.height = Math.max(this.height, yMax - yMin);

        BufferedImage smallSprite = new BufferedImage(xMax - xMin,
                yMax - yMin, BufferedImage.TYPE_INT_ARGB);

        // copy over non-empty pixels from original sprite
        for (int x = 0; x < xMax - xMin; x++) {
            for (int y = 0; y < yMax - yMin; y++) {
                smallSprite.setRGB(x, y, this.sprite[i].getRGB(x + xMin,
                        y + yMin));
            }
        }

        // set new sprite
        this.sprite[i] = smallSprite;
    }

    /*==================================================
                        Drawing
    ==================================================*/

    // draws object
    public void drawObject(double x, double y) {
        glPushMatrix();

        this.currentTime = FPS.getTime();
        this.elapsedTime += currentTime - lastTime;

        if(elapsedTime >= fps) {
            elapsedTime -= fps;
            pointer++;
        }

        if(pointer >= frames) {
            pointer = 0;
        }

        this.lastTime = currentTime;

        glEnable(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, this.animation[pointer].id);

        glTranslated(x + (width / 1000.0),
                y + (height / 1000.0), 0);

        glBegin(GL_QUADS);
        {
            glTexCoord2f(1.0f, 0.0f);
            glVertex2f((float)(width / 1000.0),
                    (float)(height / 1000.0));

            glTexCoord2f(1.0f, 1.0f);
            glVertex2f((float)(width / 1000.0),
                    -(float)(height / 1000.0));

            glTexCoord2f(0.0f, 1.0f);
            glVertex2f(-(float)(width / 1000.0),
                    -(float)(height / 1000.0));

            glTexCoord2f(0.0f, 0.0f);
            glVertex2f(-(float)(width / 1000.0),
                    (float)(height / 1000.0));
        }
        glEnd();

        glDisable(GL_TEXTURE_2D);

        glPopMatrix();
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // gets sprite from given path
    private BufferedImage loadSprite(String spritePath) {

        // get sprite from file
        try {
            return ImageIO.read(new File(spritePath));

        } catch (IOException e) {
            System.out.println("ERROR: bad path to sprite image");
            return null;
        }
    }

    public void setSpritePath(String path) {
        this.spritePath = path;

        loadAnimation(this.frames);
    }

    public String getSpritePath() {
        return this.spritePath;
    }

    public void setSprite(BufferedImage[] i) {
        this.sprite = i;

        // shrink sprite
        for(int j = 0; j < this.sprite.length; j++) {
            this.shrinkSprite(j);
        }
    }

    public BufferedImage[] getSprite() {
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