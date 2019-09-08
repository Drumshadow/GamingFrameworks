package sources.objCode;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.Objects;

public class BoxyBox extends ObjectBox {

    private Rectangle2D.Double boundBox;

    /*==================================================
                     Initialization
    ==================================================*/

    // default constructor
    BoxyBox() {
        super();
        this.boundBox = null;
    }

    // copy constructor
    BoxyBox(ObjectBox other) {
        super(other);
        this.boundBox = new Rectangle2D.Double(other.x, other.y,
                other.getBoundBox().getWidth(), other.getBoundBox().getY());
    }

    // creates bounding box
    public void createBoundingBox(double w, double h) {
        this.boundBox = new Rectangle2D.Double(this.x, this.y, w, h);
    }

    /*==================================================
                        Collision
    ==================================================*/

    // checks if two bounding boxes have collided
    public boolean basicCollisionCheck(ObjectBox other) {
        return this.boundBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth(),
                other.getBoundBox().getHeight());
    }

    // checks future collision in x direction
    public boolean xCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Rectangle2D.Double futureBox = new Rectangle2D.Double(
                this.x + this.xSpeed, this.y, this.boundBox.width,
                this.boundBox.height);

        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth(),
                other.getBoundBox().getHeight());
    }

    // checks future collision in y direction
    public boolean yCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Rectangle2D.Double futureBox = new Rectangle2D.Double(this.x,
                this.y + this.ySpeed, this.boundBox.width,
                this.boundBox.height);

        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth(),
                other.getBoundBox().getHeight());
    }

    // checks future collision in both x and y directions
    public boolean diagCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Rectangle2D.Double futureBox = new Rectangle2D.Double(
                this.x + this.xSpeed, this.y + this.ySpeed,
                this.boundBox.width, this.boundBox.height);

        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth(),
                other.getBoundBox().getHeight());
    }

    // checks horizontal distance between two hit boxes
    public double objDistX(ObjectBox other) {

        return Math.abs(this.boundBox.getCenterX() -
                other.getBoundBox().getCenterX()) -
                (this.boundBox.width + other.getBoundBox().getWidth());
    }

    // checks vertical distance between two hit boxes
    public double objDistY(ObjectBox other) {

        return Math.abs(this.boundBox.getCenterY() -
                other.getBoundBox().getCenterY()) -
                (this.boundBox.height + other.getBoundBox().getHeight());
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // updates position and bounding box
    public void updatePosition() {

        // update position
        super.updatePosition();

        // update bound box
        this.boundBox = new Rectangle2D.Double(this.getX(), this.getY(),
                this.boundBox.width, this.boundBox.height);
    }

    public void setBoundBox(RectangularShape b) {
        this.boundBox = (Rectangle2D.Double) b;
    }

    public RectangularShape getBoundBox() {
        return this.boundBox;
    }

    /*==================================================
                      Miscellaneous
    ==================================================*/

    // generates object's hashcode for equality check
    public int hashcode() {
        return Objects.hash(super.hashcode(), this.boundBox);
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
