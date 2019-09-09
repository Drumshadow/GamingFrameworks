package sources;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import sources.objCode.GameObject;
import sources.objCode.ObjectList;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameLoop {

    // The window handle
     private InitWindow newWindow = new InitWindow();
    InputList inputs = new InputList();
    ObjectList objects = new ObjectList();
    Audio bg = new Audio();

    public void run() throws Exception {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        bg.playSound("./music/MemeVapor.wav");
        newWindow.init();

        //mario.drawObject();
        //inputs.add(new Input(GLFW_KEY_A, GLFW_PRESS, "Left", mario, 2));

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        // Will use this section to handle inputs, don't delete plz
        glfwSetKeyCallback(newWindow.window, (window, key, scancode, action, mods) -> {
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
  
    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        GameObject mario = new GameObject("Mario", "./sprites/mario.jpg", 413, 550, true, -9.8, 10, 7, 0);
        objects.addObject(mario);
        objects.getOList().get(0).drawObject();
        Audio ad = new Audio();
        Audio ab = new Audio();
        ad.setFileName("./audio-files/EPress.ogg");
        ab.setFileName("./audio-files/QPress.ogg");
        FloatBuffer axes = glfwGetJoystickAxes(GLFW_JOYSTICK_1);
        inputs.add(new Input(GLFW_KEY_A, GLFW_REPEAT, mario, "MoveX", -2.0));
        inputs.add(new Input(GLFW_KEY_D, GLFW_REPEAT, mario, "MoveX", 2.0));
        inputs.add(new Input(GLFW_KEY_Q, GLFW_PRESS, mario, "PlaySound", ad));
        inputs.add(new Input(GLFW_KEY_E, GLFW_PRESS, mario, "PlaySound", ab));


        GameObject wario = new GameObject("Wario", "./sprites/mario.jpg", 413, 550, true, -9.8, 10, 7, 0);
        objects.addObject(wario);
        objects.getOList().get(1).setX(700);
        objects.getOList().get(1).drawObject();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        mario.getSprite().texture.bind();

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

            //System.out.println("Marios x: " + objects.getOList().get(0).getXSpeed());
            //System.out.println("Marios y: " + objects.getOList().get(0).getYSpeed());
            //System.out.println("How many marios: " + objects.getOList().size());

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