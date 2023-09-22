package com.koopey.model.base;

import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseCollection<T extends Base> extends ArrayList<T> {

    public void set(T t) {
        int i = indexOf(t);
        if (i >= 0) {
            this.set(i, t);
        } else {
            this.add(t);
        }

    }

    public int compare(BaseCollection<T> a, BaseCollection<T> b) {
        //-1 not the same, 0 is same, 1 > is same but larger
        // int result = -1;
        if (a.size() < b.size()) {
            return -1;
        } else if (a.size() > b.size()) {
            return 1;
        } else {
            //Sort both lists before compare
            a.sort();
            b.sort();
            //Check each tag in tags
            for (int i = 0; i < a.size(); i++) {
                if (!a.contains(b.get(i))) {
                    return -1;
                    //break;
                } else if (i == b.size() - 1) {
                    return 0;
                    //break;
                }
            }
        }
        return -1;
    }

    public T get(T t) {
        int i = this.indexOf(t);
        if (i >= 0) {
            return this.get(i);
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return this.size() == 0 ? true : false;
    }

    public void sort() {
        Collections.sort(this);
    }
}
