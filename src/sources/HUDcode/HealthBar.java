package sources.HUDcode;

import sources.Sprite;

import java.awt.Graphics2D;
import java.awt.Color;

public class HealthBar {

    private boolean display;

    public enum healthType {NUM, BAR, SPRITE}
    private healthType type;

    private int lives;
    private int maxLives;

    private Sprite hpSprite;
    private Graphics2D hpBar;

    private int x;
    private int y;

    private int width;
    private int height;

    // TODO: number form
    // TODO: sprite form
    // TODO: increase and decrease hp
    // TODO: make setters impact all related values

    /*==================================================
                     Initialization
    ==================================================*/

    // value constructor
    public HealthBar(boolean display, healthType healthType, int lives,
                     int maxLives, Sprite hpSprite, int x, int y, int width,
                     int height) {

        this.display = display;
        this.type = healthType;

        this.lives = lives;
        this.maxLives = maxLives;

        this.hpSprite = hpSprite;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /*==================================================
                         Drawing
    ==================================================*/

    public void numberHealth() {

    }

    public void barHealth() {

        // create empty hp bar
        this.hpBar.setColor(Color.black);
        this.hpBar.fillRect(this.x, this.y, this.width, this.height);

        // fill hp
        this.hpBar.setColor(Color.green);
        this.hpBar.fillRect(this.x, this.y, this.width *
                (this.lives / this.maxLives), this.height);
    }

    public void spriteHealth() {

    }

    /*==================================================
                   Getters and Setters
    ==================================================*/

    public boolean isDisplay() {
        return this.display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

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

    public Graphics2D getHpBar() {
        return this.hpBar;
    }

    public void setHpBar(Graphics2D hpBar) {
        this.hpBar = hpBar;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
