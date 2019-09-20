package sources.HUDcode;

public class Score extends HUDElement {

    private int score;
    private int maxScore;

    /*==================================================
                     Initialization
    ==================================================*/

    public Score(boolean display, int x, int y, int width, int height,
                 int score, int maxScore) {

        super(display, x, y, width, height);

        this.score = score;
        this.maxScore = maxScore;
    }

    /*==================================================
                         Drawing
    ==================================================*/

    @Override
    public void drawElement() {

        // TODO: number form
    }

    /*==================================================
                   Getters and Setters
    ==================================================*/
    // TODO: increase and decrease score

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
