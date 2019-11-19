package sources.objCode;

import java.awt.geom.RectangularShape;
import java.util.Objects;

abstract public class ObjectBox {

    // position based on top-left corner
    double x;
    double y;

    double xSpeed;
    double ySpeed;

    RectangularShape boundingBox;

    /*==================================================
                     Initialization
    ==================================================*/

    // default constructor
    ObjectBox() {

        this.x = 0.0;
        this.y = 0.0;

        this.xSpeed = 0.0;
        this.ySpeed = 0.0;

        this.boundingBox = null;
    }

    // copy constructor
    ObjectBox(ObjectBox other) {
        this.x = other.x;
        this.y = other.y;

        this.xSpeed = other.xSpeed;
        this.ySpeed = other.ySpeed;

        this.boundingBox = other.boundingBox;
    }

    /*==================================================
                        Collision
    ==================================================*/

    // checks if two bounding boxes have collided
    public boolean basicCollision(ObjectBox other) {
        return this.xCollisionCheck(other) || this.yCollisionCheck(other);
    }

    // checks future collision in x direction
    abstract public boolean xCollisionCheck(ObjectBox other);

    // checks future collision in y direction
    abstract public boolean yCollisionCheck(ObjectBox other);

    // checks future collision in both x and y directions
    abstract public boolean diagCollisionCheck(ObjectBox other);

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // updates position
    public void updatePosition() {

        // update position
        this.x += xSpeed;
        this.y += ySpeed;
    }

    void setX(double x) {
        this.x = x;
        this.boundingBox.setFrame(this.x, this.y,
                this.boundingBox.getWidth(), this.boundingBox.getHeight());
    }

    double getX() {
        return this.x;
    }

    void setY(double y) {
        this.y = y;
        this.boundingBox.setFrame(this.x, this.y,
                this.boundingBox.getWidth(), this.boundingBox.getHeight());
    }

    double getY() {
        return this.y;
    }

    void setXSpeed(double s) {
        this.xSpeed = s;
    }

    double getXSpeed() {
        return this.xSpeed;
    }

    void setYSpeed(double s) {
        this.ySpeed = s;
    }

    double getYSpeed() {
        return this.ySpeed;
    }

    abstract public RectangularShape getBoundBox();

    /*==================================================
                      Miscellaneous
    ==================================================*/

    // checks horizontal distance between two hit boxes
    double objDistX(ObjectBox other) {

        return Math.abs(this.boundingBox.getCenterX() -
                other.boundingBox.getCenterX()) -
                (this.boundingBox.getWidth() + other.boundingBox.getWidth());

    }

    // checks vertical distance between two hit boxes
    double objDistY(ObjectBox other) {

        return Math.abs(this.boundingBox.getCenterY() -
                other.boundingBox.getCenterY()) -
                (this.boundingBox.getHeight() + other.boundingBox.getHeight());
    }

    // generates object's hashcode for equality check
    private int hashcode() {
        return Objects.hash(this.x, this.y, this.xSpeed,
                this.ySpeed, this.boundingBox);
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