package sources;

import java.util.ArrayList;

public class InputList {
    private ArrayList<Input> inputs;
    InputList() {
        inputs = new ArrayList<Input>();
    }

    public void add(Input i) {
        inputs.add(i);
    }

    public Input get(int ndx) {
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