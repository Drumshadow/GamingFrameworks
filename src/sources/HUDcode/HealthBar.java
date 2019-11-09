package sources.HUDcode;

import org.lwjgl.opengl.GL11;
import sources.Sprite;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

public class HealthBar extends HUDElement {

    public enum healthType {BAR, NUM, SPRITE}
    private healthType type;

    private static int lives;
    private static int maxLives;

    // To be used if health depletes too quickly
    private static int invincibilityCounter = 0;
    private static final int INVINT = 0;

    private Sprite hpSprite;

    /*==================================================
                     Initialization
    ==================================================*/

    // value constructor
    public HealthBar(healthType healthType, int l,
                     int m, Sprite hpSprite, float x, float y,
                     float width, float height, String name) {

        super(x, y, width, height, name);

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
            GL11.glVertex2f(this.x, this.y);
            GL11.glVertex2f(this.x + this.width, this.y);
            GL11.glVertex2f(this.x + this.width, this.y + this.height);
            GL11.glVertex2f(this.x, this.y + this.height);
        GL11.glEnd();

        // fill hp
        GL11.glColor3f(0.058f, 0.956f, 0.027f);

        GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(this.x, this.y);
            GL11.glVertex2f(this.x + (this.width * ((float)lives / (float)maxLives)), this.y);
            GL11.glVertex2f(this.x + (this.width * ((float)lives / (float)maxLives)), this.y + this.height);
            GL11.glVertex2f(this.x, this.y + this.height);
        GL11.glEnd();
    }

    private void numberHealth() {

        glPushMatrix();
            GL11.glOrtho(0, 1000, 1000, 0, -100, 100);
            HUD.hudFont.drawString(this.x, this.y, "Score: " + lives);
        glPopMatrix();
    }

    private void spriteHealth() {
        // TODO: sprite form
    }

    /*==================================================
                   Getters and Setters
    ==================================================*/

    // TODO: make setters impact all related values

    public void modHealth(int mod) {
        if (mod > 0) {
            if (lives + mod <= maxLives) {
                lives += mod;
            } else {
                lives = maxLives;
            }
        }
        else if (mod < 0) {
            if (invincibilityCounter == 0) {
                if (lives + mod >= 0) {
                    lives += mod;
                }
                else {
                    lives = 0;
                }
                invincibilityCounter = INVINT;
            }
            else {
                invincibilityCounter--;
            }
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