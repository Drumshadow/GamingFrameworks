package sources.HUDcode;

public abstract class HUDElement {

    private boolean display;

    private float x;
    private float y;

    private float width;
    private float height;

    /*==================================================
                     Initialization
    ==================================================*/

    // value constructor
    HUDElement(boolean display, float x, float y, float width, float height) {

        this.display = display;

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
    }

    /*==================================================
                         Drawing
    ==================================================*/
    public abstract void drawElement();

    /*==================================================
                   Getters and Setters
    ==================================================*/

    public boolean isDisplay() {
        return this.display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}