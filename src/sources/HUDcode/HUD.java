package sources.HUDcode;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.util.Vector;

public class HUD {

    private Vector<HUDElement> elements;

    public static UnicodeFont hudFont;

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
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).name.equals(name)) {
                return elements.get(i);
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