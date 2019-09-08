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

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        newWindow.init();

        InputList inputs = new InputList();
        ObjectList objects = new ObjectList();

        GameObject mario = new GameObject("Mario", "./sprites/mario.jpg", 413, 550, true, 9.8, 10, 7, 0);
        mario.drawObject();
        inputs.add(new Input(GLFW_KEY_A, GLFW_PRESS, "Left", mario, 2));


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

        // Set the clear color
        // glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        float red = 1, green = 0, blue = 0;

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.

        while ( !glfwWindowShouldClose(newWindow.window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

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