package sources;

import sources.HUDcode.*;
import sources.objCode.GameObject;

class Event {
    public enum eventType {COLLISION, FRAME, EMISSION, DESTRUCTION}
    private eventType type;
    private String obj1;
    private String obj2;
    private String hud;
    private int mod;

    private GameObject projectile;
    private int timer;
    private int fireTime;

    // frame event
    Event(eventType eT, String hE, int mod) {
        this.type = eT;
        this.hud = hE;
        this.mod = mod;
    }

    // collision event
    Event(eventType eT, String o1, String o2, String hE, int mod) {
        this.type = eT;
        this.obj1 = o1;
        this.obj2 = o2;
        this.hud = hE;
        this.mod = mod;
    }

    // emission event
    Event(eventType eT, String o1, GameObject p, int fireTime) {
        this.type = eT;
        this.obj1 = o1;
        this.projectile = p;
        this.fireTime = fireTime;
        this.timer = fireTime;
    }

    // destruction event
    Event(eventType eT, String o1, String o2) {
        this.type = eT;
        this.obj1 = o1;
        this.obj2 = o2;
    }

    void execute(GameRoom room, HUD hud, int displayFrames) {
        switch (this.type) {
            case COLLISION: {

                // cycle through all objects with the same name
                outerLoop:
                for (GameObject O1 : room.getElements(this.obj1)) {
                    for (GameObject O2 : room.getElements(this.obj2)) {

                        // make sure none of the colliding objects have been destroyed
                        if (O1 == null || O2 == null) {
                            return;
                        }

                        // check collision
                        if (O1.getHitBox().basicCollision(O2.getHitBox()) ||
                                O2.getHitBox().basicCollision(O1.getHitBox())) {

                            updateHUD(hud, displayFrames);
                            break outerLoop;
                        }
                    }
                }
                break;
            }
            case FRAME:
                updateHUD(hud, displayFrames);
                break;

            case EMISSION: {

                // cycle through all objects with same name
                for (GameObject O : room.getElements(this.obj1)) {

                    // make sure emitting object was not destroyed
                    if (O == null) {
                        continue;
                    }

                    if (O.getAi().contains(GameObject.Behavior.EMIT)) {

                        // prepare projectile
                        prepProjectile(O, this.projectile);

                        // add to room
                        if (this.timer == this.fireTime) {
                            room.addObject(new GameObject(this.projectile));
                            this.timer = 0;
                        } else {
                            this.timer++;
                        }
                    }
                }
                break;
            }
            case DESTRUCTION: {

                outerLoop:
                for (GameObject O1 : room.getElements(this.obj1)) {
                    for (GameObject O2: room.getElements(this.obj2)) {

                        // make sure none of the colliding objects have been destroyed
                        if (O1 == null || O2 == null) {
                            return;
                        }

                        // check for collision
                        if (O1.getHitBox().basicCollision(O2.getHitBox()) ||
                                O2.getHitBox().basicCollision(O1.getHitBox())) {

                            // destroy objects
                            if (O1.getAi().contains(GameObject.Behavior.DESTRUCT) &&
                                    O1.getDestroyers().contains(this.obj2)) {

                                room.removeObject(O1);
                            }
                            if (O2.getAi().contains(GameObject.Behavior.DESTRUCT) &&
                                    O2.getDestroyers().contains(this.obj1)) {

                                room.removeObject(O2);
                            }
                            break outerLoop;
                        }
                    }
                }
                break;
            }
        }
    }

    // prepares projectile to be emitted/fired from object
    static void prepProjectile(GameObject o1, GameObject projectile) {
        if (projectile.getXSpeed() < 0) {
            projectile.setX(o1.getX() - projectile.getHitBox().getBoundBox().getWidth());
        }
        else if (projectile.getXSpeed() > 0){
            projectile.setX(o1.getX() + o1.getHitBox().getBoundBox().getWidth());
        }
        else {
            projectile.setX(o1.getX());
        }

        if (projectile.getYSpeed() < 0) {
            projectile.setY(o1.getY() - o1.getHitBox().getBoundBox().getHeight());
        }
        else if (projectile.getYSpeed() > 0) {
            projectile.setY(o1.getY() + projectile.getHitBox().getBoundBox().getHeight());
        }
        else {
            projectile.setY(o1.getY());
        }
    }

    private void updateHUD(HUD hud, int displayFrames) {
        if (hud.getElement(this.hud) instanceof HealthBar) {
            ((HealthBar) hud.getElement(this.hud)).modHealth(this.mod);
        }
        else if (hud.getElement(this.hud) instanceof FrameDisplay) {
            ((FrameDisplay) hud.getElement(this.hud)).setFrameCount(displayFrames);
        }
        else if (hud.getElement(this.hud) instanceof Score) {
            ((Score) hud.getElement(this.hud)).modScore(this.mod);
        }
    }
}