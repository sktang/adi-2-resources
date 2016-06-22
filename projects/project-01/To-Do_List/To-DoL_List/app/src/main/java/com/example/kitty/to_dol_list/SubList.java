package com.example.kitty.to_dol_list;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kitty on 6/21/16.
 */
public class SubList implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    private String listName;
    private ArrayList<Task> items;

    public SubList(String listName) {
        this.listName = listName;
        this.items = new ArrayList<>();
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<Task> getItems() {
        return items;
    }

    public void addItem(Task task) {
        items.add(0, task);
    }

    public void removeItem(Task task) {
        items.remove(task);
    }
}
