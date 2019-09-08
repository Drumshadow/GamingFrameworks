package sources.objCode;

import java.util.Vector;

// list of all created objects
public class ObjectList {

    private Vector<GameObject> oList;

    /*==================================================
                     Initialization
    ==================================================*/

    public ObjectList() {
        this.oList = new Vector<GameObject>();
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

    public Vector<GameObject> getOList() {
        return this.oList;
    }

    // TODO: add helpful functions
}
