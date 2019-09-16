package sources;

import java.util.ArrayList;

public class ControllerList {
    private ArrayList<Controller> inputs;
    ControllerList() {
        inputs = new ArrayList<Controller>();
    }

    public void add(Controller i) {
        inputs.add(i);
    }

    public Controller get(int ndx) {
        return inputs.get(ndx);
    }

    public int size() {
        return inputs.size();
    }

    public void removeAll() {
        for (int i = 0; i < inputs.size(); i++) {
            inputs.remove(i);
        }
    }
}
