package sources.objCode;

import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.util.Objects;

public class RoundBox extends ObjectBox {

    private Ellipse2D.Double boundBox;

    /*==================================================
                     Initialization
    ==================================================*/

    // generic constructor
    RoundBox() {
        super();
        this.boundBox = null;
    }

    // copy constructor
    RoundBox(ObjectBox other) {
        super(other);
        this.boundBox = new Ellipse2D.Double(other.x, other.y,
                other.getBoundBox().getWidth(), other.getBoundBox().getY());
    }

    // creates bounding box
    public void createBoundingBox(double w, double h) {
        this.boundBox = new Ellipse2D.Double(this.x, this.y, w, h);
    }

    /*==================================================
                        Collision
    ==================================================*/

    // checks if two bounding boxes have collided
    public Boolean basicCollisionCheck(ObjectBox other) {
        return this.boundBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth(),
                other.getBoundBox().getHeight());
    }

    // checks future collision in x direction
    public boolean xCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Ellipse2D.Double futureBox = new Ellipse2D.Double(this.x + this.xSpeed,
                this.y, this.boundBox.width, this.boundBox.height);


        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth(),
                other.getBoundBox().getHeight());
    }

    // checks future collision in y direction
    public boolean yCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Ellipse2D.Double futureBox = new Ellipse2D.Double(this.x,
                this.y + this.ySpeed, this.boundBox.width,
                this.boundBox.height);


        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth(),
                other.getBoundBox().getHeight());
    }

    // checks future collision in both x and y directions
    public boolean futureCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Ellipse2D.Double futureBox = new Ellipse2D.Double(this.x + this.xSpeed,
                this.y + this.ySpeed, this.boundBox.width,
                this.boundBox.height);


        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth(),
                other.getBoundBox().getHeight());
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // updates position and bounding box
    public void updatePosition() {

        // update position
        super.updatePosition();

        // update bound box
        this.boundBox = new Ellipse2D.Double(this.x, this.y,
                this.boundBox.width, this.boundBox.height);
    }

    public void setBoundBox(RectangularShape b) {
        this.boundBox = (Ellipse2D.Double) b;
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
