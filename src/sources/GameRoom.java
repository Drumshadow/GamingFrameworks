package sources;

import sources.objCode.GameObject;

import java.util.Vector;

public class GameRoom {

    private String roomName;

    private Sprite background;

    private double roomWidth;
    private double roomHeight;

    // collection of all objects in room
    private Vector<GameObject> allObjects;

    // acceleration due to gravity
    public static final double GRAVITY = 0.2;

    /*==================================================
                     Initialization
    ==================================================*/

    // generic constructor
    public GameRoom() {

        this.roomName = "Empty Room";
        this.background = null;

        this.roomWidth = 0.0;
        this.roomHeight = 0.0;

        this.allObjects = new Vector<GameObject>();
    }

    // copy constructor
    public GameRoom(GameRoom other) {

        this.roomName = other.roomName;
        this.background = new Sprite(other.background);

        this.roomWidth = other.roomWidth;
        this.roomHeight = other.roomHeight;

        this.allObjects = new Vector<GameObject>(other.allObjects);
    }

    // value constructor (finds background via path)
    public GameRoom(String name, String path, double rw, double rh) {

        this.roomName = name;
        this.background = new Sprite(path);
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

    public void setBackground(Sprite bk) {
        this.background = bk;
    }

    public Sprite getBackground() {
        return this.background;
    }

    public void setRoomWidth(double w) {
        this.roomWidth = w;
    }

    public double getRoomWidth() {
        return this.roomWidth;
    }

    public void setRoomHeight(double h) {
        this.roomHeight = h;
    }

    public double getRoomHeight() {
        return this.roomHeight;
    }

    public void setAllObjects(Vector<GameObject> o) {
        this.allObjects = o;
    }

    public Vector<GameObject> getAllObjects() {
        return this.allObjects;
    }
}
