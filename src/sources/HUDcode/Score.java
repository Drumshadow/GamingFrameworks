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

    public Score(int x, int y, int score, int maxScore, String name) {

        super(x, y, name);

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

    public void modScore(int mod) {
        if (mod > 0) {
            if (score + mod <= maxScore) {
                score += mod;
            } else {
                score = maxScore;
            }
        }
        else if (mod < 0) {

            if (score - mod >= 0) {
            score += mod;
        }
            else {
                score = 0;
            }
        }
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
