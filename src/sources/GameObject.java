/*
 * author: Ashley Roesler
 */

import java.awt.Rectangle;
import java.util.Objects;

// represents a generic game object
public class GameObject {

    private String objName;

    private String spritePath;

    private boolean hasGravity;
    private boolean canCollide;

    // TODO: decide in int or float

    // position based on top-left corner
    private int x;
    private int y;

    // dimensions of hit box
    private int width;
    private int height;

    private int xSpeed;
    private int ySpeed;

    // TODO: add acceleration and max speeds

    private Rectangle boundBox;
    // TODO: add circle hit box?
    // TODO: add custom hit box?

    /*==================================================
                     Initialization
    ==================================================*/

    public GameObject() {
        this.objName = "Empty Object";
        this.spritePath = null;

        // TODO: default sprite when none is given?

        this.hasGravity = false;
        this.canCollide = false;

        this.x = 0;
        this.y = 0;

        this.width = 0;
        this.height = 0;

        this.xSpeed = 0;
        this.ySpeed = 0;

        this.boundBox = null;
    }

    // TODO: non generic constructor?

    // sets object's sprite
    // also crops sprite, making sure there is no empty space around the sprite
    public void loadSprite(String spritePath) {


    }

    // TODO: create bounding box
    // TODO: load sprite
    // TODO: draw

    /*==================================================
                        Movement
    ==================================================*/

    // moves objects and performs collision detection
    public void move(GameObject[] objs) {

        // check collision
        if (this.canCollide) {

            for (GameObject o : objs) {

                // don't collide with self and check if can collide
                if (this.equals(o))
                    continue;

                // check if o can collide
                if (!o.canCollide)
                    continue;


            }
        }

        // move if able

    }

    // TODO: jump
    // TODO: falling

    /*==================================================
                        Collision
    ==================================================*/

    // checks if two bounding boxes have collided
    public Boolean basicCollisionCheck(GameObject other) {

        // make sure objects can be collided with
        if (!this.canCollide || !other.canCollide)
            return false;

        return this.boundBox.intersects(other.boundBox);
    }

    // checks future collision in x direction
    public boolean xCollisionCheck(GameObject other) {

        // make temporary bounding box for future position
        Rectangle futureBox = new Rectangle(this.x + this.xSpeed,
                this.y, this.width, this.height);

        return futureBox.intersects(other.boundBox);
    }

    // checks future collision in y direction
    public boolean yCollisionCheck(GameObject other) {

        // make temporary bounding box for future position
        Rectangle futureBox = new Rectangle(this.x,
                this.y + this.ySpeed, this.width, this.height);

        return futureBox.intersects(other.boundBox);
    }

    // checks future collision in both x and y directions
    public boolean futureCollisionCheck(GameObject other) {

        // make temporary bounding box for future position
        Rectangle futureBox = new Rectangle(this.x + this.xSpeed,
                this.y + this.ySpeed, this.width, this.height);

        return futureBox.intersects(other.boundBox);
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // TODO: add value validation

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

    public void setHasGravity(boolean g) {
        this.hasGravity = g;
    }

    public boolean getHasGravity() {
        return this.hasGravity;
    }

    public void setCanCollide(boolean c) {
        this.canCollide = c;
    }

    public boolean getCanCollide() {
        return this.canCollide;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
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

    public void setXSpeed(int s) {
        this.xSpeed = s;
    }

    public int getXSpeed() {
        return this.xSpeed;
    }

    public void setYSpeed(int s) {
        this.ySpeed = s;
    }

    public int getYSpeed() {
        return this.ySpeed;
    }

    public void setBoundBox(Rectangle b) {
        this.boundBox = b;
    }

    public Rectangle getBoundBox() {
        return this.boundBox;
    }

    /*==================================================
                      Miscellaneous
    ==================================================*/

    // generates object's hashcode for equality check
    public int hashcode() {
        return Objects.hash(this.objName, this.spritePath, this.hasGravity,
                this.canCollide, this.x, this.y, this.width, this.height,
                this.xSpeed, this.ySpeed, this.boundBox);
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