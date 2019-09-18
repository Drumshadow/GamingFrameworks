package sources;

import sources.objCode.GameObject;
import sources.objCode.ObjectList;

class MultithreadingDemo implements Runnable
{
    Input.purpose x;
    @Override
    public void run() {
        Audio sounds = new Audio();
        if(x == Input.purpose.MoveX){
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
    enum purpose{Create, Destroy, MoveX, MoveY, PlaySound};
    private purpose purpose;
    private GameObject obj;
    private double speed;
    private String sndName;
    private Audio sounds = new Audio();
    private MultithreadingDemo object = new MultithreadingDemo();

    Input(int k, int a, GameObject o, String pur) {
        key = k;
        action = a;
        obj = o;
        if (pur.equals("Create")) {
            purpose = purpose.Create;
        }
        else if (pur.equals("Destroy")) {
            purpose = purpose.Destroy;
        }
    }

    Input(int k, int a, GameObject o, String pur, double s) {
        key = k;
        action = a;
        obj = o;
        if (pur.equals("MoveX")) {
            purpose = purpose.MoveX;

        }
        else if (pur.equals("MoveY")) {
            purpose = purpose.MoveY;
        }
        speed = s;
    }

    Input(int k, int a, GameObject o, String pur, Audio snd) {
        key = k;
        action = a;
        obj = o;
        purpose = purpose.PlaySound;
        sounds = snd;
    }

    public int getKey() {
        return key;
    }
    public int getAction() {
        return action;
    }

    public void execute(ObjectList roomObjects ) {

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