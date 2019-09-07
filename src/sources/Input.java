package sources;

import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback.*;
import org.lwjgl.*;

import java.io.File;


public class Input {
    private int key;  // 0-256? Not sure
    private int action; // 0, 1, 2
    enum purpose{Create, Destroy, MoveLeft, MoveRight, MoveUp, MoveDown, PlaySound};
    private purpose purpose;
    private String objName; //Most likely temporary till we make an actual object class
    private double speed;
    private File sndName;

    Input(int k, int a, String pur) {
        key = k;
        action = a;
        // objName = oN;
        if (pur.equals("Create")) {
            purpose = purpose.Create;
        }
        else if (pur.equals("Destroy")) {
            purpose = purpose.Destroy;
        }
    }

    Input(int k, int a, String pur, double s) {
        key = k;
        action = a;
        // objName = oN;
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

    Input(int k, int a, File snd) {
        key = k;
        action = a;
        //objName = oN;
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
            // Create object
        }
        else if (purpose == purpose.Destroy) {
            // Destroy object
        }
        else if (purpose == purpose.MoveLeft) {
            // Move Left
            System.out.println("Moving Left!");


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
