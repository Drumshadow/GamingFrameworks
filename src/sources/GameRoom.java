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
    public static final double GRAVITY = -9.8 / 100.0;

    /*==================================================
                     Initialization
    ==================================================*/

    // default constructor
    GameRoom() {

        this.roomName = "Empty Room";
        this.background = null;

        this.roomWidth = 0.0;
        this.roomHeight = 0.0;

        this.allObjects = new Vector<>();
    }

    // copy constructor
    GameRoom(GameRoom other) {

        this.roomName = other.roomName;
        this.background = new Sprite(other.background);

        this.roomWidth = other.roomWidth;
        this.roomHeight = other.roomHeight;

        this.allObjects = new Vector<>(other.allObjects);
    }

    // value constructor (finds background via path)
    GameRoom(String name, String path) {

        this.roomName = name;
        this.background = new Sprite(path, 1);

        this.roomWidth = this.background.getWidth();
        this.roomHeight = this.background.getHeight();

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

    public void setRoomName(String r) {
        this.roomName = r;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public void setBackground(Sprite bk) {
        this.background = new Sprite(bk);

        this.roomWidth = this.background.getWidth();
        this.roomHeight = this.background.getHeight();
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

    Vector<GameObject> getAllObjects() {
        return this.allObjects;
    }

    int objectCount() {
        return this.allObjects.size();
    }

    GameObject getElement(String oN) {
        for (int i = 0; i < this.allObjects.size(); i++) {

            if (this.allObjects.elementAt(i).getObjName().equals(oN)) {
                return this.allObjects.elementAt(i);
            }
        }
        return null;
    }

    GameObject getElement(int i) {
        return this.allObjects.get(i);
    }
}