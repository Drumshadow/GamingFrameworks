package sources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {

    private String spritePath;
    private BufferedImage sprite;

    // dimensions of sprite
    private int width;
    private int height;

    /*==================================================
                     Initialization
    ==================================================*/

    // TODO: invisible objects
    // TODO: default sprite when none is given?
    // TODO: function that gets size of sprite
    // TODO: stretching sprite to different size?
    // TODO: sprite editor?

    // generic constructor
    public Sprite() {
        this.spritePath = null;
        this.sprite = null;

        this.width = 0;
        this.height = 0;
    }

    // copy constructor
    public Sprite(Sprite s) {

        this.spritePath = s.spritePath;
        this.sprite = s.sprite;

        this.width = s.width;
        this.height = s.height;
    }

    // value constructor (loads sprite from file)
    // dimensions are set by shrinkSprite
    public Sprite(String path) {

        this.spritePath = path;
        this.sprite = this.loadSprite(path);

        this.shrinkSprite();
    }

    // shrinks sprite down to smallest size (gets rid of empty space)
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
    public void drawObject() {
  /*      Graphics2D g2d = sprite.createGraphics();
        g2d.setBackground(Color.red);
        g2d.fill(new Ellipse2D.Float(0, 0, 200, 100));*/
        //g2d.dispose();
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

    public void setWidth(int w) {
        this.width = w;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public int getHeight() {
        return this.height;
    }
}
