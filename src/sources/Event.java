package sources;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;
import sources.HUDcode.*;
import sources.objCode.GameObject;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

class Event {
    public enum eventType {COLLISION, FRAME, EMISSION, DESTRUCTION, END}
    private eventType type;
    private String obj1;
    private String obj2;
    private String hud;
    private int mod;

    private GameObject projectile;
    private int timer;
    private int fireTime;

    private Audio sound;

    // For end event
    private int x;
    private int y;
    private String msg;

    // frame event
    Event(eventType eT, String hE, int mod, Audio a) {
        this.type = eT;
        this.hud = hE;
        this.mod = mod;
        this.sound = a;
    }

    // collision event
    Event(eventType eT, String o1, String o2, String hE, int mod, Audio a) {
        this.type = eT;
        this.obj1 = o1;
        this.obj2 = o2;
        this.hud = hE;
        this.mod = mod;
        this.sound = a;
    }

    // emission event
    Event(eventType eT, String o1, GameObject p, int fireTime, Audio a) {
        this.type = eT;
        this.obj1 = o1;
        this.projectile = p;
        this.fireTime = fireTime;
        this.timer = fireTime;
        this.sound = a;
    }

    // destruction event
    Event(eventType eT, String o1, String o2, Audio a) {
        this.type = eT;
        this.obj1 = o1;
        this.obj2 = o2;
        this.sound = a;
    }

    // end event
    Event(eventType eT, String hE, int mod, String msg, int x, int y, Audio a) {
        this.type = eT;
        this.hud = hE;
        this.mod = mod;
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.sound = a;
    }

    public eventType getType() {
        return this.type;
    }

    void execute(GameRoom room, HUD hud, int displayFrames) {
        switch (this.type) {
            case COLLISION: {

                // cycle through all objects with the same name
                outerLoop:
                for (GameObject O1 : room.getElements(this.obj1)) {

                    // make sure first object exists
                    if (O1 == null) {
                        continue;
                    }

                    for (GameObject O2 : room.getElements(this.obj2)) {

                        // make sure second object exists
                        if (O2 == null) {
                            continue;
                        }

                        // check collision
                        if (O1.getHitBox().basicCollision(O2.getHitBox()) ||
                                O2.getHitBox().basicCollision(O1.getHitBox())) {

                            // update HUD
                            updateHUD(hud, displayFrames);

                            // play sound
                            if (this.sound != null) {
                                this.sound.loadPlaySound();
                            }

                            break outerLoop;
                        }
                    }
                }
                break;
            }
            case FRAME:
                updateHUD(hud, displayFrames);

                // play sound
                if (this.sound != null) {
                    this.sound.loadPlaySound();
                }

                break;

            case EMISSION: {

                // cycle through all objects with same name
                for (GameObject O : room.getElements(this.obj1)) {

                    // make sure emitting object exists
                    if (O == null) {
                        continue;
                    }

                    // prepare projectile
                    prepProjectile(O, this.projectile);

                    // add to room
                    if (this.timer == this.fireTime) {
                        room.addObject(new GameObject(this.projectile));
                        this.timer = 0;

                        // play sound
                        if (this.sound != null) {
                            this.sound.loadPlaySound();
                        }

                    } else {
                        this.timer++;
                    }

                   /* if (O.getAi().contains(GameObject.Behavior.EMIT)) {

                        // prepare projectile
                        prepProjectile(O, this.projectile);

                        // add to room
                        if (this.timer == this.fireTime) {
                            room.addObject(new GameObject(this.projectile));
                            this.timer = 0;

                            // play sound
                            if (this.sound != null) {
                                this.sound.loadPlaySound();
                            }

                        } else {
                            this.timer++;
                        }
                    }*/
                }
                break;
            }
            case DESTRUCTION: {

                outerLoop:
                for (GameObject O1 : room.getElements(this.obj1)) {

                    // make sure first object exists
                    if (O1 == null) {
                        continue;
                    }

                    for (GameObject O2: room.getElements(this.obj2)) {

                        // make sure second object exists
                        if (O2 == null) {
                            continue;
                        }

                        // check for collision
                        if (O1.getHitBox().basicCollision(O2.getHitBox()) ||
                                O2.getHitBox().basicCollision(O1.getHitBox())) {

                            // destroy objects
                            if (O1.getAi().contains(GameObject.Behavior.DESTRUCT) &&
                                    O1.getDestroyers().contains(this.obj2)) {

                                room.removeObject(O1);

                                // play sound
                                if (this.sound != null) {
                                    this.sound.loadPlaySound();
                                }
                            }
                            if (O2.getAi().contains(GameObject.Behavior.DESTRUCT) &&
                                    O2.getDestroyers().contains(this.obj1)) {

                                room.removeObject(O2);

                                // play sound
                                if (this.sound != null) {
                                    this.sound.loadPlaySound();
                                }
                            }
                            break outerLoop;
                        }
                    }
                }
                break;
            }
        }
    }

    boolean execute(HUD hud, UnicodeFont hudFont, boolean paused) {
        if (hud.getElement(this.hud) instanceof HealthBar) {
            if (((HealthBar) hud.getElement(this.hud)).getLives() == mod) {
                glPushMatrix();
                GL11.glOrtho(0, 1000, 1000, 0, -100, 100);
                hudFont.drawString(this.x, this.y, this.msg);
                glPopMatrix();
                return true;
            }
        }
        else if (hud.getElement(this.hud) instanceof Score) {
            if (((Score) hud.getElement(this.hud)).getScore() == mod) {
                glPushMatrix();
                GL11.glOrtho(0, 1000, 1000, 0, -100, 100);
                hudFont.drawString(this.x, this.y, this.msg);
                glPopMatrix();
                return true;
            }
        }
        return paused;
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