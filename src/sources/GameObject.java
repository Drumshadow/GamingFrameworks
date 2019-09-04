/*
 * author: Ashley Roesler
 * last updated: 09/03/19
 */

import java.awt.Rectangle;

// represents a generic game object
public class GameObject {

    private String objName;

    private String spritePath;

    private boolean hasGravity;
    private boolean canCollide;

    // TODO: decide in int or float

    // position based on top-left corner
    private int x;
    private int y;

    // dimensions of hit box
    private int width;
    private int height;

    private int xSpeed;
    private int ySpeed;

    // TODO: add acceleration and max speeds

    private Rectangle boundBox;
    // TODO: add circle hit box?
    // TODO: add custom hit box?

    /*==================================================
                     Initialization
    ==================================================*/

    public GameObject() {
        this.objName = "Generic Object";

        this.hasGravity = false;
        this.canCollide = false;

        this.x = 0;
        this.y = 0;

        this.width = 0;
        this.height = 0;

        this.xSpeed = 0;
        this.ySpeed = 0;

        this.boundBox = null;
    }

    // TODO: create bounding box
    // TODO: load sprite
    // TODO: draw

    /*==================================================
                        Movement
    ==================================================*/

    public void move() {

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
        Rectangle futureBox = new Rectangle(this.x + this.xSpeed,
                this.y, this.width, this.height);

        return futureBox.intersects(other.boundBox);
    }

    // checks future collision in y direction
    public boolean yCollisionCheck(GameObject other) {

        // make temporary bounding box for future position
        Rectangle futureBox = new Rectangle(this.x,
                this.y + this.ySpeed, this.width, this.height);

        return futureBox.intersects(other.boundBox);
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // TODO: setters and getters

}