package sources;

import org.ini4j.Ini;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import sources.HUDcode.HUD;
import sources.HUDcode.HealthBar;
import sources.HUDcode.Score;
import sources.objCode.GameObject;

import java.awt.*;
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

    private InitWindow newWindow = new InitWindow();
    private InputList inputs = new InputList();
    private Audio bg = new Audio();
    private ControllerList controls = new ControllerList();

    //private GameRoom room = new GameRoom("test", "./sprites/clouds_bkg.png");
    private GameRoom room = new GameRoom();
    private boolean isPaused = false;


    public void paused(int key){
        glfwWaitEvents();
        while(isPaused){
            glfwPollEvents();
        }
    }

    private void run() throws Exception {
        bg.playSound("./music/omae_wa_mou.wav");
        newWindow.init();

        // setup key callback
        // called every time a key is pressed, repeated or released
        glfwSetKeyCallback(newWindow.window, (window, key, scanCode, action,
                                              mods) -> {

            for (int i = 0; i < inputs.size(); i++) {
                if (key == inputs.get(i).getKey() &&
                        action == inputs.get(i).getAction()) {

                    inputs.get(i).execute(room);
                }

                else if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, true);
                }

                else if(key == GLFW_KEY_O && action == GLFW_PRESS && isPaused){
                    System.out.println("O");
                    isPaused = false;
                }

                else if(key == GLFW_KEY_P && action == GLFW_PRESS && !isPaused){
                    isPaused = true;
                    System.out.println("P");
                    paused(key);
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

        // set background
        room.setBackground(new Sprite("./sprites/clouds_bkg.png"));

        /*==================================================
                                HUD
        ==================================================*/
        HUD hud = new HUD();

        hud.addElement(new HealthBar(true, HealthBar.healthType.BAR, 10, 10,
                null, -0.9f, 0.85f, 0.5f, 0.05f));
        hud.addElement(new Score(true, -100, 0, 5, 5, 0, 100));

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
                        ini.get("input" + i, "object"),
                        ini.get("input" + i, "purpose")));
            }
            else if (ini.get("input" + i, "purpose").equals("MoveX") ||
                    ini.get("input" + i, "purpose").equals("MoveY") ||
                    ini.get("input" + i, "purpose").equals("Jump")) {

                inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                        Integer.parseInt(ini.get("input" + i, "action")),
                        ini.get("input" + i, "object"),
                        ini.get("input" + i, "purpose"),
                        Double.parseDouble(ini.get("input" + i, "speed"))));
            }
            else if (ini.get("input" + i, "purpose").equals("PlaySound")) {

                Audio a = new Audio();
                a.setFileName(ini.get("input" + i, "audio"));

                inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                        Integer.parseInt(ini.get("input" + i, "action")) | GLFW_PRESS,
                        ini.get("input" + i, "object"),
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
                        iniC.get("input" + i, "object"),
                        iniC.get("input" + i, "purpose")));
            }
            else if (iniC.get("input" + i, "purpose").equals("MoveX") ||
                    iniC.get("input" + i, "purpose").equals("MoveY")) {

                controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                        Integer.parseInt(iniC.get("input" + i, "index")),
                        Float.parseFloat(iniC.get("input" + i, "range")),
                        iniC.get("input" + i, "object"),
                        iniC.get("input" + i, "purpose"),
                        Double.parseDouble(iniC.get("input" + i, "speed"))));
            }
            else if (iniC.get("input" + i, "purpose").equals("PlaySound")) {

                Audio a = new Audio();
                a.setFileName(iniC.get("input" + i, "audio"));

                controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                        Integer.parseInt(iniC.get("input" + i, "index")),
                        Float.parseFloat(iniC.get("input" + i, "range")),
                        iniC.get("input" + i, "object"),
                        iniC.get("input" + i, "purpose"),
                        a));
            }
        }

        /*==================================================
                          Object Creation
        ==================================================*/

        Ini iniO = new Ini(new File("./objects/objects.ini"));
        inputNum = Integer.parseInt(iniO.get("control", "objects"));
        for (int i = 0; i < inputNum; i++) {
            room.addObject(new GameObject(iniO.get("object" + i, "name"),
                    iniO.get("object" + i, "sprPath"),
                    Boolean.parseBoolean(iniO.get("object" + i, "collide")),
                    Boolean.parseBoolean(iniO.get("object" + i, "fear")),
                    Double.parseDouble(iniO.get("object" + i, "weight")),
                    Double.parseDouble(iniO.get("object" + i, "tv")),
                    Double.parseDouble(iniO.get("object" + i, "jump")),
                    Integer.parseInt(iniO.get("object" + i, "boxType")),
                    Double.parseDouble(iniO.get("object" + i, "x")),
                    Double.parseDouble(iniO.get("object" + i, "y"))));
        }

        /*==================================================
                          Game Loop
        ==================================================*/

        glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
        double frame_cap = 1.0/60.0;
        double frame_time = 0;
        int frames = 0;
        double time = FPS.getTime();
        double unprocesses = 0;

        while ( !glfwWindowShouldClose(newWindow.window)) {
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
            boolean can_render = false;
            double time_2 = FPS.getTime();
            double passed = time_2 - time;
            unprocesses+=passed;
            frame_time += passed;

            time = time_2;


            while(unprocesses >= frame_cap){
                unprocesses-=frame_cap;
                can_render = true;
                glfwPollEvents();
                if(frame_time >= 1.0){
                    frame_time = 0;
                    //System.out.println("FPS: " + frames);
                    frames = 0;
                }
            }

            // draw background (scale to fit window)
            glPushMatrix();
                glScaled(1.25, 1.25, 1.0);
                room.getBackground().drawObject(0.0, 0.0);
            glPopMatrix();

            // draw HUD
            hud.drawHUD();

            // demonstrate hp bar
            if (room.getElement("player").getHitBox().basicCollision(room.getElement("foe").getHitBox())) {

                ((HealthBar) hud.getElements().get(0)).decHealth();
            }

            // demonstrate flower healing power
            if (room.getElement("player").getHitBox().basicCollision(room.getElement("flower").getHitBox())) {

                ((HealthBar) hud.getElements().get(0)).incHealth(5);

                // TODO: consume flower after use
            }

            // draw objects
            for(int i = 0; i < room.objectCount(); i++) {

                room.getElement(i).drawObject();
                room.getElement(i).move(room.getAllObjects());
            }

            if (glfwGetJoystickName(GLFW_JOYSTICK_1) != null) {

                FloatBuffer axes = glfwGetJoystickAxes(GLFW_JOYSTICK_1);
                ByteBuffer buttons = glfwGetJoystickButtons(GLFW_JOYSTICK_1);

                for (int i = 0; i < controls.size(); i++) {

                    if (controls.get(i).getButton() == 1) {
                        if (buttons.get(controls.get(i).getIndex()) == 1) {
                            controls.get(i).execute(room);
                        }

                    } else if (controls.get(i).getButton() == 0) {

                        if (controls.get(i).getRange() < 0 &&
                                axes.get(controls.get(i).getIndex()) <
                                        controls.get(i).getRange()) {

                            controls.get(i).execute(room);

                        } else if (controls.get(i).getRange() >= 0 &&
                                axes.get(controls.get(i).getIndex()) >
                                        controls.get(i).getRange()) {

                            controls.get(i).execute(room);
                        }
                    }
                }
            }

            glfwSwapBuffers(newWindow.window); // swap the color buffers
            frames++;

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception {
        new GameLoop().run();
    }
}