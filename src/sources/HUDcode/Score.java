package sources.HUDcode;

public class Score extends HUDElement {

    public int score;
    public int maxScore;

    public Score(boolean display, int x, int y, int width, int height) {
        super(display, x, y, width, height);
    }

    @Override
    public void drawElement() {

    }

    // TODO: number form
    // TODO: increase and decrease score
}
