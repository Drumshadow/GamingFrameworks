package sources;

import org.ini4j.Ini;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import sources.HUDcode.HUD;
import sources.objCode.GameObject;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import java.io.IOException;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;

public class GameLoop {

    private InitWindow newWindow = new InitWindow();
    private InputList inputs = new InputList();
    private Audio bg = new Audio();
    private ControllerList controls = new ControllerList();
    private HUD hud = new HUD();
    private EventHandler events = new EventHandler();
    private INI pivotalMoment = new INI();

    private GameRoom room = new GameRoom();
    private boolean isPaused = false;


    private void paused(int key, int action){
        glfwWaitEvents();
    }

    private void run() throws Exception {
        Ini ini = new Ini(new File("./options/options.ini"));
        bg.playSound(ini.get("background", "music"));
        newWindow.init();

        // initialization
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        long device = alcOpenDevice(defaultDeviceName);
        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        long context = alcCreateContext(device, (IntBuffer)null);
        alcMakeContextCurrent(context);
        ALCapabilities alCapabilities  = AL.createCapabilities(alcCapabilities);

        // setup key callback
        // called every time a key is pressed, repeated or released
        glfwSetKeyCallback(newWindow.window, (window, key, scanCode, action,
                                              mods) -> {
            if(key == GLFW_KEY_O && action == GLFW_PRESS && isPaused){
                inputs.removeAll();
                controls.removeAll();
                room.getAllObjects().removeAllElements();
                System.out.println("O");
                isPaused = false;
                try {
                    pivotalMoment.renderObjects(room, events);
                    pivotalMoment.setControls(controls, events);
                    pivotalMoment.setKeyboardControls(inputs, events);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                key = GLFW_KEY_X;
                //TODO: Render everything back
            }
            for (int i = 0; i < inputs.size(); i++) {

                if (key == inputs.get(i).getKey() &&
                        action == inputs.get(i).getAction()) {
                    if (inputs.get(i).getPurpose() == Input.inputPurpose.PAUSE) {
                        isPaused = inputs.get(i).execute(isPaused);
                    }
                    else {
                        inputs.get(i).execute(room);
                    }
                }

                else if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, true);
                }

                else if(key == GLFW_KEY_P && action == GLFW_PRESS && !isPaused){
                    isPaused = true;
                    System.out.println("P");
                    paused(key, action);
                }
            }
        });

        loop();

        alcDestroyContext(context);
        alcCloseDevice(device);

        // free window callbacks and destroy window
        glfwFreeCallbacks(newWindow.window);
        glfwDestroyWindow(newWindow.window);

        // terminate GLFW and free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
  
    private void loop() throws IOException, SlickException {

        /*==================================================
                         Initialize Game
        ==================================================*/

        GL.createCapabilities();
        Ini ini = new Ini(new File("./options/options.ini"));

        // set background
        room.setBackground(new Sprite(ini.get("background", "art"), 1));

        // set hud
        hud.setHudFont(ini.get("misc", "font"), 32);
        pivotalMoment.renderHUD(hud);

        // get other game components
        pivotalMoment.renderEvents(events);
        pivotalMoment.setKeyboardControls(inputs, events);
        pivotalMoment.setControls(controls, events);
        pivotalMoment.renderObjects(room, events);

        /*==================================================
                          Game Loop
        ==================================================*/

        GL11.glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glViewport(0,0,1000,1000);

        // initialize frame-related variables
        double frame_cap = 1.0/60.0;
        double frame_time = 0;
        int frames = 0;
        double time = FPS.getTime();
        double unprocesses = 0;
        double time_2;
        double passed;
        int displayFrames = 0;


        while ( !glfwWindowShouldClose(newWindow.window)) {
            if (!isPaused) {
                glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

            /*==================================================
                                 Frames
            ==================================================*/

                time_2 = FPS.getTime();
                passed = time_2 - time;
                unprocesses += passed;
                frame_time += passed;

                time = time_2;

                while (unprocesses >= frame_cap) {
                    unprocesses -= frame_cap;
                    glfwPollEvents();
                    if (frame_time >= 1.0) {
                        frame_time = 0;
                        displayFrames = frames;
                        frames = 0;
                    }
                }

                // draw background (scale to fit window)
                glPushMatrix();
                glScaled(1.25, 1.25, 1.0);
                room.getBackground().drawObject(-0.8, -0.8);
                glPopMatrix();

                for (GameObject GO : room.getAllObjects()) {
                    GO.drawObject();
                    GO.move(room);
                }

                // draw HUD
                hud.drawHUD();

                // Execute events
                for (int i = 0; i < events.size(); i++) {
                    events.getEvent(i).execute(room, hud, displayFrames);
                }

                // TODO: make sure controller works
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

            }
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws Exception {
        new GameLoop().run();
    }
}