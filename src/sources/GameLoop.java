package sources;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import sources.HUDcode.HUD;
import sources.HUDcode.HealthBar;
import sources.HUDcode.Score;

import javax.swing.text.Position;
import java.awt.*;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import java.io.IOException;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

public class GameLoop {

    private InitWindow newWindow = new InitWindow();
    private InputList inputs = new InputList();
    private Audio bg = new Audio();
    private ControllerList controls = new ControllerList();
    private INI pivotalMoment = new INI();

    //private GameRoom room = new GameRoom("test", "./sprites/clouds_bkg.png");
    private GameRoom room = new GameRoom();
    private boolean isPaused = false;


    public void paused(int key, int action){
        glfwWaitEvents();
    }

    private void run() throws Exception {
        bg.playSound("./music/omae_wa_mou.wav");
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
                    pivotalMoment.renderObjects(room);
                    pivotalMoment.setControls(controls);
                    pivotalMoment.setKeyboardControls(inputs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                key = GLFW_KEY_X;
                //TODO: Render everything back
            }
            for (int i = 0; i < inputs.size(); i++) {

                if (key == inputs.get(i).getKey() &&
                        action == inputs.get(i).getAction()) {

                    inputs.get(i).execute(room);
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
        pivotalMoment.setKeyboardControls(inputs);

        /*==================================================
                        Controller Inputs
        ==================================================*/
        pivotalMoment.setControls(controls);

        /*==================================================
                          Object Creation
        ==================================================*/
        pivotalMoment.renderObjects(room);

        // allow foe to auto-move
        room.getElement("foe").setXSpeed(5.0/1000.0);

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
        //GL11.glMatrixMode(GL11.GL_MODELVIEW);

        //GL11.glMatrixMode(GL_PROJECTION);
        //GL11.glLoadIdentity();
        //GL11.glOrtho(0, 1000, 1000, 0, -100, 100);
        //GL11.glMatrixMode(GL_MODELVIEW);
        double frame_cap = 1.0/60.0;
        double frame_time = 0;
        int frames = 0;
        double time = FPS.getTime();
        double unprocesses = 0;
        double time_2 = FPS.getTime();
        double passed = time_2 - time;
        int displayFrames = 0;

        UnicodeFont font;
        Font awtFont = new Font(Font.DIALOG, Font.PLAIN, 48);
        font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(java.awt.Color.BLACK));
        font.loadGlyphs();

        while ( !glfwWindowShouldClose(newWindow.window)) {
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
            time_2 = FPS.getTime();
            passed = time_2 - time;
            unprocesses+=passed;
            frame_time += passed;

            time = time_2;

            while(unprocesses >= frame_cap){
                unprocesses-=frame_cap;
                glfwPollEvents();
                if(frame_time >= 1.0){
                    frame_time = 0;
                    //System.out.println("FPS: " + frames);
                    displayFrames = frames;
                    frames = 0;
                }
            }

            // draw background (scale to fit window)
            glPushMatrix();
                glScaled(1.25, 1.25, 1.0);
                room.getBackground().drawObject(-0.8, -0.8);
            glPopMatrix();

            // demonstrate hp bar
            if (room.getElement("player").getHitBox().basicCollision(room.getElement("foe").getHitBox()) ||
                    room.getElement("foe").getHitBox().basicCollision(room.getElement("player").getHitBox())) {

                ((HealthBar) hud.getElements().get(0)).decHealth();
            }

            // demonstrate flower healing power
            if (room.getElement("player").getHitBox().basicCollision(room.getElement("flower").getHitBox())) {

                ((HealthBar) hud.getElements().get(0)).incHealth(5);

                // TODO: consume flower after use
            }

            // demonstrate basic AI
            if (room.getElement("foe").getHitBox().basicCollision(room.getElement("wall").getHitBox())) {
                room.getElement("foe").setXSpeed(room.getElement("foe").getXSpeed() * -1.0);
            }

            // draw objects
            for(int i = 0; i < room.objectCount(); i++) {

                room.getElement(i).drawObject();
                room.getElement(i).move(room.getAllObjects());
            }

            // draw HUD
            hud.drawHUD();

            glPushMatrix();
            GL11.glOrtho(0, 1000, 1000, 0, -100, 100);
            font.drawString(400, 200, "Frames: " + displayFrames);
            glPopMatrix();

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