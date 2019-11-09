package sources.HUDcode;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.util.Vector;

public class HUD {

    private Vector<HUDElement> elements;

    static UnicodeFont hudFont;

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

    public HUDElement getElement(String name) {
        for (HUDElement element : elements) {
            if (element.name.equals(name)) {
                return element;
            }
        }
        return null;
    }

    public void setElements(Vector<HUDElement> elements) {
        this.elements = elements;
    }

    public void setHudFont(String fontPath, int fontSize) throws SlickException {

        Font awtFont = new Font(fontPath, Font.PLAIN, fontSize);
        hudFont = new UnicodeFont(awtFont);

        hudFont.addAsciiGlyphs();
        hudFont.getEffects().add(new ColorEffect(Color.WHITE));
        hudFont.loadGlyphs();
    }
}