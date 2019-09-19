package sources;

import java.util.ArrayList;

public class InputList {

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

    public void removeAll() {
        inputs.clear();
    }
}