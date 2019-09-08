package sources;

import sources.objCode.GameObject;
import java.util.Vector;

public class GameRoom {

    private String roomName;

    // collection of all objects in room
    private Vector<GameObject> allObjects;

    // acceleration due to gravity
    public static final double GRAVITY = 9.8;

    // TODO: add background

    /*==================================================
                     Initialization
    ==================================================*/

    public GameRoom() {

        this.roomName = "Empty Room";
        this.allObjects = new Vector<GameObject>();
    }

    /*==================================================
                    Object Control
    ==================================================*/

    // adds object to room
    public void addObject(GameObject o) {
        this.allObjects.add(o);
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    public void setRoomName(String r) {
        this.roomName = r;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public void setAllObjects(Vector<GameObject> o) {
        this.allObjects = o;
    }

    public Vector<GameObject> getAllObjects() {
        return this.allObjects;
    }
}
