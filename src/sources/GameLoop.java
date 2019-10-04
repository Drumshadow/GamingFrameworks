package sources;

import org.ini4j.Ini;
import org.lwjgl.opengl.GL;
import sources.HUDcode.HUD;
import sources.HUDcode.HealthBar;
import sources.HUDcode.Score;
import sources.objCode.GameObject;
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
    java.awt.Font font = new java.awt.Font(MONOSPACED, PLAIN, 16);

    //private GameRoom room = new GameRoom("test", "./sprites/clouds_bkg.png");
    private GameRoom room = new GameRoom();

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

        // set background
        room.setBackground(new Sprite("./sprites/clouds_bkg.png"));

        /*==================================================
                          Object Creation
        ==================================================*/

        // player
        GameObject player = new GameObject("player", "./sprites/friend.png",
                true, false, 1.0, 10, 0.0, 0, 200.0, 1000.0);
        room.addObject(player);

        // grass platforms
        GameObject grass1 = new GameObject("grass", "./sprites/grass.png",
                true, false, 0.0, 0.0, 0.0, 0, 0.0, 500.0);
        room.addObject(grass1);

        GameObject grass2 = new GameObject("grass", "./sprites/grass.png",
                true, false, 0.0, 0.0, 0.0, 0, 400.0, 500.0);
        room.addObject(grass2);

        GameObject grass3 = new GameObject("grass", "./sprites/grass.png",
                true, false, 0.0, 0.0, 0.0, 0, 800.0, 660.0);
        room.addObject(grass3);

        GameObject grass4 = new GameObject("grass", "./sprites/grass.png",
                true, false, 0.0, 0.0, 0.0, 0, 1200.0, 660.0);
        room.addObject(grass4);

        GameObject grass5 = new GameObject("grass", "./sprites/grass.png",
                true, false, 0.0, 0.0, 0.0, 0, 1600.0, 660.0);
        room.addObject(grass5);

        // stone wall
        GameObject wall = new GameObject("wall", "./sprites/wall.png",
                true, false, 0.0, 0.0, 0.0, 0, 1400.0, 820.0);
        room.addObject(wall);

        // foe
        GameObject foe = new GameObject("foe", "./sprites/foe.png",
                true, false, 1.0, 0.0, 0.0, 0, 1000.0, 830.0);
        room.addObject(foe);

        // flower
        GameObject flower = new GameObject("flower", "./sprites/flower.png",
                true, false, 0.0, 0.0, 0.0, 0, 1700.0, 820.0);
        room.addObject(flower);

        /*==================================================
                                HUD
        ==================================================*/
        HUD hud = new HUD();

        hud.addElement(new HealthBar(true, HealthBar.healthType.BAR, 3, 3,
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
                        room.getElement(ini.get("input" + i, "object")),
                        ini.get("input" + i, "purpose")));
            }
            else if (ini.get("input" + i, "purpose").equals("MoveX") ||
                    ini.get("input" + i, "purpose").equals("MoveY") ||
                    ini.get("input" + i, "purpose").equals("Jump")) {

                inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                        Integer.parseInt(ini.get("input" + i, "action")),
                        room.getElement(ini.get("input" + i, "object")),
                        ini.get("input" + i, "purpose"),
                        Double.parseDouble(ini.get("input" + i, "speed"))));
            }
            else if (ini.get("input" + i, "purpose").equals("PlaySound")) {

                Audio a = new Audio();
                a.setFileName(ini.get("input" + i, "audio"));

                inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                        Integer.parseInt(ini.get("input" + i, "action")) | GLFW_PRESS,
                        room.getElement(ini.get("input" + i, "object")),
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
                        room.getElement(iniC.get("input" + i, "object")),
                        iniC.get("input" + i, "purpose")));
            }
            else if (iniC.get("input" + i, "purpose").equals("MoveX") ||
                    iniC.get("input" + i, "purpose").equals("MoveY")) {

                controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                        Integer.parseInt(iniC.get("input" + i, "index")),
                        Float.parseFloat(iniC.get("input" + i, "range")),
                        room.getElement(iniC.get("input" + i, "object")),
                        iniC.get("input" + i, "purpose"),
                        Double.parseDouble(iniC.get("input" + i, "speed"))));
            }
            else if (iniC.get("input" + i, "purpose").equals("PlaySound")) {

                Audio a = new Audio();
                a.setFileName(iniC.get("input" + i, "audio"));

                controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                        Integer.parseInt(iniC.get("input" + i, "index")),
                        Float.parseFloat(iniC.get("input" + i, "range")),
                        room.getElement(iniC.get("input" + i, "object")),
                        iniC.get("input" + i, "purpose"),
                        a));
            }
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

        while ( !glfwWindowShouldClose(newWindow.window) ) {
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
                    System.out.println("FPS: " + frames);
                    frames = 0;
                }
            }

            // draw background (scale to fit window)
            glPushMatrix();
                glScaled(1.256, 1.256, 1.0);
                room.getBackground().drawObject(0.0, 0.0);
            glPopMatrix();

            // draw HUD
            hud.drawHUD();

            // demonstrate hp bar
            if (player.getHitBox().xCollisionCheck(foe.getHitBox())) {

                ((HealthBar) hud.getElements().get(0)).decHealth();
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