package sources;

import org.ini4j.Config;
import org.ini4j.Ini;
import sources.HUDcode.FrameDisplay;
import sources.HUDcode.HUD;
import sources.HUDcode.HealthBar;
import sources.HUDcode.Score;
import sources.objCode.GameObject;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

class INI {
    void renderObjects(GameRoom room, EventHandler events, int index) throws IOException {
        Ini iniO = new Ini(new File("./objects/objects.ini"));
        Config.getGlobal().setEscape(false);

        int i = index;

        if (index == -1) {
            i = 0;
        }

        // for (String section : (Set<String>)iniO.keySet()) {
        for (; i < iniO.keySet().size(); i++) {

            String section = i + "";

            GameObject newObject = new GameObject(iniO.get(section, "name"),
                    iniO.get(section, "sprPath"),
                    Integer.parseInt(iniO.get(section, "frames")),
                    Boolean.parseBoolean(iniO.get(section, "collide")),
                    Double.parseDouble(iniO.get(section, "weight")),
                    Double.parseDouble(iniO.get(section, "tv")),
                    Double.parseDouble(iniO.get(section, "jump")),
                    Integer.parseInt(iniO.get(section, "boxType")),
                    Double.parseDouble(iniO.get(section, "x")),
                    Double.parseDouble(iniO.get(section, "y")));

            // add AI behaviors
            String[] ai = iniO.get(section, "AI").split(",");
            this.applyAI(events, iniO, newObject, ai, section, null);

            room.addObject(newObject);

            // update change keeper
            if (index != -1) {
                GameLoop.changeKeeper[4]++;
            }
        }
    }

    void setControls(ControllerList controls, EventHandler events, int index) throws IOException {
        Ini iniC = new Ini(new File("./inputs/controller.ini"));

        int i = index;

        if (index == -1) {
            i = 0;
        }

        //for (String section : (Set<String>)iniC.keySet()) {
        for (; i < iniC.keySet().size(); i++) {

            String section = i + "";

            switch (iniC.get(section, "purpose")) {
                case "Create":
                case "Destroy":

                    controls.add(new Controller(Integer.parseInt(iniC.get(section, "button")),
                            Integer.parseInt(iniC.get(section, "index")),
                            Float.parseFloat(iniC.get(section, "range")),
                            iniC.get(section, "object"),
                            iniC.get(section, "purpose")));
                    break;
                case "MoveX":
                case "MoveY":

                    controls.add(new Controller(Integer.parseInt(iniC.get(section, "button")),
                            Integer.parseInt(iniC.get(section, "index")),
                            Float.parseFloat(iniC.get(section, "range")),
                            iniC.get(section, "object"),
                            iniC.get(section, "purpose"),
                            Double.parseDouble(iniC.get(section, "speed"))));
                    break;
                case "PlaySound":

                    Audio a = new Audio();
                    a.setFileName(iniC.get(section, "audio"));

                    controls.add(new Controller(Integer.parseInt(iniC.get(section, "button")),
                            Integer.parseInt(iniC.get(section, "index")),
                            Float.parseFloat(iniC.get(section, "range")),
                            iniC.get(section, "object"),
                            iniC.get(section, "purpose"),
                            a));
                    break;

                case "Fire":

                    // create projectile (x and y position don't matter)
                    GameObject projectile = new GameObject(iniC.get(section, "o2name"),
                            iniC.get(section, "o2path"),
                            Integer.parseInt(iniC.get(section, "o2frames")),
                            Boolean.parseBoolean(iniC.get(section, "o2collide")),
                            Double.parseDouble(iniC.get(section, "o2weight")),
                            Double.parseDouble(iniC.get(section, "o2tv")),
                            Double.parseDouble(iniC.get(section, "o2jump")),
                            Integer.parseInt(iniC.get(section, "o2boxType")),
                            0.0, 0.0);

                    // add AI to projectile
                    String[] ai = iniC.get(section, "AI").split(",");

                    this.applyAI(events, iniC, projectile, ai, section, null);

                    // make projectile auto-move
                    projectile.addBehaviors(GameObject.Behavior.AUTO);
                    projectile.auto(Double.parseDouble(iniC.get(section, "xSpeed")) / 1000.0,
                            Double.parseDouble(iniC.get(section, "ySpeed")) / 1000.0);

                    // add input
                    controls.add(new Controller(Integer.parseInt(iniC.get(section, "button")),
                            Integer.parseInt(iniC.get(section, "index")),
                            Float.parseFloat(iniC.get(section, "range")),
                            iniC.get(section, "object"),
                            iniC.get(section, "purpose"),
                            projectile));
                    break;
            }

            // update change keeper
            if (index != -1) {
                GameLoop.changeKeeper[1]++;
            }
        }
    }

