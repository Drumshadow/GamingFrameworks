package sources.objCode;

import java.util.Vector;

// list of created objects
public class ObjectList {

    private Vector<GameObject> oList;

    /*==================================================
                     Initialization
    ==================================================*/

    // default constructor
    public ObjectList() {
        this.oList = new Vector<>();
    }

    // copy constructor
    public ObjectList(ObjectList other) {
        this.oList = new Vector<>(other.oList);
    }

    // value constructor
    public ObjectList(Vector<GameObject> o) {
        this.oList = o;
    }

    /*==================================================
                    Object Control
    ==================================================*/

    // adds given object to list
    public void addObject(GameObject o) {
        this.oList.add(o);
    }

    // removes given object from list
    public void removeObject(GameObject o) {
        this.oList.remove(o);
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    public void setOList(Vector<GameObject> o) {
        this.oList = o;
    }

    public Vector<GameObject> getOList() {
        return this.oList;
    }
}