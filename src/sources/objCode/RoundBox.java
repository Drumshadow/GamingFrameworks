package sources.objCode;

import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

public class RoundBox extends ObjectBox {

    /*==================================================
                     Initialization
    ==================================================*/

    // default constructor
    RoundBox() {
        super();
        this.boundingBox = new Ellipse2D.Double();
    }

    // copy constructor
    RoundBox(ObjectBox b) {
        super(b);
    }

    // value constructor
    RoundBox(double x, double y, double w, double h) {
        super();

        this.boundingBox = new Ellipse2D.Double(x, y, w, h);

        this.x = x;
        this.y = y;
    }

    /*==================================================
                        Collision
    ==================================================*/

    // checks future collision in x direction
    public boolean xCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Ellipse2D.Double futureBox = new Ellipse2D.Double(this.x + this.xSpeed,
                this.y, this.boundingBox.getWidth(),
                this.boundingBox.getHeight());


        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth(),
                other.getBoundBox().getHeight());
    }

    // checks future collision in y direction
    public boolean yCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Ellipse2D.Double futureBox = new Ellipse2D.Double(this.x,
                this.y + this.ySpeed, this.boundingBox.getWidth(),
                this.boundingBox.getHeight());


        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth(),
                other.getBoundBox().getHeight());
    }

    // checks future collision in both x and y directions
    public boolean diagCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Ellipse2D.Double futureBox = new Ellipse2D.Double(this.x + this.xSpeed,
                this.y + this.ySpeed, this.boundingBox.getWidth(),
                this.boundingBox.getHeight());


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
        this.boundingBox = new Ellipse2D.Double(this.x, this.y,
                this.boundingBox.getWidth(), this.boundingBox.getHeight());
    }

    public void setBoundBox(RectangularShape b) {
        this.boundingBox = b;
    }

    public RectangularShape getBoundBox() {
        return this.boundingBox;
    }
}