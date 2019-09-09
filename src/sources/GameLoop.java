package sources;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import sources.objCode.GameObject;
import sources.objCode.ObjectList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameLoop {

    // The window handle
     private InitWindow newWindow = new InitWindow();
    InputList inputs = new InputList();
    ObjectList objects = new ObjectList();

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

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

        GameObject mario = new GameObject("Mario", "./sprites/mario.jpg", 413, 550, true, 0, 10, 7, 0);
        objects.addObject(mario);
        objects.getOList().get(0).getSprite().drawObject();
        inputs.add(new Input(GLFW_KEY_A, GLFW_PRESS, "MoveX", mario, 2.0));

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
                glColor4f(red, green, blue, 0);
                glVertex2f(-1f, 1f);
                glColor4f(blue, red, green, 0);
                glVertex2f(1f, 1f);
                glColor4f(green, blue, red, 0);
                glVertex2f(1f, -1f);
                glColor4f(red, green, blue, 0);
                glVertex2f(-1f, -1f);
            glEnd();

            glEnable(GL_TEXTURE_2D);

            glBegin(GL_QUADS);
            {
                glTexCoord2f(1.0f, 0.0f);
                glVertex2f(-0.5f, 0.5f);

                glTexCoord2f(1.0f, 1.0f);
                glVertex2f(-0.5f, -0.5f);

                glTexCoord2f(0.0f, 1.0f);
                glVertex2f(0.5f, -0.5f);

                glTexCoord2f(0.0f, 0.0f);
                glVertex2f(0.5f, 0.5f);
            }
            glEnd();

            glDisable(GL_TEXTURE_2D);

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

            System.out.println("Marios x: " + objects.getOList().get(0).getXSpeed());
            System.out.println("Marios y: " + objects.getOList().get(0).getYSpeed());
            System.out.println("How many marios: " + objects.getOList().size());

            glfwSwapBuffers(newWindow.window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new GameLoop().run();
    }

}