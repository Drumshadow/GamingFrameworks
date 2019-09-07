
import java.awt.geom.Rectangle2D;
import java.util.Objects;

// represents a generic game object
public class GameObject {

    private String objName;

    private String spritePath;

    private boolean hasGravity;
    private boolean canCollide;

    // position based on top-left corner
    private double x;
    private double y;

    private double xSpeed;
    private double ySpeed;

    // dimensions of hit box
    private double width;
    private double height;

    // TODO: add acceleration and max speeds

    private Rectangle2D.Double boundBox;
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

        this.x = 0.0;
        this.y = 0.0;

        this.width = 0.0;
        this.height = 0.0;

        this.xSpeed = 0.0;
        this.ySpeed = 0.0;

        this.boundBox = null;
    }

    // TODO: non generic constructor?
    // TODO: create bounding box

    /*==================================================
                        Drawing
    ==================================================*/

    // sets object's sprite and saves to file within game engine
    public void loadSprite(String spritePath) {

    }

    // draws object
    public void drawObject() {

    }

    /*==================================================
                        Movement
    ==================================================*/

    // moves objects and performs collision detection
    public void move(GameObject[] objs) {

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

                // TODO: update speeds
                // TODO: decide between look-ahead collision or other??

                if (xCollide)
                    xSpeed = 0;

                if (yCollide)
                    ySpeed = 0;

            }
        }

        // move if able
        this.x += xSpeed;
        this.y += ySpeed;

        // update hit box
        this.boundBox.x = this.x;
        this.boundBox.y = this.y;
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
        Rectangle2D.Double futureBox = new Rectangle2D.Double(
                this.x + this.xSpeed, this.y, this.width, this.height);

        return futureBox.intersects(other.boundBox);
    }

    // checks future collision in y direction
    public boolean yCollisionCheck(GameObject other) {

        // make temporary bounding box for future position
        Rectangle2D.Double futureBox = new Rectangle2D.Double(this.x,
                this.y + this.ySpeed, this.width, this.height);

        return futureBox.intersects(other.boundBox);
    }

    // checks future collision in both x and y directions
    public boolean futureCollisionCheck(GameObject other) {

        // make temporary bounding box for future position
        Rectangle2D.Double futureBox = new Rectangle2D.Double(
                this.x + this.xSpeed, this.y + this.ySpeed,
                this.width, this.height);

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

    public void setWidth(double w) {
        this.width = w;
    }

    public double getWidth() {
        return this.width;
    }

    public void setHeight(double h) {
        this.height = h;
    }

    public double getHeight() {
        return this.height;
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