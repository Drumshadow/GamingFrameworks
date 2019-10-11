package sources.objCode;

import sources.GameRoom;
import sources.Sprite;

import java.awt.geom.Rectangle2D;
import java.util.Objects;
import java.util.Vector;

// represents generic object
public class GameObject {

    private String objName;

    private Sprite sprite;

    // no gravity is gravityFactor = 0
    // controls "weight" of object
    private double weight;
    private double terminalV;
    private double jumpPower;

    private boolean canCollide;
    private boolean fearLedge;

    // boxCode is the type of hit box
    // 0 = square, 1 = round
    private int boxCode;
    private ObjectBox hitBox;

    /*==================================================
                     Initialization
    ==================================================*/

    // default constructor
    public GameObject() {
        this.objName = "Empty Object";
        this.sprite = null;

        this.weight = 0.0;
        this.terminalV = 0.0;
        this.jumpPower = 0.0;

        this.canCollide = false;
        this.fearLedge = false;

        // default hit box is square
        this.boxCode = 0;
        this.hitBox = new BoxyBox();
    }

    // copy constructor
    public GameObject(GameObject other) {
        this.objName = other.objName;
        this.sprite = new Sprite(other.sprite);

        this.weight = other.weight;
        this.terminalV = other.terminalV;
        this.jumpPower = other.jumpPower;

        this.canCollide = other.canCollide;
        this.fearLedge = other.fearLedge;

        // create hit box
        this.boxCode = other.boxCode;

        if (other.boxCode == 1) {
            this.hitBox = new RoundBox(other.hitBox);
        }
        else {
            this.hitBox = new BoxyBox(other.hitBox);
        }
    }

    // constructor via given values (finds sprite via path)
    public GameObject(String name, String sprPath, boolean collide, boolean fear,
                      double weight, double tv, double jump, int boxType,
                      double x, double y) {

        this.objName = name;
        this.sprite = new Sprite(sprPath);

        this.weight = weight / 1000.0;
        this.terminalV = tv / 1000.0;
        this.jumpPower = jump;

        this.canCollide = collide;
        this.fearLedge = fear;

        // create hit box
        this.boxCode = boxType;

        if (this.boxCode == 1) {
            this.hitBox = new RoundBox((x - 1000) / 1000,
                    (y - 1000) / 1000, this.sprite.getWidth() / 500.0,
                    this.sprite.getHeight() / 500.0);
        }
        else {
            this.hitBox = new BoxyBox((x - 1000) / 1000,
                    (y - 1000) / 1000,  this.sprite.getWidth() / 500.0,
                    this.sprite.getHeight() / 500.0);
        }
    }

    // constructor via given values (invisible object)
    public GameObject(String name, boolean collide, double weight, double tv,
                      double jump, int boxType, double x, double y, int width,
                      int height) {

        this.objName = name;
        this.sprite = new Sprite(width, height);

        this.weight = weight;
        this.terminalV = tv;
        this.jumpPower = jump;

        this.canCollide = collide;

        // create hit box
        this.boxCode = boxType;

        if (this.boxCode == 1) {
            this.hitBox = new RoundBox(x, y, this.sprite.getWidth(),
                    this.sprite.getHeight());
        }
        else {
            this.hitBox = new BoxyBox(x, y, this.sprite.getWidth(),
                    this.sprite.getHeight());
        }
    }

    /*==================================================
                        Movement
    ==================================================*/

