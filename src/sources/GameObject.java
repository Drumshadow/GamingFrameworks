/*
 * author: Ashley Roesler
 * last updated: 09/03/19
 */

import java.awt.Rectangle;
import java.awt.geom.Area;

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

    private Rectangle boundBox;

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

    /*==================================================
                        Movement
    ==================================================*/

    public void moveX() {
        this.x =+ xSpeed;
    }

    public void moveY() {
        this.y =+ ySpeed;
    }

    // TODO: jump

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

    // TODO: more accurate collision check

    /*==================================================
                   Setters and Getters
    ==================================================*/

    // TODO: setters and getters

}