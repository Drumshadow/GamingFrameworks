package sources.HUDcode;

import java.util.Vector;

public class HUD {

    private Vector<HUDElement> elements;

     /*==================================================
                     Initialization
    ==================================================*/

     public HUD() {
         this.elements = new Vector<>();
     }

    /*==================================================
                       Elements
    ==================================================*/

    public void addElement(HUDElement he) {
        this.elements.add(he);
    }

    public void removeElement(HUDElement he) {
        this.elements.remove(he);
    }

    public void drawHUD() {
        this.elements.forEach(HUDElement::drawElement);
    }

    /*==================================================
                   Getters and Setters
    ==================================================*/

    public Vector<HUDElement> getElements() {
        return this.elements;
    }

    public void setElements(Vector<HUDElement> elements) {
        this.elements = elements;
    }
}