package sources;

import sources.objCode.GameObject;

class MultithreadingDemo implements Runnable
{
    private Input.inputPurpose x;

    @Override
    public void run() {

        Audio sounds = new Audio();

        if(x == Input.inputPurpose.MOVEX){
            if(sounds.fileName == null) {
                sounds.setFileName("./audio-files/oof.ogg");
            }
            sounds.loadPlaySound();
        }
    }
}

public class Input {

    private int key;  // 0-256? Not sure
    private int action; // 0: release, 1: press, 2: hold

    enum inputPurpose{CREATE, DESTROY, MOVEX, MOVEY, PLAYSOUND, JUMP, PAUSE, FIRE}
    private inputPurpose purpose;

    private String obj;
    private double speed;

    private Audio sounds = new Audio();
    private MultithreadingDemo object = new MultithreadingDemo();

    // projectile variable
    private GameObject projectile;

    // pause
    Input(int k, int a) {
        this.key = k;
        this.action = a;
        this.purpose = inputPurpose.PAUSE;
    }

    // create and destroy
    Input(int k, int a, String o, String pur) {
        this.key = k;
        this.action = a;
        this.obj = o;
        if (pur.equals("Create")) {
            this.purpose = inputPurpose.CREATE;
        }
        else if (pur.equals("Destroy")) {
            this.purpose = inputPurpose.DESTROY;
        }
    }

    // fire projectile
    Input(int k, int a, String o, GameObject p) {

        this.key = k;
        this.action = a;
        this.obj = o;
        this.projectile = p;
        this.purpose = inputPurpose.FIRE;
    }


    // movement
    Input(int k, int a, String o, String pur, double s) {
        this.key = k;
        this.action = a;
        this.obj = o;

        switch(pur) {

            case "MoveX":
                this.purpose = inputPurpose.MOVEX;
                break;

            case "MoveY":
                this.purpose = inputPurpose.MOVEY;
                break;

            case "Jump":
                this.purpose = inputPurpose.JUMP;
                break;
        }

        this.speed = s / 1000.0;
    }

    // sound
    Input(int k, int a, String o, Audio snd) {
        this.key = k;
        this.action = a;
        this.obj = o;
        this.purpose = inputPurpose.PLAYSOUND;
        this.sounds = snd;
    }

    int getKey() {
        return this.key;
    }
    public int getAction() {
        return this.action;
    }
    public inputPurpose getPurpose() { return this.purpose; }

    void execute(GameRoom room) {

        switch(this.purpose) {

            case CREATE:
                room.addObject(room.getElement(this.obj));
                break;

            case DESTROY:
                room.removeObject(room.getElement(this.obj));
                break;

            case MOVEX:
                room.getElement(this.obj).setXSpeed(this.speed);
                break;

            case MOVEY:
                room.getElement(this.obj).setYSpeed(this.speed);
                break;

            case PLAYSOUND:
                this.sounds.loadPlaySound();
                break;

            case JUMP:
                room.getElement(this.obj).objectJump(room.getAllObjects());
                break;

            case FIRE:

                // prepare projectile
                Event.prepProjectile(room.getElement(this.obj), this.projectile);

                // add to room
                room.addObject(new GameObject(this.projectile));
                break;
        }
    }

    boolean execute(boolean paused) {
        return !paused;
    }
}