    // moves objects and performs collision detection
    public void move(Vector<GameObject> roomObjects) {

        // acceleration due to gravity
        if (this.weight != 0.0 && this.hitBox.ySpeed < this.terminalV) {
            this.hitBox.ySpeed += (GameRoom.GRAVITY * this.weight);

            // don't go over terminal velocity
            if (this.hitBox.ySpeed > this.terminalV) {
                this.hitBox.ySpeed = this.terminalV;
            }
        }

        // check collision
        if (this.canCollide) {

            for (GameObject other : roomObjects) {

                // don't collide with self
                if (this.equals(other))
                    continue;

                // don't collide with objects without collision
                if (!other.canCollide)
                    continue;

                // test future collision
                boolean xCollision = this.hitBox.xCollisionCheck(other.hitBox);
                boolean yCollision = this.hitBox.yCollisionCheck(other.hitBox);

                if(xCollision || yCollision) {
                    if (xCollision) {

                        // move up to object without actually colliding
                        this.hitBox.xSpeed = Math.signum(
                                this.hitBox.objDistX(other.hitBox)) / 1000.0;
                        if(this.hitBox.objDistX(other.hitBox) <= 0.001) {
                            this.hitBox.xSpeed = 0;
                        }
                    }
                    else {

                        // move up to object without actually colliding
                        this.hitBox.ySpeed = Math.signum(
                                this.hitBox.objDistY(other.hitBox)) / 1000.0;
                        if(this.hitBox.objDistY(other.hitBox) < 0.001) {
                            this.hitBox.ySpeed = 0;
                        }

                        // perform ledge detection
                        if (this.fearLedge) {

                            // create temporary testing rectangle
                            BoxyBox temp = new BoxyBox();
                            Rectangle2D.Double tempHitBox = new Rectangle2D.Double(
                                    this.getX() + (this.sprite.getWidth() / 2.0) *
                                            (int)Math.signum(this.getXSpeed()),
                                    this.getY() + (this.sprite.getHeight() / 2.0)
                                            + 8.0, this.sprite.getWidth(),
                                    this.sprite.getHeight());

                            temp.setBoundBox(tempHitBox);

                            // test collision
                            if (!this.hitBox.basicCollision(temp)) {
                                this.setXSpeed(this.getXSpeed() * -1.0);
                            }
                        }
                    }
                }

                // test future diagonal collision
                if (!xCollision && !yCollision) {
                    if(this.hitBox.diagCollisionCheck(other.hitBox)) {

                        // move up to object from x direction
                        this.hitBox.xSpeed = Math.signum(
                                this.hitBox.objDistX(other.hitBox)) / 1000.0;
                        if(this.hitBox.objDistX(other.hitBox) <= 0.001) {
                            this.hitBox.xSpeed = 0;
                        }

                        // move up to object from y direction
                        this.hitBox.ySpeed = Math.signum(
                                this.hitBox.objDistY(other.hitBox)) / 1000.0;
                        if(this.hitBox.objDistY(other.hitBox) <= 0.001) {
                            this.hitBox.ySpeed = 0;
                        }
                    }
                }
            }
        }

        // update position
        this.hitBox.updatePosition();
    }

    public void objectJump(Vector<GameObject> roomObjects) {

        // check if colliding on bottom
        if (this.canCollide) {

            for (GameObject other : roomObjects) {

                if (this.hitBox.yCollisionCheck(other.hitBox) &&
                        other.hitBox.y > this.hitBox.y) {

                    this.hitBox.ySpeed = -this.jumpPower;
                    break;
                }
            }
        }
    }

    public void drawObject() {
        this.sprite.drawObject(this.hitBox.getX(), this.hitBox.getY());
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    public void setObjName(String o) {
        this.objName = o;
    }

    public String getObjName() {
        return this.objName;
    }

    public void setSprite(Sprite s) {
        this.sprite = s;

        // update hit box
        if (this.boxCode == 1) {
            this.hitBox = new RoundBox(this.hitBox.x, this.hitBox.y,
                    s.getWidth(), s.getHeight());
        }
        else {
            this.hitBox = new BoxyBox(this.hitBox.x, this.hitBox.y,
                    s.getWidth(), s.getHeight());
        }
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public void setWeight(double w) {
        this.weight = w;
    }

    public double getWeight() {
        return this.weight;
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

    public void setBoxCode(int c) {
        this.boxCode = c;

        // update hit box
        if (c == 1) {
            this.hitBox = new RoundBox(this.hitBox);
        }
        else {
            this.hitBox = new BoxyBox(this.hitBox);
        }
    }

    public void setXSpeed(double s) {
        this.hitBox.setXSpeed(s);
    }

    public void setYSpeed(double s) {
        this.hitBox.setYSpeed(s);
    }

    public double getXSpeed() {
        return hitBox.getXSpeed();
    }

    public double getYSpeed() {
        return hitBox.getYSpeed();
    }

    public double getX() {
        return hitBox.getX();
    }

    public double getY() {
        return hitBox.getY();
    }

    public void setX(double x) {
        this.hitBox.setX(x);
    }

    public void setY(double y) {
        this.hitBox.setY(y);
    }

    public ObjectBox getHitBox() {
        return this.hitBox;
    }

    /*==================================================
                      Miscellaneous
    ==================================================*/

    // generates object's hashcode for equality check
    private int hashcode() {
        return Objects.hash(this.objName, this.sprite, this.weight,
                this.canCollide, this.hitBox, this.terminalV, this.jumpPower);
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