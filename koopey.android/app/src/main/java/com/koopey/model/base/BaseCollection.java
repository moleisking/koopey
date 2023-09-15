package com.koopey.model.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class BaseCollection<T extends Base> implements Serializable, Comparator<BaseCollection<T>>, Comparable<BaseCollection<T>> {

    public BaseCollection() {
        items = new ArrayList();
    }

    private List<T> items;

    public void add(T item) {
        if (!this.contains(item)) {
            this.items.add(item);
        } else {
            this.set(item);
        }
    }

    @Override
    public int compare(BaseCollection<T> a, BaseCollection<T> b) {
        //-1 not the same, 0 is same, 1 > is same but larger
        int result = -1;
        if (a.size() < b.size()) {
            result = -1;
        } else if (a.size() > b.size()) {
            result = 1;
        } else {
            //Sort both lists before compare
            a.sort();
            b.sort();
            //Check each tag in tags
            for (int i = 0; i < a.size(); i++) {
                if (!a.contains(b.get(i))) {
                    result = -1;
                    break;
                } else if (i == b.size() - 1) {
                    result = 0;
                    break;
                }
            }
        }
        return result;
    }

    public int compareTo(BaseCollection<T> o) {
        return this.compare(this, o);
    }

    public boolean contains(T item) {
        if (this.get(item) != null) {
            return true;
        } else {
            return false;
        }
    }

    public T get(int i) {
        return items.get(i);
    }

    public T get(T item) {
        T result = null;
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).id.equals(item.id) ||
                    this.items.get(i).type.equals(item.type)) {
                result = this.items.get(i);
                break;
            }
        }
        return result;
    }

    public boolean isEmpty() {
        return this.size() == 0 ? true : false;
    }

    public void set(T item) {
        for (int x = 0; x < this.items.size(); x++) {
            if (this.items.get(x).type.equals(item.type)) {
                this.items.set(x, item);
            }
        }
    }

    public int size() {
        return this.items.size();
    }

    public void sort() {
        Collections.sort(items);
    }

}
