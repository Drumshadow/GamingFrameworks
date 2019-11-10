package sources.objCode;

import sources.GameRoom;
import sources.Sprite;

import java.awt.geom.Rectangle2D;
import java.util.*;

// represents generic object
public class GameObject {

    private String objName;

    private Sprite sprite;

    // no gravity is weight = 0
    // controls "weight" of object
    private double weight;
    private double terminalV;
    private double jumpPower;

    private boolean canCollide;

    // boxCode is the type of hit box
    // 0 = square, 1 = round
    private int boxCode;
    private ObjectBox hitBox;

    // AI behaviors
    public enum Behavior{COPY, LEDGES, WALLS, BOUNCE, AUTO, EMIT, DESTRUCT}
    private Set<Behavior> ai = new HashSet<>();

    private double autoX = 0.0;
    private double autoY = 0.0;

    private GameObject target = null;
    private GameObject destroyer = null;

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

        // create hit box
        this.boxCode = other.boxCode;

        if (other.boxCode == 1) {
            this.hitBox = new RoundBox(other.hitBox);
        }
        else {
            this.hitBox = new BoxyBox(other.hitBox);
        }

        this.ai = other.ai;
        this.autoX = other.autoX;
        this.autoY = other.autoY;

        this.target = other.target;
        this.destroyer = other.destroyer;
    }

    // constructor via given values (finds sprite via path)
    public GameObject(String name, String sprPath, int frames, boolean collide,
                      double weight, double tv, double jump,
                      int boxType, double x, double y) {

        this.objName = name;
        this.sprite = new Sprite(sprPath, frames);

        this.weight = weight / 1000.0;
        this.terminalV = tv / 1000.0;
        this.jumpPower = jump;

        this.canCollide = collide;

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

    /*==================================================
                        Movement
    ==================================================*/

    // moves objects and performs collision detection
    public void move(Vector<GameObject> roomObjects) {

        // copy target
        if (this.ai.contains(Behavior.COPY)) {

            this.setXSpeed(target.getXSpeed());
            this.setYSpeed(target.getYSpeed());

            this.setX(this.getX() + this.getXSpeed());
            this.setY(this.getY() + this.getYSpeed());

            return;
        }

        // auto-move unless the object is supposed to bounce off things
        if (this.ai.contains(Behavior.AUTO) && !this.ai.contains(Behavior.WALLS) &&
                !this.ai.contains(Behavior.BOUNCE)) {

            this.setXSpeed(this.autoX);
            this.setYSpeed(this.autoY);
        }

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
                if (this.equals(other)) {
                    continue;
                }

                // don't collide with objects without collision
                if (!other.canCollide) {
                    continue;
                }

                // test future collision
                boolean xCollision = this.hitBox.xCollisionCheck(other.hitBox);
                boolean yCollision = this.hitBox.yCollisionCheck(other.hitBox);

                if(xCollision || yCollision) {
                    if (xCollision) {

                        if (ai.contains(Behavior.WALLS)) {
                            this.setXSpeed(this.getXSpeed() * -1.0);
                        }
                        else {
                            // move up to object without actually colliding
                            this.hitBox.xSpeed = Math.signum(
                                    this.hitBox.objDistX(other.hitBox)) / 1000.0;
                            if(this.hitBox.objDistX(other.hitBox) <= 0.001) {
                                this.hitBox.xSpeed = 0;
                            }
                        }
                    }
                    else {

                        if (ai.contains(Behavior.BOUNCE)) {
                            this.setYSpeed(this.getYSpeed() * -1.0);
                        }
                        else {

                            // move up to object without actually colliding
                            this.hitBox.ySpeed = Math.signum(
                                    this.hitBox.objDistY(other.hitBox)) / 1000.0;
                            if (this.hitBox.objDistY(other.hitBox) < 0.001) {
                                this.hitBox.ySpeed = 0;
                            }

                            // perform ledge detection
                            if (this.ai.contains(Behavior.LEDGES)) {
                                this.ledges(roomObjects);
                            }
                        }
                    }
                }

                // test future diagonal collision
                if (!xCollision && !yCollision) {
                    if(this.hitBox.diagCollisionCheck(other.hitBox)) {

                        if (this.ai.contains(Behavior.WALLS) && this.ai.contains(Behavior.BOUNCE)) {
                            this.setXSpeed(this.getXSpeed() * -1.0);
                            this.setYSpeed(this.getYSpeed() * -1.0);
                        }
                        else {

                            // move up to object from x direction
                            this.hitBox.xSpeed = Math.signum(
                                    this.hitBox.objDistX(other.hitBox)) / 1000.0;
                            if (this.hitBox.objDistX(other.hitBox) <= 0.001) {
                                this.hitBox.xSpeed = 0;
                            }

                            // move up to object from y direction
                            this.hitBox.ySpeed = Math.signum(
                                    this.hitBox.objDistY(other.hitBox)) / 1000.0;
                            if (this.hitBox.objDistY(other.hitBox) <= 0.001) {
                                this.hitBox.ySpeed = 0;
                            }
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
                      AI Behaviors
    ==================================================*/

    public void addBehaviors(Behavior behavior) {
        this.ai.add(behavior);
    }

    private void ledges(Vector<GameObject> room) {

        // create temp box to detect ledge
        BoxyBox temp = new BoxyBox(this.getX(), this.getY(),
                this.hitBox.boundingBox.getWidth() / 8.0,
                this.hitBox.boundingBox.getHeight() / 8.0);

        temp.setXSpeed(this.getXSpeed());
        temp.setYSpeed(this.getYSpeed());
        temp.updatePosition();

        temp.setY(temp.y - this.hitBox.boundingBox.getHeight());

        // check if temp intersects with any sort of ground
        boolean hasLedge = true;

        for (GameObject go : room) {

            // if there is something there, then there is NO ledge
            if (go.canCollide && temp.getBoundBox().intersects((
                    Rectangle2D.Double)go.hitBox.boundingBox)) {

                if (go.getY() < this.getY()) {
                    hasLedge = false;
                    break;
                }
            }
        }

        // adjust speed if there is a ledge
        if (hasLedge) {
            this.setXSpeed(this.getXSpeed() * -1.0);
        }
    }

    public void auto(double xSpeed, double ySpeed) {
        this.autoX = xSpeed;
        this.autoY = ySpeed;

        this.setXSpeed(xSpeed);
        this.setYSpeed(ySpeed);
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

    public Set<Behavior> getAi() {
        return this.ai;
    }

    public void setAi(Set<Behavior> ai) {
        this.ai = ai;
    }

    public GameObject getDestroyer() {
        return this.destroyer;
    }

    public void setDestroyer(GameObject destroyer) {
        this.destroyer = destroyer;
    }

    public GameObject getTarget() {
        return this.target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
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