package sources;

import org.lwjgl.openal.ALUtil;
import org.lwjgl.openal.*;
import sources.objCode.GameObject;
import sources.GameRoom;

class MultithreadingDemo implements Runnable
{
    private Input.purpose x;

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
    enum purpose{Create, Destroy, MoveX, MoveY, PlaySound, Jump, Pause};
    private purpose purpose;
    private String obj;
    private double speed;
    private String sndName;
    private Audio sounds = new Audio();
    private MultithreadingDemo object = new MultithreadingDemo();

    Input(int k, int a, String pur) {
        key = k;
        action = a;
        purpose = purpose.Pause;
    }

    Input(int k, int a, String o, String pur) {
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

    Input(int k, int a, String o, String pur, double s) {
        key = k;
        action = a;
        obj = o;

        switch(pur) {

            case "MoveX":
                purpose = purpose.MoveX;
                break;

            case "MoveY":
                purpose = purpose.MoveY;
                break;

            case "Jump":
                purpose = purpose.Jump;
                break;
        }
/*
        if (pur.equals("MoveX")) {
            purpose = purpose.MoveX;

        }
        else if (pur.equals("MoveY")) {
            purpose = purpose.MoveY;
        }*/
        speed = s / 1000.0;
    }

    Input(int k, int a, String o, String pur, Audio snd) {
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
    public purpose getPurpose() { return purpose; }

    public void execute(GameRoom room) {

        switch(purpose) {

            case Create:
                for (int i = 0; i < room.getAllObjects().size(); i++) {
                    if (room.getElement(i).getObjName().equals(obj)) {
                        room.addObject(room.getElement(i));
                        break;
                    }
                }
                break;

            case Destroy:
                for (int i = 0; i < room.getAllObjects().size(); i++) {
                    if (room.getElement(i).getObjName().equals(obj)) {
                        room.removeObject(room.getElement(i));
                        break;
                    }
                }
                break;

            case MoveX:
                for (int i = 0; i < room.getAllObjects().size(); i++) {
                    if (room.getElement(i).getObjName().equals(obj)) {
                        room.getElement(i).setXSpeed(speed);
                    }
                }
                break;

            case MoveY:
                for (int i = 0; i < room.getAllObjects().size(); i++) {
                    if (room.getElement(i).getObjName().equals(obj)) {
                        room.getElement(i).setYSpeed(speed);
                    }
                }
                break;

            case PlaySound:
                sounds.loadPlaySound();
                break;

            case Jump:
                for (int i = 0; i < room.getAllObjects().size(); i++) {
                    if (room.getElement(i).getObjName().equals(obj)) {
                        room.getElement(i).objectJump(room.getAllObjects());
                    }
                }
                break;
        }
    }

    public boolean execute(boolean paused) { return !paused; }
}