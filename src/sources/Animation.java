package sources;

import java.io.IOException;

public class Animation {
    private Texture[] frames;
    private int pointer;

    private double elapsedTime;
    private double currentTime;
    private double lastTime;
    private double fps;

    public Animation(int amount, int fps, String filename) {
        this.pointer = 0;
        this.elapsedTime = 0;
        this.currentTime = 0;
        this.lastTime = FPS.getTime();
        this.fps = 1.0/fps;

        this.frames = new Texture[amount];
        for(int i = 0; i < amount; i++) {
            try {
                this.frames[i] = new Texture("anim/" + filename + "_" + i + ".png");
            } catch (IOException e) {
                System.out.println("Bad file path");
            }
        }
    }

    public void bind() {
        bind(0);
    }

    public void bind(int sampler) {
        this.currentTime = FPS.getTime();
        this.elapsedTime += currentTime - lastTime;

        if(elapsedTime >= fps) {
            elapsedTime -= fps;
            pointer++;
        }

        if(pointer > frames.length) {
            pointer = 0;
        }

        this.lastTime = currentTime;

      //  frames[pointer].bind(sampler);
    }
}
