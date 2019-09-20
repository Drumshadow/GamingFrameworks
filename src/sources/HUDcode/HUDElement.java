package sources.HUDcode;

public abstract class HUDElement {

    private boolean display;

    private int x;
    private int y;

    private int width;
    private int height;

    /*==================================================
                     Initialization
    ==================================================*/

    // value constructor
    public HUDElement(boolean display, int x, int y, int width, int height) {

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