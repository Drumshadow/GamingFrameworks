package sources;

import sources.objCode.GameObject;

import java.awt.image.BufferedImage;
import java.util.Vector;

public class GameRoom {

    private String roomName;

    private String bkgPath;
    private BufferedImage background;

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

        this.bkgPath = null;
        this.background = null;

        this.roomWidth = 0.0;
        this.roomHeight = 0.0;

        this.allObjects = new Vector<GameObject>();
    }

    // TODO: copy constructor, value constructor (call loadsprite and shrink sprite)

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

    public void setBkgPath(String bp) {
        this.bkgPath = bp;
    }

    public String getBkgPath() {
        return this.bkgPath;
    }

    public void setBackground(BufferedImage bk) {
        this.background = bk;
    }

    public BufferedImage getBackground() {
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
