package sources.objCode;

import sources.GameRoom;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

// represents generic object
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
    private double terminalV;

    private boolean canCollide;

    // boxCode is the type of hit box
    // 0 = square, 1 = round
    private int boxCode;
    private ObjectBox hitBox;

    /*==================================================
                     Initialization
    ==================================================*/

    // generic constructor
    public GameObject() {
        this.objName = "Empty Object";

        this.spritePath = null;
        this.sprite = null;

        this.width = 0;
        this.height = 0;

        this.canCollide = false;
        this.gravityFactor = 0.0;
        this.terminalV = 0.0;

        // default hit box is square
        this.boxCode = 0;
        this.hitBox = new BoxyBox();
        this.hitBox.createBoundingBox(this.width, this.height);
    }

    // copy constructor
    public GameObject(GameObject other) {
        this.objName = other.objName;

        this.spritePath = other.spritePath;
        this.sprite = other.sprite;

        this.width = other.width;
        this.height = other.height;

        this.canCollide = other.canCollide;
        this.gravityFactor = other.gravityFactor;
        this.terminalV = other.terminalV;

        // create hit box
        this.boxCode = other.boxCode;

        if (other.boxCode == 1) {
            this.hitBox = new RoundBox(other.hitBox);
        }
        else {
            this.hitBox = new BoxyBox(other.hitBox);
        }

        this.hitBox.createBoundingBox(other.width, other.height);
    }

    // constructor via given values
    public GameObject(String name, String sprPath, int w, int h,
                      double gravity, boolean collide, double tv,
                      int boxType) {

        this.objName = name;
        this.spritePath = sprPath;

        // get sprite from file
        try {
            this.sprite = ImageIO.read(new File(this.spritePath));
        } catch (IOException e) {
            System.out.println("ERROR: bad path to sprite image");
            this.sprite = null;
        }

        this.width = w;
        this.height = h;
        this.gravityFactor = gravity;
        this.canCollide = collide;
        this.terminalV = tv;

        // create hit box
        this.boxCode = boxType;

        if (this.boxCode == 1) {
            this.hitBox = new RoundBox();
        }
        else {
            this.hitBox = new BoxyBox();
        }

        this.hitBox.createBoundingBox(this.width, this.height);
    }

    //TODO: Make sure this does not suck, I added this to get some other stuff working but may need to be fixed.
    public GameObject(String name, String path, int w, int h, double gravity, boolean collide) {
        this.objName = name;
        this.spritePath = path;

        // get sprite from file
        try {
            this.sprite = ImageIO.read(new File(this.spritePath));
        } catch (IOException e) {
            System.out.println("ERROR: bad path to sprite image");
            this.sprite = null;
        }

        this.width = w;
        this.height = h;
        this.gravityFactor = gravity;
        this.canCollide = collide;

        if (this.boxCode == 1) {
            this.hitBox = new RoundBox();
        }
        else {
            this.hitBox = new BoxyBox();
        }

        this.hitBox.createBoundingBox(this.width, this.height);
    }

    /*==================================================
                        Drawing
    ==================================================*/

    // sets object's sprite and saves to file within game engine
    public void loadSprite(String spritePath) {
        this.spritePath = spritePath;
        // get sprite from file
        try {
            this.sprite = ImageIO.read(new File(this.spritePath));
        } catch (IOException e) {
            System.out.println("ERROR: bad path to sprite image");
            this.sprite = null;
        }
    }

    // draws object
    public void drawObject() {
        Graphics2D g2d = sprite.createGraphics();
        g2d.setBackground(Color.red);
        g2d.fill(new Ellipse2D.Float(0, 0, 200, 100));
        //g2d.dispose();
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

        // acceleration due to gravity
        if (gravityFactor != 0.0 && this.hitBox.ySpeed < this.terminalV) {
            this.hitBox.ySpeed += (GameRoom.GRAVITY * this.gravityFactor);
        }

        // check collision
        if (this.canCollide) {

            for (GameObject other : roomObjs.getOList()) {

                // don't collide with self
                if (this.equals(other))
                    continue;

                // test future horizontal collision
                if (this.hitBox.xCollisionCheck(other.hitBox)) {

                    // move up to object without actually colliding
                    this.hitBox.xSpeed = Math.signum(
                            this.hitBox.objDistX(other.hitBox));

                }

                // test future vertical collision
                if (this.hitBox.yCollisionCheck(other.hitBox)) {

                    // move up to object without actually colliding
                    this.hitBox.ySpeed = Math.signum(
                            this.hitBox.objDistY(other.hitBox));
                }

                // test future diagonal collision
                if (this.hitBox.diagCollisionCheck(other.hitBox)) {

                    // move up to object from x direction
                    this.hitBox.xSpeed = Math.signum(
                            this.hitBox.objDistX(other.hitBox));

                    // move up to object from y direction
                    this.hitBox.ySpeed = Math.signum(
                            this.hitBox.objDistY(other.hitBox));
                }
            }
        }

        // update position
        this.hitBox.updatePosition();
    }

    public void objectJump() {

        // check if colliding on bottom
        // set vertical speed to -jumpspeed
        // TODO: jump
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // TODO: add value validation
    // TODO: add updates to all attrib affected by change
    // TODO: add box type setter

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

    public void setTerminalV(double v) {
        this.terminalV = v;
    }

    public double getTerminalV() {
        return this.terminalV;
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