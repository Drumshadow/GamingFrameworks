package sources;

import sources.objCode.GameObject;

import java.util.Vector;

public class GameRoom {

    private Sprite background;

    // collection of all objects in room
    private Vector<GameObject> allObjects;

    // acceleration due to gravity
    public static final double GRAVITY = -9.8 / 100.0;

    /*==================================================
                     Initialization
    ==================================================*/

    // default constructor
    GameRoom() {
        this.background = null;
        this.allObjects = new Vector<>();
    }

    /*==================================================
                    Object Control
    ==================================================*/

    // adds object to room
    void addObject(GameObject o) {
        this.allObjects.add(o);
    }

    // removes object from room
    void removeObject(GameObject o) {
        this.allObjects.remove(o);
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    void setBackground(Sprite bk) {
        this.background = new Sprite(bk);
    }

    Sprite getBackground() {
        return this.background;
    }

    public Vector<GameObject> getAllObjects() {
        return this.allObjects;
    }

    // gets first game object with given name
    public GameObject getElement(String oN) {

        for (GameObject object : this.allObjects) {

            if (object.getObjName().equals(oN)) {
                return object;
            }
        }
        return null;
    }

    // returns all elements with the same name
    Vector<GameObject> getElements(String name) {

        Vector<GameObject> elements = new Vector<>();

        for (GameObject object : this.allObjects) {

            if (object.getObjName().equals(name)) {
                elements.add(object);
            }
        }
        return elements;
    }
}