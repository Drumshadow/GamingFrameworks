import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GameLoop {

    // The window handle
    private long window;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {

        ArrayList<Input> inputs = new ArrayList<>();
        Input test = new Input(GLFW_KEY_D, GLFW_PRESS, "Dummy");
        test.setPlaySound("testing");
        inputs.add(test);
        Input test2 = new Input(GLFW_KEY_A, GLFW_PRESS, "Dummy");
        test2.setMove("Right", 1);
        inputs.add(test2);

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

//        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//        int window_width = mode.width();
//        int window_height = mode.height();

        // Create the window
        window = glfwCreateWindow(600, 600, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            for (int i = 0; i < inputs.size(); i++) {
                if ( key == inputs.get(i).getKey() && action == inputs.get(i).getAction() )
                    inputs.get(i).execute();
            }
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
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
        while ( !glfwWindowShouldClose(window) ) {
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

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new GameLoop().run();
    }

}