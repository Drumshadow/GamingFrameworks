/*
 * Main Author: Ashley Roesler
 */

package sources;

import java.awt.geom.Rectangle2D;
import java.util.Objects;

// Contains all information specific to one instance of an object
public class ObjectBox {

    // position based on top-left corner
    private double x;
    private double y;

    private double xSpeed;
    private double ySpeed;

    private Rectangle2D.Double boundBox;

    // TODO: add acceleration and max speeds??
    // TODO: add circle / custom hit boxes??

    /*==================================================
                     Initialization
    ==================================================*/

    public ObjectBox() {

        this.x = 0.0;
        this.y = 0.0;

        this.xSpeed = 0.0;
        this.ySpeed = 0.0;

        this.boundBox = null;
    }

    ObjectBox(ObjectBox ob) {
        this.x = ob.x;
        this.y = ob.y;

        this.xSpeed = ob.xSpeed;
        this.ySpeed = ob.ySpeed;

        this.boundBox = ob.boundBox;
    }

    // creates bounding box
    public void createBoundingBox(double w, double h) {
        this.boundBox = new Rectangle2D.Double(this.x, this.y, w, h);
    }

    /*==================================================
                        Collision
    ==================================================*/

    // checks if two bounding boxes have collided
    public Boolean basicCollisionCheck(ObjectBox other) {
        return this.boundBox.intersects(other.boundBox);
    }

    // checks future collision in x direction
    public boolean xCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Rectangle2D.Double futureBox = new Rectangle2D.Double(
                this.x + this.xSpeed, this.y, this.boundBox.width,
                this.boundBox.height);

        return futureBox.intersects(other.boundBox);
    }

    // checks future collision in y direction
    public boolean yCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Rectangle2D.Double futureBox = new Rectangle2D.Double(this.x,
                this.y + this.ySpeed, this.boundBox.width,
                this.boundBox.height);

        return futureBox.intersects(other.boundBox);
    }

    // checks future collision in both x and y directions
    public boolean futureCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Rectangle2D.Double futureBox = new Rectangle2D.Double(
                this.x + this.xSpeed, this.y + this.ySpeed,
                this.boundBox.width, this.boundBox.height);

        return futureBox.intersects(other.boundBox);
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // updates position and bounding box
    public void updatePosition() {

        // update position
        this.x += xSpeed;
        this.y += ySpeed;

        // update hit box
        this.boundBox.x = this.x;
        this.boundBox.y = this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

    public void setXSpeed(double s) {
        this.xSpeed = s;
    }

    public double getXSpeed() {
        return this.xSpeed;
    }

    public void setYSpeed(double s) {
        this.ySpeed = s;
    }

    public double getYSpeed() {
        return this.ySpeed;
    }

    public void setBoundBox(Rectangle2D.Double b) {
        this.boundBox = b;
    }

    public Rectangle2D.Double getBoundBox() {
        return this.boundBox;
    }

    /*==================================================
                      Miscellaneous
    ==================================================*/

    // generates object's hashcode for equality check
    public int hashcode() {
        return Objects.hash(this.x, this.y, this.xSpeed, this.ySpeed, this.boundBox);
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
