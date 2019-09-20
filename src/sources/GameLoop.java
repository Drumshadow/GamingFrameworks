package sources;

import org.ini4j.Ini;
import org.lwjgl.opengl.GL;
import sources.objCode.GameObject;
import sources.objCode.ObjectList;
import org.lwjgl.glfw.GLFWGamepadState;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import java.io.File;
import java.io.IOException;

import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameLoop {

    // window handle
    private InitWindow newWindow = new InitWindow();
    private InputList inputs = new InputList();
    private ObjectList objects = new ObjectList();
    private Audio bg = new Audio();
    private ControllerList controls = new ControllerList();
    java.awt.Font font = new java.awt.Font(MONOSPACED, PLAIN, 16);


    private void run() throws Exception {
        bg.playSound("./music/PTheme.wav");
        newWindow.init();

        // setup key callback
        // called every time a key is pressed, repeated or released
        glfwSetKeyCallback(newWindow.window, (window, key, scanCode, action,
                                              mods) -> {

            for (int i = 0; i < inputs.size(); i++) {
                if (key == inputs.get(i).getKey() &&
                        action == inputs.get(i).getAction()) {

                    inputs.get(i).execute(objects);
                }

                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        });

        loop();

        // free window callbacks and destroy window
        glfwFreeCallbacks(newWindow.window);
        glfwDestroyWindow(newWindow.window);

        // terminate GLFW and free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
  
    private void loop() throws IOException {
        GL.createCapabilities();

        /*==================================================
                          Mario Creation
        ==================================================*/

        GameObject mario = new GameObject("Mario", "./sprites/mario.jpg",
                true, 0.0, 0.0, 0.0, 0, 200.0, 1000.0);
        objects.addObject(mario);

        GameObject wario = new GameObject("Wario", "./sprites/mario.jpg",
                true, 0.0, 0.0, 0.0, 0, 0.0, 500.0);
        objects.addObject(wario);

        GameObject floor1 = new GameObject("floor", "./sprites/mario.jpg",
                true, 0.0, 0.0, 0.0, 0, 400.0, 500.0);
        objects.addObject(floor1);

        GameObject floor2 = new GameObject("floor", "./sprites/mario.jpg",
                true, 0.0, 0.0, 0.0, 0, 200.0, 500.0);
        objects.addObject(floor2);

        GameObject floor3 = new GameObject("floor", "./sprites/mario.jpg",
                true, 0.0, 0.0, 0.0, 0, 600.0, 500.0);
        objects.addObject(floor3);

        GameObject wall = new GameObject("wall", "./sprites/mario.jpg",
                true, 0.0, 0.0, 0.0, 0, 800.0, 700.0);
        objects.addObject(wall);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        mario.getSprite().texture.bind();
        wario.getSprite().texture.bind();
        floor1.getSprite().texture.bind();
        floor2.getSprite().texture.bind();
        floor3.getSprite().texture.bind();
        wall.getSprite().texture.bind();

        /*==================================================
                          Keyboard Inputs
        ==================================================*/

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

        /*==================================================
                        Controller Inputs
        ==================================================*/

        Ini iniC = new Ini(new File("./inputs/controller.ini"));
        inputNum = Integer.parseInt(iniC.get("control", "inputs"));

        for (int i = 0; i < inputNum; i++) {
            if (iniC.get("input" + i, "purpose").equals("Create") ||
                    iniC.get("input" + i, "purpose").equals("Destroy")) {

                controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                        Integer.parseInt(iniC.get("input" + i, "index")),
                        Float.parseFloat(iniC.get("input" + i, "range")),
                        objects.getElement(iniC.get("input" + i, "object")),
                        iniC.get("input" + i, "purpose")));
            }
            else if (iniC.get("input" + i, "purpose").equals("MoveX") ||
                    iniC.get("input" + i, "purpose").equals("MoveY")) {

                controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                        Integer.parseInt(iniC.get("input" + i, "index")),
                        Float.parseFloat(iniC.get("input" + i, "range")),
                        objects.getElement(iniC.get("input" + i, "object")),
                        iniC.get("input" + i, "purpose"),
                        Double.parseDouble(iniC.get("input" + i, "speed"))));
            }
            else if (iniC.get("input" + i, "purpose").equals("PlaySound")) {

                Audio a = new Audio();
                a.setFileName(iniC.get("input" + i, "audio"));

                controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                        Integer.parseInt(iniC.get("input" + i, "index")),
                        Float.parseFloat(iniC.get("input" + i, "range")),
                        objects.getElement(iniC.get("input" + i, "object")),
                        iniC.get("input" + i, "purpose"),
                        a));
            }
        }

        /*==================================================
                          Game Loop
        ==================================================*/

        float red = 1;
        float green = 0;
        float blue = 0;

        glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);

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

            if (glfwGetJoystickName(GLFW_JOYSTICK_1) != null) {

                FloatBuffer axes = glfwGetJoystickAxes(GLFW_JOYSTICK_1);
                ByteBuffer buttons = glfwGetJoystickButtons(GLFW_JOYSTICK_1);

                for (int i = 0; i < controls.size(); i++) {

                    if (controls.get(i).getButton() == 1) {
                        if (buttons.get(controls.get(i).getIndex()) == 1) {
                            controls.get(i).execute(objects);
                        }

                    } else if (controls.get(i).getButton() == 0) {

                        if (controls.get(i).getRange() < 0 &&
                                axes.get(controls.get(i).getIndex()) <
                                        controls.get(i).getRange()) {

                            controls.get(i).execute(objects);

                        } else if (controls.get(i).getRange() >= 0 &&
                                axes.get(controls.get(i).getIndex()) >
                                        controls.get(i).getRange()) {

                            controls.get(i).execute(objects);
                        }
                    }
                }
            }

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