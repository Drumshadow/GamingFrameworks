package sources;

import sources.HUDcode.*;
import sources.objCode.GameObject;

class Event {
    public enum eventType {COLLISION, FRAME}
    private eventType type;
    private String obj1;
    private String obj2;
    private String hud;
    private int hpMod;

    Event(eventType eT, String hE, int hpMod) {
        type = eT;
        hud = hE;
        this.hpMod = hpMod;
    }

    Event(eventType eT, String o1, String o2, String hE, int hpMod) {
        type = eT;
        obj1 = o1;
        obj2 = o2;
        hud = hE;
        this.hpMod = hpMod;
    }

    void execute(GameRoom room, HUD hud, int displayFrames) {
        if (type == eventType.COLLISION) {

            GameObject O1 = room.getElement(obj1);
            GameObject O2 = room.getElement(obj2);

            // make sure none of the colliding objects have been destroyed
            if (O1 == null || O2 == null) {
                return;
            }

            if (O1.getHitBox().basicCollision(O2.getHitBox()) ||
                    O2.getHitBox().basicCollision(O1.getHitBox())) {

                updateHUD(hud, displayFrames);

                if (O1.getAi().contains(GameObject.Behavior.DESTRUCT) && O1.getDestroyer().equals(O2)) {
                    room.removeObject(room.getElement(obj1));
                }
                else if (O2.getAi().contains(GameObject.Behavior.DESTRUCT) && O2.getDestroyer().equals(O1)) {
                    room.removeObject(room.getElement(obj2));
                }
            }
        }
        else if (type == eventType.FRAME) {
            updateHUD(hud, displayFrames);
        }
    }

    private void updateHUD(HUD hud, int displayFrames) {
        if (hud.getElement(this.hud) instanceof HealthBar) {
            ((HealthBar) hud.getElement(this.hud)).modHealth(hpMod);
        }
        else if (hud.getElement(this.hud) instanceof FrameDisplay) {
            ((FrameDisplay) hud.getElement(this.hud)).setFrameCount(displayFrames);
        }
        else if (hud.getElement(this.hud) instanceof Score) {
            ((Score) hud.getElements().get(2)).modScore(hpMod);
        }
    }
}