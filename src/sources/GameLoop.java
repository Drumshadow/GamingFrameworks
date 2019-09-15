package sources;

import org.ini4j.Ini;
import org.lwjgl.opengl.GL;
import sources.objCode.GameObject;
import sources.objCode.ObjectList;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameLoop {

    // The window handle
    private InitWindow newWindow = new InitWindow();
    private InputList inputs = new InputList();
    private ObjectList objects = new ObjectList();
    private Audio bg = new Audio();

    private void run() throws Exception {
        bg.playSound("./music/MemeVapor.wav");
        newWindow.init();

        // Setup a key callback.
        // It will be called every time a key is pressed, repeated or released.
        // Will use this section to handle inputs, don't delete plz
        glfwSetKeyCallback(newWindow.window, (window, key, scanCode, action, mods) -> {
            for (int i = 0; i < inputs.size(); i++) {
                if ( key == inputs.get(i).getKey() && action == inputs.get(i).getAction() )
                    inputs.get(i).execute(objects);
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(newWindow.window);
        glfwDestroyWindow(newWindow.window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
  
    private void loop() throws IOException {
        GL.createCapabilities();

        GameObject mario = new GameObject("Mario", "./sprites/mario.jpg",
                true, 0.0, 10, 7, 0, 600, 0.0);
        objects.addObject(mario);

        GameObject wario = new GameObject("Wario", "./sprites/mario.jpg",
                true, 0.0, 10, 7, 0, -100, 0.0);
        objects.addObject(wario);

        GameObject floorMario = new GameObject("floorMario", "./sprites/mario.jpg",
                true, 0.0, 0.0, 0.0, 0, 0.0, -800.0);
        objects.addObject(floorMario);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        mario.getSprite().texture.bind();
        wario.getSprite().texture.bind();
        floorMario.getSprite().texture.bind();

        Ini ini = new Ini(new File("./inputs/keyboard.ini"));
        int inputNum = Integer.parseInt(ini.get("control", "inputs"));
        for (int i = 0; i < inputNum; i++) {
            if (ini.get("input" + i, "purpose").equals("Create") ||
                    ini.get("input" + i, "purpose").equals("Destroy")) {
                inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                        Integer.parseInt(ini.get("input" + i, "action")),
                        objects.getElement(ini.get("input" + i, "object")),
                        ini.get("input" + i, "purpose")));
            }
            else if (ini.get("input" + i, "purpose").equals("MoveX") ||
                    ini.get("input" + i, "purpose").equals("MoveY")) {
                inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                        Integer.parseInt(ini.get("input" + i, "action")),
                        objects.getElement(ini.get("input" + i, "object")),
                        ini.get("input" + i, "purpose"),
                        Double.parseDouble(ini.get("input" + i, "speed"))));
            }
            else if (ini.get("input" + i, "purpose").equals("PlaySound")) {
                Audio a = new Audio();
                a.setFileName(ini.get("input" + i, "audio"));
                inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                        Integer.parseInt(ini.get("input" + i, "action")),
                        objects.getElement(ini.get("input" + i, "object")),
                        ini.get("input" + i, "purpose"),
                        a));
            }
        }

        float red = 1;
        float green = 0;
        float blue = 0;

        glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);

        // Game Loop
        while ( !glfwWindowShouldClose(newWindow.window) ) {
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

            glBegin(GL_QUADS);
            {
                glColor4f(red, green, blue, 0);
                glVertex2f(-1f, 1f);
                glColor4f(blue, red, green, 0);
                glVertex2f(1f, 1f);
                glColor4f(green, blue, red, 0);
                glVertex2f(1f, -1f);
                glColor4f(red, green, blue, 0);
                glVertex2f(-1f, -1f);
            }
            glEnd();

            for(int i = 0; i < objects.getOList().size(); i++) {
                objects.getOList().get(i).drawObject();
            }

            if(red > 0 && blue < 0) {
                red -= 0.01;
                green += 0.01;
            } else if (green > 0) {
                green -= 0.01;
                blue += 0.01;
            } else {
                blue -= 0.01;
                red += 0.01;
            }

            /*GLFWGamepadState state = new GLFWGamepadState(ByteBuffer.allocate(40));
            if (glfwGetGamepadState(GLFW_JOYSTICK_1, state)) {

            }*/

            glfwSwapBuffers(newWindow.window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception {
        new GameLoop().run();
    }
}