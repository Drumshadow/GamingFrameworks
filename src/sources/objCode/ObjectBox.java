package sources.objCode;

import java.awt.geom.RectangularShape;
import java.util.Objects;

abstract public class ObjectBox {

    // position based on top-left corner
    protected double x;
    protected double y;

    double xSpeed;
    double ySpeed;

    // TODO: add acceleration and max speeds??

    /*==================================================
                     Initialization
    ==================================================*/

    // generic constructor
    ObjectBox() {

        this.x = 0.0;
        this.y = 0.0;

        this.xSpeed = 0.0;
        this.ySpeed = 0.0;
    }

    // copy constructor
    ObjectBox(ObjectBox other) {
        this.x = other.x;
        this.y = other.y;

        this.xSpeed = other.xSpeed;
        this.ySpeed = other.ySpeed;
    }

    // creates bounding box
    abstract public void createBoundingBox(double w, double h);

    /*==================================================
                        Collision
    ==================================================*/

    // checks if two bounding boxes have collided
    abstract public boolean basicCollisionCheck(ObjectBox other);

    // checks future collision in x direction
    abstract public boolean xCollisionCheck(ObjectBox other);

    // checks future collision in y direction
    abstract public boolean yCollisionCheck(ObjectBox other);

    // checks future collision in both x and y directions
    abstract public boolean diagCollisionCheck(ObjectBox other);

    // checks horizontal distance between two hit boxes
    abstract public double objDistX(ObjectBox other);

    // checks vertical distance between two hit boxes
    abstract public double objDistY(ObjectBox other);

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // updates position
    public void updatePosition() {

        // update position
        this.x += xSpeed;
        this.y += ySpeed;
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

    abstract public void setBoundBox(RectangularShape s);
    abstract public RectangularShape getBoundBox();

    /*==================================================
                      Miscellaneous
    ==================================================*/

    // generates object's hashcode for equality check
    public int hashcode() {
        return Objects.hash(this.x, this.y, this.xSpeed, this.ySpeed);
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
