package sources;

import java.util.ArrayList;

class InputList {

    private ArrayList<Input> inputs;

    InputList() {
        inputs = new ArrayList<>();
    }

    void add(Input i) {
        inputs.add(i);
    }

    Input get(int ndx) {
        return inputs.get(ndx);
    }

    int size() {
        return inputs.size();
    }

    void removeAll() {
        inputs.clear();
    }
}