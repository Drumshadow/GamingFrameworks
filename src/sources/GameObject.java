/*
 * Main Author: Ashley Roesler
 */

package sources;

import sources.ObjectBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

// contains info common across all instances of an object
public class GameObject {

    private String objName;

    private String spritePath;
    private BufferedImage sprite;

    // TODO: invisible objects
    // TODO: default sprite when none is given?

    // dimensions of sprite
    private int width;
    private int height;

    // no gravity is gravityFactor = 0
    // controls "weight" of object
    private double gravityFactor;

    private boolean canCollide;

    private ObjectBox hitBox;

    /*==================================================
                     Initialization
    ==================================================*/

    public GameObject() {
        this.objName = "Empty Object";

        this.spritePath = null;
        this.sprite = null;

        this.width = 0;
        this.height = 0;

        this.canCollide = false;

        this.gravityFactor = 0.0;

        this.hitBox = new ObjectBox();
        this.hitBox.createBoundingBox(this.width, this.height);
    }

    // TODO: non generic constructor?

    public GameObject(String objName, String spritePath, int width, int height, double gravityFactor, boolean canCollide) {
        this.objName = objName;
        this.spritePath = spritePath;
        try {
            this.sprite = ImageIO.read(new File(this.spritePath));
        } catch (IOException e) {
            System.out.println("ERROR: bad path to sprite image");
            this.sprite = null;
        }
        this.width = width;
        this.height = height;
        this.gravityFactor = gravityFactor;
        this.canCollide = canCollide;
        this.hitBox = new ObjectBox();
        this.hitBox.createBoundingBox(this.width, this.height);
    }


    /*==================================================
                        Drawing
    ==================================================*/

    // sets object's sprite and saves to file within game engine
    public void loadSprite(String spritePath) {

    }

    // draws object
    public void drawObject() {

    }

    // shrinks sprite down to smallest size (gets rid of empty space)
    // sets width and height
    public void shrinkSprite() {

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
                        Movement
    ==================================================*/

    // moves objects and performs collision detection
    public void move(ObjectList roomObjs) {

        // check collision
        if (this.canCollide) {
            // TODO: do collision stuff
        }

        // update position
        this.hitBox.updatePosition();

        // TODO: jump
        // TODO: falling

/*
        // check collision
        if (this.canCollide) {

            boolean xCollide;
            boolean yCollide;

            for (GameObject o : objs) {

                // don't collide with self and check if can collide
                if (this.equals(o) || !o.canCollide)
                    continue;

                // check x collision
                xCollide = this.xCollisionCheck(o);

                // check y collision
                yCollide = this.yCollisionCheck(o);

                // check both directions (ran into corner)
                if (!xCollide && !yCollide) {

                    if (this.futureCollisionCheck(o)) {

                        // continue in the faster direction
                        xCollide = ySpeed > xSpeed;
                        yCollide = !xCollide;
                    }
                }

                if (xCollide)
                    xSpeed = 0;

                if (yCollide)
                    ySpeed = 0;

            }
        }*/
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // TODO: add value validation
    // TODO: add updates to all attrib affected by change

    public void setObjName(String o) {
        this.objName = o;
    }

    public String getObjName() {
        return this.objName;
    }

    public void setSpritePath(String s) {
        this.spritePath = s;
    }

    public String getSpritePath() {
        return this.spritePath;
    }

    public void setSprite(BufferedImage s) {
        this.sprite = s;
    }

    public BufferedImage getSprite() {
        return this.sprite;
    }

    public void setGravityFactor(double g) {
        this.gravityFactor = g;
    }

    public double getGravityFactor() {
        return this.gravityFactor;
    }

    public void setCanCollide(boolean c) {
        this.canCollide = c;
    }

    public boolean getCanCollide() {
        return this.canCollide;
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

    /*==================================================
                      Miscellaneous
    ==================================================*/

    // generates object's hashcode for equality check
    public int hashcode() {
        return Objects.hash(this.objName, this.spritePath, this.sprite,
                this.gravityFactor, this.canCollide, this.width, this.height,
                this.hitBox);
    }

    // checks if two objects are the same
    public boolean equals(Object other) {

        // Check if they are the same object
        if (this == other)
            return true;

        // Check if null or if same class
        if (other == null || this.getClass() != other.getClass())
            return false;

        // Check class members
        return (this.hashcode() == other.hashCode());
    }
}