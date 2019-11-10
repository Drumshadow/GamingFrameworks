package sources;

import org.ini4j.Ini;
import sources.HUDcode.FrameDisplay;
import sources.HUDcode.HUD;
import sources.HUDcode.HealthBar;
import sources.HUDcode.Score;
import sources.objCode.GameObject;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class INI {
    void renderObjects(GameRoom room) throws IOException {
        Ini iniO = new Ini(new File("./objects/objects.ini"));
        int inputNum = Integer.parseInt(iniO.get("control", "objects"));
        for (int i = 0; i < inputNum; i++) {
            room.addObject(new GameObject(iniO.get("object" + i, "name"),
                    iniO.get("object" + i, "sprPath"),
                    Integer.parseInt(iniO.get("object" + i, "frames")),
                    Boolean.parseBoolean(iniO.get("object" + i, "collide")),
                    Double.parseDouble(iniO.get("object" + i, "weight")),
                    Double.parseDouble(iniO.get("object" + i, "tv")),
                    Double.parseDouble(iniO.get("object" + i, "jump")),
                    Integer.parseInt(iniO.get("object" + i, "boxType")),
                    Double.parseDouble(iniO.get("object" + i, "x")),
                    Double.parseDouble(iniO.get("object" + i, "y"))));
        }
    }

    public void setControls(ControllerList controls) throws IOException {
        Ini iniC = new Ini(new File("./inputs/controller.ini"));
        int inputNum = Integer.parseInt(iniC.get("control", "inputs"));

        for (int i = 0; i < inputNum; i++) {
            switch (iniC.get("input" + i, "purpose")) {
                case "Create":
                case "Destroy":

                    controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                            Integer.parseInt(iniC.get("input" + i, "index")),
                            Float.parseFloat(iniC.get("input" + i, "range")),
                            iniC.get("input" + i, "object"),
                            iniC.get("input" + i, "purpose")));
                    break;
                case "MoveX":
                case "MoveY":

                    controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                            Integer.parseInt(iniC.get("input" + i, "index")),
                            Float.parseFloat(iniC.get("input" + i, "range")),
                            iniC.get("input" + i, "object"),
                            iniC.get("input" + i, "purpose"),
                            Double.parseDouble(iniC.get("input" + i, "speed"))));
                    break;
                case "PlaySound":

                    Audio a = new Audio();
                    a.setFileName(iniC.get("input" + i, "audio"));

                    controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                            Integer.parseInt(iniC.get("input" + i, "index")),
                            Float.parseFloat(iniC.get("input" + i, "range")),
                            iniC.get("input" + i, "object"),
                            iniC.get("input" + i, "purpose"),
                            a));
                    break;
            }
        }
    }

    void setKeyboardControls(InputList inputs) throws IOException {
        Ini ini = new Ini(new File("./inputs/keyboard.ini"));
        int inputNum = Integer.parseInt(ini.get("control", "inputs"));

        for (int i = 0; i < inputNum; i++) {
            switch (ini.get("input" + i, "purpose")) {
                case "Create":
                case "Destroy":

                    inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                            Integer.parseInt(ini.get("input" + i, "action")),
                            ini.get("input" + i, "object"),
                            ini.get("input" + i, "purpose")));
                    break;
                case "MoveX":
                case "MoveY":
                case "Jump":

                    inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                            Integer.parseInt(ini.get("input" + i, "action")),
                            ini.get("input" + i, "object"),
                            ini.get("input" + i, "purpose"),
                            Double.parseDouble(ini.get("input" + i, "speed"))));
                    break;
                case "PlaySound":

                    Audio a = new Audio();
                    a.setFileName(ini.get("input" + i, "audio"));

                    inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                            Integer.parseInt(ini.get("input" + i, "action")) | GLFW_PRESS,
                            ini.get("input" + i, "object"), a));
                    break;
                case "Pause":
                    inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                            Integer.parseInt(ini.get("input" + i, "action"))));
                    break;
            }
        }
    }

    void renderHUD(HUD hud) throws IOException {
        Ini ini = new Ini(new File("./HUD/HUD.ini"));
        int elementNum = Integer.parseInt(ini.get("control", "elements"));
        for (int i = 0; i < elementNum; i++) {
            switch (ini.get("element" + i, "type")) {
                case "HealthBar":

                    // get health bar type (default is bar)
                    HealthBar.healthType hpType;
                    Sprite hpSprite = null;

                    if (ini.get("element" + i, "hType").equals("SPRITE")) {
                        hpType = HealthBar.healthType.SPRITE;

                        // get sprite path
                        hpSprite = new Sprite(ini.get("element" + i, "spPath"),
                                Integer.parseInt(ini.get("element" + i, "spFrames")));
                    }
                    else if (ini.get("element" + i, "hType").equals("NUM")) {
                        hpType = HealthBar.healthType.NUM;
                    }
                    else {
                        hpType = HealthBar.healthType.BAR;
                    }

                    hud.addElement(new HealthBar(hpType, Integer.parseInt(ini.get("element" + i, "lives")),
                            Integer.parseInt(ini.get("element" + i, "max")), hpSprite,
                            Float.parseFloat(ini.get("element" + i, "x")),
                            Float.parseFloat(ini.get("element" + i, "y")),
                            Float.parseFloat(ini.get("element" + i, "width")),
                            Float.parseFloat(ini.get("element" + i, "height")),
                            ini.get("element" + i, "name")));
                    break;
                case "FrameDisplay":
                    hud.addElement(new FrameDisplay(Float.parseFloat(ini.get("element" + i, "x")),
                            Float.parseFloat(ini.get("element" + i, "y")),
                            ini.get("element" + i, "name")));
                    break;
                case "Score":
                    hud.addElement(new Score(Integer.parseInt(ini.get("element" + i, "x")),
                            Integer.parseInt(ini.get("element" + i, "y")),
                            Integer.parseInt(ini.get("element" + i, "score")),
                            Integer.parseInt(ini.get("element" + i, "maxScore")),
                            ini.get("element" + i, "name")));
                    break;
            }
        }
    }

    void renderEvents(EventHandler events) throws IOException {
        Ini ini = new Ini(new File("./events/events.ini"));
        int eventNum = Integer.parseInt(ini.get("control", "events"));

        for (int i = 0; i < eventNum; i++) {
            switch (ini.get("event" + i, "type")) {
                case "collision":
                    events.addEvent(new Event(Event.eventType.COLLISION, ini.get("event" + i, "obj1"),
                            ini.get("event" + i, "obj2"),
                            ini.get("event" + i, "hud"),
                            Integer.parseInt(ini.get("event" + i, "mod"))));
                    break;

                case "frame":
                    events.addEvent(new Event(Event.eventType.FRAME, ini.get("event" + i, "hud"),
                            Integer.parseInt(ini.get("event" + i, "mod"))));
                    break;

                case "emission":

                    // create projectile (x and y position don't matter)
                    GameObject projectile = new GameObject(ini.get("event" + i, "o2name"),
                            ini.get("event" + i, "o2path"),
                            Integer.parseInt(ini.get("event" + i, "o2frames")),
                            Boolean.parseBoolean(ini.get("event" + i, "o2collide")),
                            Double.parseDouble(ini.get("event" + i, "o2weight")),
                            Double.parseDouble(ini.get("event" + i, "o2tv")),
                            Double.parseDouble(ini.get("event" + i, "o2jump")),
                            Integer.parseInt(ini.get("event" + i, "o2boxType")),
                            0.0, 0.0);

                    // make projectile auto-move
                    projectile.addBehaviors(GameObject.Behavior.AUTO);
                    projectile.auto(Double.parseDouble(ini.get("event" + i, "xSpeed")) / 1000.0,
                            Double.parseDouble(ini.get("event" + i, "ySpeed")) / 1000.0);

                    events.addEvent(new Event(Event.eventType.EMISSION,
                            ini.get("event" + i, "obj1"), projectile,
                            Integer.parseInt(ini.get("event" + i, "timer"))));
                    break;
            }
        }
    }
}