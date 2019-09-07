package sources;

import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback.*;
import org.lwjgl.*;

import java.io.File;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

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
    private File sndName;
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

    Input(int k, int a, GameObject o, File snd) {
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

    public void execute() {
        if (purpose == purpose.Create) {
            // Create Object
        }
        else if (purpose == purpose.Destroy) {
            // Destroy object
        }
        else if (purpose == purpose.MoveLeft) {
            System.out.println("Moving Left!");
            sounds.setFileName("/Users/stevie_damrel/Desktop/Fall 2019/Gaming Frameworks/GamingFrameworks/audio-files/oof.ogg");
            sounds.loadPlaySound();
        }
        else if (purpose == purpose.MoveRight) {
            // Move Right
            System.out.println("Moving Right!");
        }
        else if (purpose == purpose.MoveUp) {
            // Move Up
            System.out.println("Moving Up!");
        }
        else if (purpose == purpose.MoveDown) {
            // Move Down
            System.out.println("Moving Down!");
        }
        else if (purpose == purpose.PlaySound) {
            // Play Sound
            System.out.println("Bwoop!");
        }
    }

}
