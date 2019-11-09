package sources;

public class Controller {

    private int button; // 1 if button, 0 if axes
    private int index; // 0-5 for axes, 0-13 for buttons
    private float range; // only applicable for axes
    private String obj;
    private String purpose;
    private double speed;
    private Audio sounds;

    Controller(int button, int index, float range, String obj, String purpose) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.purpose = purpose;
    }

    Controller(int button, int index, float range, String obj, String purpose, double speed) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.speed = speed;
        this.purpose = purpose;
    }

    Controller(int button, int index, float range, String obj, String purpose, Audio sndName) {
        this.button = button;
        this.index = index;
        this.range = range;
        this.obj = obj;
        this.sounds = sndName;
        this.purpose = purpose;
    }

    int getButton() {
        return button;
    }

    public int getIndex() {
        return index;
    }

    float getRange() {
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

    void execute(GameRoom room) {
        if (purpose.equals("Create")) {
            for (int i = 0; i < room.getAllObjects().size(); i++) {
                if (room.getElement(i).getObjName().equals(obj)) {
                    room.addObject(room.getElement(i));
                }
            }
        }
        if (purpose.equals("Destroy")) {
            for (int i = 0; i < room.getAllObjects().size(); i++) {
                if (room.getElement(i).getObjName().equals(obj)) {
                    room.removeObject(room.getElement(i));
                }
            }
        }
        if (purpose.equals("MoveX")) {
            for (int i = 0; i < room.getAllObjects().size(); i++) {
                if (room.getElement(i).getObjName().equals(obj)) {
                    room.getElement(i).setXSpeed(speed / 1000);
                }
            }
        }
        if (purpose.equals("MoveY")) {
            for (int i = 0; i < room.getAllObjects().size(); i++) {
                if (room.getElement(i).getObjName().equals(obj)) {
                    room.getElement(i).setYSpeed(speed / 1000);
                }
            }
        }
        if (purpose.equals("PlaySound")) {
            sounds.loadPlaySound();
        }
    }
}
