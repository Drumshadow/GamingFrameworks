package sources.HUDcode;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

public class Score extends HUDElement {

    private int score;
    private int maxScore;

    /*==================================================
                     Initialization
    ==================================================*/

    public Score(int x, int y, int score, int maxScore) {

        super(x, y);

        this.score = score;
        this.maxScore = maxScore;
    }

    /*==================================================
                         Drawing
    ==================================================*/

    @Override
    public void drawElement() {

        glPushMatrix();
            GL11.glOrtho(0, 1000, 1000, 0, -100, 100);
            HUD.hudFont.drawString(this.x, this.y, "Score: " + this.score);
        glPopMatrix();
    }

    /*==================================================
                   Getters and Setters
    ==================================================*/

    public void incScore() {

        if (this.score < this.maxScore) {
            this.score++;
        }
    }

    public void decScore() {
        this.score--;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxScore() {
        return this.maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
}
