package sources;

import sources.objCode.GameObject;

public class Controller {

    private int button; // 1 if button, 0 if axes
    private int index; // 0-5 for axes, 0-13 for buttons
    private float range; // only applicable for axes
    private String obj;
    private String purpose;
    private double speed;
    private Audio sounds;

    // projectile variable
    private GameObject projectile;

    // creation/deletion
    Controller(int button, int index, float range, String obj, String purpose) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.purpose = purpose;
    }

    // movement
    Controller(int button, int index, float range, String obj, String purpose, double speed) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.speed = speed;
        this.purpose = purpose;
    }

    // audio
    Controller(int button, int index, float range, String obj, String purpose, Audio sndName) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.sounds = sndName;
        this.purpose = purpose;
    }

    // fire projectile
    Controller(int button, int index, float range, String obj, String purpose, GameObject p) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.purpose = purpose;
        this.projectile = p;
    }

    int getButton() {
        return this.button;
    }

    public int getIndex() {
        return this.index;
    }

    float getRange() {
        return this.range;
    }

    void execute(GameRoom room) {
        switch (purpose) {
            case "Create":
                room.addObject(room.getElement(this.obj));
                break;

            case "Destroy":
                room.removeObject(room.getElement(this.obj));
                break;

            case "MoveX":
                room.getElement(this.obj).setXSpeed(this.speed / 1000.0);
                break;

            case "MoveY":
                room.getElement(this.obj).setYSpeed(this.speed / 1000.0);
                break;

            case "PlaySound":
                sounds.loadPlaySound();
                break;

            case "Fire":

                // prepare projectile
                Event.prepProjectile(room.getElement(obj), this.projectile);

                // add to room
                room.addObject(new GameObject(this.projectile));
                break;
        }
    }
}