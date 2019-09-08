package sources.objCode;

import java.util.Vector;

// list of created objects
public class ObjectList {

    private Vector<GameObject> oList;

    /*==================================================
                     Initialization
    ==================================================*/

    public ObjectList() {
        this.oList = new Vector<>();
    }

    /*==================================================
                    Object Control
    ==================================================*/

    // adds object to list
    public void addObject(GameObject o) {
        this.oList.add(o);
    }

    /*==================================================
                   Setters and Getters
    ==================================================*/

    public void setOList(Vector<GameObject> o) {
        this.oList = o;
    }

    Vector<GameObject> getOList() {
        return this.oList;
    }
}