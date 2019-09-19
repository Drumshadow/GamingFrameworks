package sources;

import java.util.ArrayList;

public class ControllerList {

    private ArrayList<Controller> inputs;
    ControllerList() {
        inputs = new ArrayList<>();
    }

    void add(Controller i) {
        inputs.add(i);
    }

    Controller get(int ndx) {
        return inputs.get(ndx);
    }

    int size() {
        return inputs.size();
    }

    public void removeAll() {
        inputs.clear();
    }
}
