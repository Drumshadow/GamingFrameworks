package sources;

import org.ini4j.Ini;
import sources.objCode.GameObject;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class INI {
    public void renderObjects(GameRoom room) throws IOException {
        Ini iniO = new Ini(new File("./objects/objects.ini"));
        int inputNum = Integer.parseInt(iniO.get("control", "objects"));
        for (int i = 0; i < inputNum; i++) {
            room.addObject(new GameObject(iniO.get("object" + i, "name"),
                    iniO.get("object" + i, "sprPath"),
                    Boolean.parseBoolean(iniO.get("object" + i, "collide")),
                    Boolean.parseBoolean(iniO.get("object" + i, "fear")),
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
            if (iniC.get("input" + i, "purpose").equals("Create") ||
                    iniC.get("input" + i, "purpose").equals("Destroy")) {

                controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                        Integer.parseInt(iniC.get("input" + i, "index")),
                        Float.parseFloat(iniC.get("input" + i, "range")),
                        iniC.get("input" + i, "object"),
                        iniC.get("input" + i, "purpose")));
            }
            else if (iniC.get("input" + i, "purpose").equals("MoveX") ||
                    iniC.get("input" + i, "purpose").equals("MoveY")) {

                controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                        Integer.parseInt(iniC.get("input" + i, "index")),
                        Float.parseFloat(iniC.get("input" + i, "range")),
                        iniC.get("input" + i, "object"),
                        iniC.get("input" + i, "purpose"),
                        Double.parseDouble(iniC.get("input" + i, "speed"))));
            }
            else if (iniC.get("input" + i, "purpose").equals("PlaySound")) {

                Audio a = new Audio();
                a.setFileName(iniC.get("input" + i, "audio"));

                controls.add(new Controller(Integer.parseInt(iniC.get("input" + i, "button")),
                        Integer.parseInt(iniC.get("input" + i, "index")),
                        Float.parseFloat(iniC.get("input" + i, "range")),
                        iniC.get("input" + i, "object"),
                        iniC.get("input" + i, "purpose"),
                        a));
            }
        }
    }

    public void setKeyboardControls(InputList inputs) throws IOException {
        Ini ini = new Ini(new File("./inputs/keyboard.ini"));
        int inputNum = Integer.parseInt(ini.get("control", "inputs"));

        for (int i = 0; i < inputNum; i++) {
            if (ini.get("input" + i, "purpose").equals("Create") ||
                    ini.get("input" + i, "purpose").equals("Destroy")) {

                inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                        Integer.parseInt(ini.get("input" + i, "action")),
                        ini.get("input" + i, "object"),
                        ini.get("input" + i, "purpose")));
            }
            else if (ini.get("input" + i, "purpose").equals("MoveX") ||
                    ini.get("input" + i, "purpose").equals("MoveY") ||
                    ini.get("input" + i, "purpose").equals("Jump")) {

                inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                        Integer.parseInt(ini.get("input" + i, "action")),
                        ini.get("input" + i, "object"),
                        ini.get("input" + i, "purpose"),
                        Double.parseDouble(ini.get("input" + i, "speed"))));
            }
            else if (ini.get("input" + i, "purpose").equals("PlaySound")) {

                Audio a = new Audio();
                a.setFileName(ini.get("input" + i, "audio"));

                inputs.add(new Input(Integer.parseInt(ini.get("input" + i, "key")),
                        Integer.parseInt(ini.get("input" + i, "action")) | GLFW_PRESS,
                        ini.get("input" + i, "object"),
                        ini.get("input" + i, "purpose"),
                        a));
            }
        }
    }

    public void renderHUD(){

    }
}