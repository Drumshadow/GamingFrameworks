package sources.HUDcode;

import org.lwjgl.opengl.GL11;
import sources.Sprite;

public class HealthBar extends HUDElement {

    public enum healthType {BAR, NUM, SPRITE}
    private healthType type;

    private static int lives = 10;
    private static int maxLives = 10;

    private static int invincibilityCounter = 0;
    private static final int INVTIME = 4;

    private Sprite hpSprite;

    /*==================================================
                     Initialization
    ==================================================*/

    // value constructor
    public HealthBar(boolean display, healthType healthType, int l,
                     int m, Sprite hpSprite, float x, float y,
                     float width, float height) {

        super(display, x, y, width, height);

        this.type = healthType;

        lives = l;
        maxLives = m;

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

        // fill hp
        GL11.glColor3f(0.058f, 0.956f, 0.027f);

        GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(this.getX(), this.getY());
            GL11.glVertex2f(this.getX() + (this.getWidth() * ((float)lives / (float)maxLives)), this.getY());
            GL11.glVertex2f(this.getX() + (this.getWidth() * ((float)lives / (float)maxLives)), this.getY() + this.getHeight());
            GL11.glVertex2f(this.getX(), this.getY() + this.getHeight());
        GL11.glEnd();
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

    // TODO: make setters impact all related values

    public void incHealth() {

        if (lives < maxLives) {
            lives++;
        }
    }

    public void decHealth() {

        // make sure there is time between damage taken
        if (invincibilityCounter == 0) {

            if (lives > 0) {
                lives--;
            }
            invincibilityCounter = 4;
        }
        else {
            invincibilityCounter--;
        }
    }

    public healthType getType() {
        return this.type;
    }

    public void setType(healthType type) {
        this.type = type;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int l) {
        lives = l;
    }

    public int getMaxLives() {
        return maxLives;
    }

    public void setMaxLives(int m) {
        maxLives = m;
    }

    public Sprite getHpSprite() {
        return this.hpSprite;
    }

    public void setHpSprite(Sprite hpSprite) {
        this.hpSprite = hpSprite;
    }
}
