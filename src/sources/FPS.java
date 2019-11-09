package sources;

public class FPS {
    private int lastFPS;

    /**
     * Get the time in milliseconds
     *
     * @return The system time in milliseconds
     */
    public static double getTime() {
        return (double) System.nanoTime() / (double) 1000000000L;
    }

    public void start() {
        //some startup code
        lastFPS = (int) getTime(); //set lastFPS to current Time
    }

    public void updateFPS() {
        int fps = 0;
        if (getTime() - lastFPS > 1000) {
            System.out.println("FPS" + fps);
            fps = 0; //reset the FPS counter
            lastFPS += 1000; //add one second
        }
        fps++;
    }
}