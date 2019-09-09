package sources.objCode;

import sources.GameRoom;
import sources.Sprite;

import java.util.Objects;

// represents generic object
public class GameObject {

    private String objName;

    private Sprite sprite;

    // no gravity is gravityFactor = 0
    // controls "weight" of object
    private double gravityFactor;
    private double terminalV;
    private double jumpPower;

    private boolean canCollide;

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

        this.gravityFactor = 0.0;
        this.terminalV = 0.0;
        this.jumpPower = 0.0;

        this.canCollide = false;

        // default hit box is square
        this.boxCode = 0;
        this.hitBox = null;
    }

    // copy constructor
    public GameObject(GameObject other) {
        this.objName = other.objName;
        this.sprite = new Sprite(other.sprite);

        this.gravityFactor = other.gravityFactor;
        this.terminalV = other.terminalV;
        this.jumpPower = other.jumpPower;

        this.canCollide = other.canCollide;

        // create hit box
        this.boxCode = other.boxCode;

        if (other.boxCode == 1) {
            this.hitBox = new RoundBox(other.hitBox);
        }
        else {
            this.hitBox = new BoxyBox(other.hitBox);
        }

        this.hitBox.createBoundingBox(other.sprite.getWidth(),
                other.sprite.getHeight());
    }

    // constructor via given values (finds sprite via path)
    public GameObject(String name, String sprPath, int w, int h,
                      boolean collide, double gravity, double tv,
                      double jump, int boxType) {

        this.objName = name;

        // get sprite from file
        this.sprite = new Sprite(sprPath);

        this.gravityFactor = gravity;
        this.terminalV = tv;
        this.jumpPower = jump;

        this.canCollide = collide;

        // create hit box
        this.boxCode = boxType;

        if (this.boxCode == 1) {
            this.hitBox = new RoundBox();
        }
        else {
            this.hitBox = new BoxyBox();
        }

        this.hitBox.createBoundingBox(this.sprite.getWidth(),
                this.sprite.getHeight());
    }

    /*==================================================
                        Movement
    ==================================================*/

    // moves objects and performs collision detection
    public void move(ObjectList roomObjs) {

        // acceleration due to gravity
        if (gravityFactor != 0.0 && this.hitBox.ySpeed < this.terminalV) {
            this.hitBox.ySpeed += (GameRoom.GRAVITY * this.gravityFactor);
        }

        // check collision
        if (this.canCollide) {

            for (GameObject other : roomObjs.getOList()) {

                // don't collide with self
                if (this.equals(other))
                    continue;

                // test future horizontal collision
                if (this.hitBox.xCollisionCheck(other.hitBox)) {

                    // move up to object without actually colliding
                    this.hitBox.xSpeed = Math.signum(
                            this.hitBox.objDistX(other.hitBox));

                }

                // test future vertical collision
                if (this.hitBox.yCollisionCheck(other.hitBox)) {

                    // move up to object without actually colliding
                    this.hitBox.ySpeed = Math.signum(
                            this.hitBox.objDistY(other.hitBox));
                }

                // test future diagonal collision
                if (this.hitBox.diagCollisionCheck(other.hitBox)) {

                    // move up to object from x direction
                    this.hitBox.xSpeed = Math.signum(
                            this.hitBox.objDistX(other.hitBox));

                    // move up to object from y direction
                    this.hitBox.ySpeed = Math.signum(
                            this.hitBox.objDistY(other.hitBox));
                }
            }
        }

        // update position
        this.hitBox.updatePosition();
    }

    public void objectJump(ObjectList roomObjs) {

        // check if colliding on bottom
        if (this.canCollide) {

            for (GameObject other : roomObjs.getOList()) {

                if (this.hitBox.yCollisionCheck(other.hitBox) &&
                        other.hitBox.y > this.hitBox.y) {

                    this.hitBox.ySpeed = -this.jumpPower;
                    break;
                }
            }
        }
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
        this.hitBox.createBoundingBox(s.getWidth(), s.getHeight());
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public void setGravityFactor(double g) {
        this.gravityFactor = g;
    }

    public double getGravityFactor() {
        return this.gravityFactor;
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

    public int getBoxCode() {
        return this.boxCode;
    }

    public void setXSpeed(double s) {
        this.hitBox.setXSpeed(s);
    }

    public void setYSpeed(double s) {
        this.hitBox.setYSpeed(s);
    }

    public double getXSpeed() { return hitBox.getXSpeed(); }

    public double getYSpeed() { return hitBox.getYSpeed(); }

    public double getX() { return hitBox.getX(); }

    public double getY() { return hitBox.getY(); }

    /*==================================================
                      Miscellaneous
    ==================================================*/

    // generates object's hashcode for equality check
    public int hashcode() {
        return Objects.hash(this.objName, this.sprite, this.gravityFactor,
                this.canCollide, this.hitBox);
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