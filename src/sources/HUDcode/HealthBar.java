package sources.HUDcode;

import org.lwjgl.opengl.GL11;
import sources.Sprite;

public class HealthBar extends HUDElement {

    public enum healthType {BAR, NUM, SPRITE}
    private healthType type;

    private int lives;
    private int maxLives;

    private Sprite hpSprite;

    /*==================================================
                     Initialization
    ==================================================*/

    // value constructor
    public HealthBar(boolean display, healthType healthType, int lives,
                     int maxLives, Sprite hpSprite, int x, int y, int width,
                     int height) {

        super(display, x, y, width, height);

        this.type = healthType;

        this.lives = lives;
        this.maxLives = maxLives;

        this.hpSprite = hpSprite;
    }

    /*==================================================
                         Drawing
    ==================================================*/

    @Override
    public void drawElement() {

        switch (this.type) {

            case BAR:
                this.barHealth();
                break;

            case NUM:
                this.numberHealth();
                break;

            case SPRITE:
                this.spriteHealth();
                break;
        }
    }

    private void barHealth() {

        // create empty hp bar
        GL11.glColor3f(0.0f, 0.0f, 0.0f);

        GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(this.getX(), this.getY());
            GL11.glVertex2f(this.getX() + this.getWidth(), this.getY());
            GL11.glVertex2f(this.getX() + this.getWidth(), this.getY() + this.getHeight());
            GL11.glVertex2f(this.getX(), this.getY() + this.getHeight());
        GL11.glEnd();

    /*    this.hpBar.setColor(Color.black);
        this.hpBar.fillRect(this.getX(), this.getY(), this.getWidth(),
                this.getHeight());

        // fill hp
        this.hpBar.setColor(Color.green);
        this.hpBar.fillRect(this.getX(), this.getY(), this.getWidth() *
                (this.lives / this.maxLives), this.getHeight());*/
    }

    private void numberHealth() {
        // TODO: number form
    }

    private void spriteHealth() {
        // TODO: sprite form
    }

    /*==================================================
                   Getters and Setters
    ==================================================*/

    // TODO: increase and decrease health
    // TODO: make setters impact all related values

    public healthType getType() {
        return this.type;
    }

    public void setType(healthType type) {
        this.type = type;
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getMaxLives() {
        return this.maxLives;
    }

    public void setMaxLives(int maxLives) {
        this.maxLives = maxLives;
    }

    public Sprite getHpSprite() {
        return this.hpSprite;
    }

    public void setHpSprite(Sprite hpSprite) {
        this.hpSprite = hpSprite;
    }
}
