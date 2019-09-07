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
}