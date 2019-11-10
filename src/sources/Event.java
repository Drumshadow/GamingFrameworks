package sources;

import sources.HUDcode.*;
import sources.objCode.GameObject;

class Event {
    public enum eventType {COLLISION, FRAME, EMISSION}
    private eventType type;
    private String obj1;
    private String obj2;
    private String hud;
    private int hpMod;

    private GameObject projectile;
    private int timer;
    private int fireTime;

    // frame event
    Event(eventType eT, String hE, int hpMod) {
        this.type = eT;
        this.hud = hE;
        this.hpMod = hpMod;
    }

    // collision event
    Event(eventType eT, String o1, String o2, String hE, int hpMod) {
        this.type = eT;
        this.obj1 = o1;
        this.obj2 = o2;
        this.hud = hE;
        this.hpMod = hpMod;
    }

    // emission event
    Event(eventType eT, String o1, GameObject p, int fireTime) {
        this.type = eT;
        this.obj1 = o1;
        this.projectile = p;
        this.fireTime = fireTime;
        this.timer = fireTime;
    }

    void execute(GameRoom room, HUD hud, int displayFrames) {
        switch (this.type) {
            case COLLISION: {

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
                    } else if (O2.getAi().contains(GameObject.Behavior.DESTRUCT) && O2.getDestroyer().equals(O1)) {
                        room.removeObject(room.getElement(obj2));
                    }
                }
                break;
            }

            case FRAME:
                updateHUD(hud, displayFrames);
                break;

            case EMISSION:

                GameObject O1 = room.getElement(obj1);

                // set x position
                if (this.projectile.getXSpeed() < 0) {
                    this.projectile.setX(O1.getX() - this.projectile.getHitBox().getBoundBox().getWidth());
                }
                else if (this.projectile.getXSpeed() > 0){
                    this.projectile.setX(O1.getX() + O1.getHitBox().getBoundBox().getWidth());
                }
                else {
                    this.projectile.setX(O1.getX());
                }

                // set y position
                if (this.projectile.getYSpeed() < 0) {
                    this.projectile.setY(O1.getY() - O1.getHitBox().getBoundBox().getHeight());
                }
                else if (this.projectile.getYSpeed() > 0) {
                    this.projectile.setY(O1.getY() + this.projectile.getHitBox().getBoundBox().getHeight());
                }
                else {
                    this.projectile.setY(O1.getY());
                }

                // add to room
                if (this.timer == this.fireTime) {
                    room.addObject(new GameObject(this.projectile));
                    this.timer = 0;
                }
                else {
                    this.timer++;
                }

                break;
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