    void setKeyboardControls(InputList inputs, EventHandler events, int index) throws IOException {
        Ini ini = new Ini(new File("./inputs/keyboard.ini"));

        int i = index;

        if (index == -1) {
            i = 0;
        }

        //for (String section : (Set<String>)ini.keySet()) {
        for (; i < ini.keySet().size(); i++) {

            String section = i + "";

            switch (ini.get(section, "purpose")) {
                case "Create":
                case "Destroy":

                    inputs.add(new Input(Integer.parseInt(ini.get(section, "key")),
                            Integer.parseInt(ini.get(section, "action")),
                            ini.get(section, "object"),
                            ini.get(section, "purpose")));
                    break;
                case "MoveX":
                case "MoveY":
                case "Jump":

                    inputs.add(new Input(Integer.parseInt(ini.get(section, "key")),
                            Integer.parseInt(ini.get(section, "action")),
                            ini.get(section, "object"),
                            ini.get(section, "purpose"),
                            Double.parseDouble(ini.get(section, "speed"))));
                    break;
                case "PlaySound":

                    Audio a = new Audio();
                    a.setFileName(ini.get(section, "audio"));

                    inputs.add(new Input(Integer.parseInt(ini.get(section, "key")),
                            Integer.parseInt(ini.get(section, "action")) | GLFW_PRESS,
                            ini.get(section, "object"), a));
                    break;
                case "Pause":
                    inputs.add(new Input(Integer.parseInt(ini.get(section, "key")),
                            Integer.parseInt(ini.get(section, "action"))));
                    break;

                case "Fire":

                    // create projectile (x and y position don't matter)
                    GameObject projectile = new GameObject(ini.get(section, "o2name"),
                            ini.get(section, "o2path"),
                            Integer.parseInt(ini.get(section, "o2frames")),
                            Boolean.parseBoolean(ini.get(section, "o2collide")),
                            Double.parseDouble(ini.get(section, "o2weight")),
                            Double.parseDouble(ini.get(section, "o2tv")),
                            Double.parseDouble(ini.get(section, "o2jump")),
                            Integer.parseInt(ini.get(section, "o2boxType")),
                            0.0, 0.0);

                    // add AI to projectile
                    String[] ai = ini.get(section, "AI").split(",");

                    this.applyAI(events, ini, projectile, ai, section, null);

                    // make projectile auto-move
                    projectile.addBehaviors(GameObject.Behavior.AUTO);
                    projectile.auto(Double.parseDouble(ini.get(section, "xSpeed")) / 1000.0,
                            Double.parseDouble(ini.get(section, "ySpeed")) / 1000.0);

                    // add input
                    inputs.add(new Input(Integer.parseInt(ini.get(section, "key")),
                            Integer.parseInt(ini.get(section, "action")),
                            ini.get(section, "object"), projectile));
                    break;
            }

            // update change keeper
            if (index != -1) {
                GameLoop.changeKeeper[0]++;
            }
        }
    }

    void renderHUD(HUD hud, int index) throws IOException {
        Ini ini = new Ini(new File("./HUD/HUD.ini"));

        int i = index;

        if (index == -1) {
            i = 0;
        }

        //for (String section : (Set<String>)ini.keySet()) {
        for (; i < ini.keySet().size(); i++) {

            String section = i + "";

            switch (ini.get(section, "type")) {
                case "HealthBar":

                    // get health bar type (default is bar)
                    HealthBar.healthType hpType;
                    Sprite hpSprite = null;

                    if (ini.get(section, "hType").equals("SPRITE")) {
                        hpType = HealthBar.healthType.SPRITE;

                        // get sprite path
                        hpSprite = new Sprite(ini.get(section, "spPath"),
                                Integer.parseInt(ini.get(section, "spFrames")));
                    }
                    else if (ini.get(section, "hType").equals("NUM")) {
                        hpType = HealthBar.healthType.NUM;
                    }
                    else {
                        hpType = HealthBar.healthType.BAR;
                    }

                    hud.addElement(new HealthBar(hpType, Integer.parseInt(ini.get(section, "lives")),
                            Integer.parseInt(ini.get(section, "max")), hpSprite,
                            Float.parseFloat(ini.get(section, "x")),
                            Float.parseFloat(ini.get(section, "y")),
                            Float.parseFloat(ini.get(section, "width")),
                            Float.parseFloat(ini.get(section, "height")),
                            ini.get(section, "name")));
                    break;
                case "FrameDisplay":
                    hud.addElement(new FrameDisplay(Float.parseFloat(ini.get(section, "x")),
                            Float.parseFloat(ini.get(section, "y")),
                            ini.get(section, "name")));
                    break;
                case "Score":
                    hud.addElement(new Score(Integer.parseInt(ini.get(section, "x")),
                            Integer.parseInt(ini.get(section, "y")),
                            Integer.parseInt(ini.get(section, "score")),
                            Integer.parseInt(ini.get(section, "maxScore")),
                            ini.get(section, "name")));
                    break;
            }

            // update change keeper
            if (index != -1) {
                GameLoop.changeKeeper[2]++;
            }
        }
    }

