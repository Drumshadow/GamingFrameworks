package sources;

// I am not sure if this class is even used....
public class FPS {
    private int lastFPS;

    // gets system time in milliseconds
    public static double getTime() {
        return (double) System.nanoTime() / (double) 1000000000L;
    }

    //some startup code
    public void start() {
        lastFPS = (int) getTime(); //set lastFPS to current Time
    }
}