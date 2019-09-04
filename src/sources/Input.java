package sources;

import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback.*;


public class Input {
    private int key;  // 0-256? Not sure
    private int action; // 0, 1, 2
    enum purpose{Create, Destroy, MoveLeft, MoveRight, MoveUp, MoveDown, PlaySound};
    private purpose purpose;
    String objName; //Most likely temporary till we make an actual object class
    double speed;
    String sndName;

    Input(int k, int a, String oN) {
        key = k;
        action = a;
        objName = oN;
    }

    public void setCoD(String pur) {
        if (pur.equals("Create")) {
            purpose = purpose.Create;
        }
        else if (pur.equals("Destroy")) {
            purpose = purpose.Destroy;
        }
    }
    public void setMove(String dir, double s) {
        if (dir.equals("Left")) {
            purpose = purpose.MoveLeft;
        }
        else if (dir.equals("Right")) {
            purpose = purpose.MoveRight;
        }
        else if (dir.equals("Up")) {
            purpose = purpose.MoveUp;
        }
        else if (dir.equals("Down")) {
            purpose = purpose.MoveDown;
        }
        speed = s;
    }
    public void setPlaySound(String s) {
        purpose = purpose.PlaySound;
        sndName = s;
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
        }
        else if (purpose == purpose.MoveRight) {
            // Move Right
            System.out.println("Moving Right!");
        }
        else if (purpose == purpose.MoveUp) {
            // Move Up
        }
        else if (purpose == purpose.MoveDown) {
            // Move Down
        }
        else if (purpose == purpose.PlaySound) {
            // Play Sound
            System.out.println("Bwoop!");
        }
    }

}
