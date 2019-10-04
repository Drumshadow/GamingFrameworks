package sources;

import sources.objCode.GameObject;
import sources.GameRoom;

public class Controller {

    private int button; // 1 if button, 0 if axes
    private int index; // 0-5 for axes, 0-13 for buttons
    private float range; // only applicable for axes
    private GameObject obj;
    private String purpose;
    private double speed;
    private Audio sounds;

    public Controller(int button, int index, float range, GameObject obj, String purpose) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.purpose = purpose;
    }

    public Controller(int button, int index, float range, GameObject obj, String purpose, double speed) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.speed = speed;
        this.purpose = purpose;
    }

    public Controller(int button, int index, float range, GameObject obj, String purpose, Audio sndName) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.sounds = sndName;
        this.purpose = purpose;
    }

    public int getButton() {
        return button;
    }

    public int getIndex() {
        return index;
    }

    public float getRange() {
        return range;
    }

    /*public String getPurpose() {
        switch(purpose) {
            case Create:
                return "Create";
            case Destroy:
                return "Destroy";
            case MoveX:
                return "MoveX";
            case MoveY:
                return "MoveY";
            case PlaySound:
                return "PlaySound";
        }
        return null;
    }*/

    public void execute(GameRoom room) {
        if (purpose.equals("Create")) {
            room.addObject(new GameObject(this.obj));
        }
        if (purpose.equals("Destroy")) {
            room.removeObject(this.obj);
        }
        if (purpose.equals("MoveX")) {
            obj.setXSpeed(speed / 1000);
            obj.move(room.getAllObjects());
        }
        if (purpose.equals("MoveY")) {
            obj.setYSpeed(speed / 1000);
            obj.move(room.getAllObjects());
        }
        if (purpose.equals("PlaySound")) {
            sounds.loadPlaySound();
        }
    }
}
