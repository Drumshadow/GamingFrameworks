package sources.objCode;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

// this is a rectangular hit box
public class BoxyBox extends ObjectBox {

    /*==================================================
                     Initialization
    ==================================================*/

    // copy constructor
    BoxyBox(ObjectBox b) {
        super(b);
    }

    // value constructor
    BoxyBox(double x, double y, double w, double h) {
        super();

        this.boundingBox = new Rectangle2D.Double(x, y, w, h);

        this.x = x;
        this.y = y;
    }

    /*==================================================
                        Collision
    ==================================================*/

    // checks future collision in x direction
    public boolean xCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position and movement path
        Rectangle2D.Double futureBox;

        futureBox = new Rectangle2D.Double(this.x + this.xSpeed, this.y,
                this.boundingBox.getWidth(), this.boundingBox.getHeight());

        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth() + 0.001,
                other.getBoundBox().getHeight() + 0.001);
    }

    // checks future collision in y direction
    public boolean yCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position and movement path
        Rectangle2D.Double futureBox;

        futureBox = new Rectangle2D.Double(this.x, this.y + this.ySpeed,
                this.boundingBox.getWidth(), this.boundingBox.getHeight());

        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth() + 0.001,
                other.getBoundBox().getHeight() + 0.001);
    }

    // checks future collision in both x and y directions
    public boolean diagCollisionCheck(ObjectBox other) {

        // make temporary bounding box for future position
        Rectangle2D.Double futureBox = new Rectangle2D.Double(
                this.x + this.xSpeed, this.y + this.ySpeed,
                this.boundingBox.getWidth(), this.boundingBox.getHeight());

        return futureBox.intersects(other.x, other.y,
                other.getBoundBox().getWidth() + 0.0001,
                other.getBoundBox().getHeight() + 0.0001);
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // updates position and bounding box
    public void updatePosition() {

        // update position
        super.updatePosition();

        // update bound box
        this.boundingBox = new Rectangle2D.Double(this.getX(), this.getY(),
                this.boundingBox.getWidth(), this.boundingBox.getHeight());
    }

    public void setBoundBox(RectangularShape b) {
        this.boundingBox = b;
    }

    public RectangularShape getBoundBox() {
        return this.boundingBox;
    }
}