package sources;

import static org.lwjgl.glfw.GLFW.*;

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
