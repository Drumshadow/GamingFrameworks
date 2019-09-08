package sources;

import sources.objCode.GameObject;
import sources.objCode.ObjectList;

class MultithreadingDemo implements Runnable
{
    Input.purpose x;
    @Override
    public void run() {
        Audio sounds = new Audio();
        if(x == Input.purpose.MoveLeft){
            if(sounds.fileName == null) {
                sounds.setFileName("./audio-files/oof.ogg");
            }
            sounds.loadPlaySound();
        }
    }
}

public class Input {
    private int key;  // 0-256? Not sure
    private int action; // 0, 1, 2
    enum purpose{Create, Destroy, MoveLeft, MoveRight, MoveUp, MoveDown, PlaySound};
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

    Input(int k, int a, String pur, GameObject o, double s) {
        key = k;
        action = a;
        obj = o;
        if (pur.equals("Left")) {
            purpose = purpose.MoveLeft;
            object.run();

        }
        else if (pur.equals("Right")) {
            purpose = purpose.MoveRight;
        }
        else if (pur.equals("Up")) {
            purpose = purpose.MoveUp;
        }
        else if (pur.equals("Down")) {
            purpose = purpose.MoveDown;
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

    // the moves prob arent perfect yet
    public void execute(ObjectList roomObjects ) {
        if (purpose == purpose.Create) {
            // Create Object
            roomObjects.addObject(new GameObject(obj));
        }
        else if (purpose == purpose.Destroy) {
            // Destroy object
        }
        else if (purpose == purpose.MoveLeft) {
            System.out.println("Moving Left!");
            object.x = purpose.MoveLeft;
            object.run();
            obj.move(roomObjects);
        }
        else if (purpose == purpose.MoveRight) {
            // Move Right
            obj.move(roomObjects);
        }
        else if (purpose == purpose.MoveUp) {
            // Move Up
            obj.move(roomObjects);
        }
        else if (purpose == purpose.MoveDown) {
            // Move Down
            obj.move(roomObjects);
        }
        else if (purpose == purpose.PlaySound) {
            // Play Sound
            if(sounds.fileName == null) {
                sounds.setFileName("./audio-files/oof.ogg");
            }
            sounds.loadPlaySound();
        }
    }

}
