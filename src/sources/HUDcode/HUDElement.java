package sources.HUDcode;

public abstract class HUDElement {

    protected float x;
    protected float y;

    protected float width;
    protected float height;

    /*==================================================
                     Initialization
    ==================================================*/

    // value constructor
    HUDElement(float x, float y, float width, float height) {

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
    }

    // value constructor with no dimensions
    HUDElement(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /*==================================================
                         Drawing
    ==================================================*/
    public abstract void drawElement();

    /*==================================================
                   Getters and Setters
    ==================================================*/

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