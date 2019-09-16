package sources;

import sources.objCode.GameObject;
import sources.objCode.ObjectList;

public class Controller {
    private int button; // 1 if button, 0 if axes
    private int index; // 0-5 for axes, 0-13 for buttons
    private float range; // only applicable for axes
    private GameObject obj;
    enum purpose{Create, Destroy, MoveX, MoveY, PlaySound};
    static private purpose purpose;
    private double speed;
    private Audio sounds;

    public Controller(int button, int index, float range, GameObject obj, String purpose) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        if (purpose.equals("Create")) {
            this.purpose = Controller.purpose.Create;
        }
        else if (purpose.equals("Destroy")) {
            this.purpose = Controller.purpose.Destroy;
        }
    }

    public Controller(int button, int index, float range, GameObject obj, String purpose, double speed) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.speed = speed;
        if (purpose.equals("MoveX")) {
            this.purpose = Controller.purpose.MoveX;
        }
        else if (purpose.equals("MoveY")) {
            this.purpose = Controller.purpose.MoveY;
        }
    }

    public Controller(int button, int index, float range, GameObject obj, String purpose, Audio sndName) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.sounds = sndName;
        this.purpose = Controller.purpose.PlaySound;
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

    public void execute(ObjectList roomObjects) {
        switch(purpose) {

            case Create:
                roomObjects.addObject(new GameObject(obj));
                break;

            case Destroy:
                roomObjects.removeObject(obj);
                break;

            case MoveX:
                obj.setXSpeed(speed);
                obj.move(roomObjects);
                break;

            case MoveY:
                obj.setYSpeed(speed);
                obj.move(roomObjects);
                break;

            case PlaySound:
                sounds.loadPlaySound();
                break;
        }
    }
}
