package sources;

import sources.HUDcode.*;

public class Event {
    public enum eventType {COLLISION, FRAME};
    private eventType type;
    private String obj1;
    private String obj2;
    private String hud;
    private int hpMod;

    public Event(eventType eT, String hE, int hpMod) {
        type = eT;
        hud = hE;
        this.hpMod = hpMod;
    }

    public Event(eventType eT, String o1, String o2, String hE, int hpMod) {
        type = eT;
        obj1 = o1;
        obj2 = o2;
        hud = hE;
        this.hpMod = hpMod;
    }

    public void execute(GameRoom room, HUD hud, int displayFrames) {
        if (type == eventType.COLLISION) {
            if (room.getElement(obj1).getHitBox().basicCollision(room.getElement(obj2).getHitBox()) ||
                    room.getElement(obj2).getHitBox().basicCollision(room.getElement(obj1).getHitBox())) {
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
        else if (type == eventType.FRAME) {
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
}
