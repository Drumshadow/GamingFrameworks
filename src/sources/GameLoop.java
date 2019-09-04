package sources;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GameLoop {

    // The window handle
     private InitWindow newWindow = new InitWindow();

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        newWindow.init();
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