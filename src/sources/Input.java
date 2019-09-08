package sources;

import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback.*;
import org.lwjgl.*;

import java.io.File;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Vector;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.libc.LibCStdlib.free;


public class Input {
    private int key;  // 0-256? Not sure
    private int action; // 0, 1, 2
    enum purpose{Create, Destroy, MoveLeft, MoveRight, MoveUp, MoveDown, PlaySound};
    private purpose purpose;
    private GameObject obj;
    private double speed;
    private String sndName;
    Audio sounds = new Audio();

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

    Input(int k, int a, GameObject o, String pur, String snd) {
        key = k;
        action = a;
        obj = o;
        purpose = purpose.PlaySound;
        sndName = snd;
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
            sounds.setFileName(sndName);
            sounds.loadPlaySound();
        }
    }

}
