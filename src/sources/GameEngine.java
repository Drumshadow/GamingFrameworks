package sources;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GameEngine {
    enum COMMANDS{
        Quit,
        Sound,
        Music,
        Play;
    }

    public static void main(String[] args) {
        InitWindow newWindow = new InitWindow();

        newWindow.init();

        while( !glfwWindowShouldClose(newWindow.window) ) {

        }

        glfwTerminate();
    }
}