    void renderEvents(EventHandler events, int index) throws IOException {
        Ini ini = new Ini(new File("./events/events.ini"));

        int i = index;

        if (index == -1) {
            i = 0;
        }

        //for (String section : (Set<String>)ini.keySet()) {
        for (; i < ini.keySet().size(); i++) {

            String section = i + "";

            // get sound if there is one
            Audio a = null;

            if (!ini.get(section, "audio").equals("null")) {
                a = new Audio();
                a.setFileName(ini.get(section, "audio"));
            }

            switch (ini.get(section, "type")) {
                case "collision":
                    events.addEvent(new Event(Event.eventType.COLLISION, ini.get(section, "obj1"),
                            ini.get(section, "obj2"),
                            ini.get(section, "hud"),
                            Integer.parseInt(ini.get(section, "mod")), a));
                    break;

                case "frame":
                    events.addEvent(new Event(Event.eventType.FRAME, ini.get(section, "hud"),
                            Integer.parseInt(ini.get(section, "mod")), a));
                    break;

                case "emission":

                    // create projectile (x and y position don't matter)
                    GameObject projectile = new GameObject(ini.get(section, "o2name"),
                            ini.get(section, "o2path"),
                            Integer.parseInt(ini.get(section, "o2frames")),
                            Boolean.parseBoolean(ini.get(section, "o2collide")),
                            Double.parseDouble(ini.get(section, "o2weight")),
                            Double.parseDouble(ini.get(section, "o2tv")),
                            Double.parseDouble(ini.get(section, "o2jump")),
                            Integer.parseInt(ini.get(section, "o2boxType")),
                            0.0, 0.0);

                    // add AI behaviors
                    String[] ai = ini.get(section, "AI").split(",");
                    this.applyAI(events, ini, projectile, ai, section, a);

                    // make projectile auto-move
                    projectile.addBehaviors(GameObject.Behavior.AUTO);
                    projectile.auto(Double.parseDouble(ini.get(section, "xSpeed")) / 1000.0,
                            Double.parseDouble(ini.get(section, "ySpeed")) / 1000.0);

                    // add event
                    events.addEvent(new Event(Event.eventType.EMISSION,
                            ini.get(section, "obj1"), projectile,
                            Integer.parseInt(ini.get(section, "timer")), a));
                    break;

                case "destruction":
                    events.addEvent(new Event(Event.eventType.DESTRUCTION,
                            ini.get(section, "obj1"),
                            ini.get(section, "obj2"), a));
                    break;

                case "end":
                    events.addEvent(new Event(Event.eventType.END,
                            ini.get(section, "hud"),
                            Integer.parseInt(ini.get(section, "mod")),
                            ini.get(section, "msg"),
                            Integer.parseInt(ini.get(section, "x")),
                            Integer.parseInt(ini.get(section, "y")), a));
            }

            // update change keeper
            if (index != -1) {
                GameLoop.changeKeeper[3]++;
            }
        }
    }

    // applies AI to projectiles
    private void applyAI(EventHandler events, Ini ini, GameObject projectile, String[] ai, String section, Audio a) {
        if (!ai[0].equals("null")) {

            for (String s : ai) {
                switch (s) {

                    case "copy":
                        projectile.addBehaviors(GameObject.Behavior.COPY);
                        projectile.setTarget(ini.get(section, "o2"));
                        break;

                    case "ledges":
                        projectile.addBehaviors(GameObject.Behavior.LEDGES);
                        break;

                    case "walls":
                        projectile.addBehaviors(GameObject.Behavior.WALLS);
                        break;

                    case "bounce":
                        projectile.addBehaviors(GameObject.Behavior.BOUNCE);
                        break;

                    case "auto":
                        projectile.addBehaviors(GameObject.Behavior.AUTO);

                        projectile.auto(Double.parseDouble(ini.get(section, "xSpeed")) / 1000.0,
                                Double.parseDouble(ini.get(section, "ySpeed")) / 1000.0);
                        break;

                    case "emit":
                        projectile.addBehaviors(GameObject.Behavior.EMIT);
                        break;

                    case "destruct":
                        projectile.addBehaviors(GameObject.Behavior.DESTRUCT);

                        String[] destroyers = ini.get(section, "destroyers").split(",");
                        projectile.addDestroyer(destroyers);

                        for (String d : projectile.getDestroyers()) {
                            events.addEvent(new Event(Event.eventType.DESTRUCTION, projectile.getObjName(), d, a));
                        }
                        break;
                }
            }
        }
    }
}