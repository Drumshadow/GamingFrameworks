package sources;

import java.util.ArrayList;

class ControllerList {

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

    void removeAll() {
        inputs.clear();
    }
}
