/*
 * author: Ashley Roesler
 * last updated: 09/03/19
 */

import java.awt.Rectangle;

// represents a generic game object
public class GameObject {

    private String objName;

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

    private Rectangle hitBox;

    // TODO: default constructor
    // TODO: check collision
    public Boolean collisionCheck(GameObject other) {

        if (!this.canCollide || !other.canCollide)
            return false;

        return this.hitBox.intersects(other.hitBox);
    }


    // TODO: fill in rectangle so object can be drawn
    // TODO: movement controls
    // TODO: setters and getters

}