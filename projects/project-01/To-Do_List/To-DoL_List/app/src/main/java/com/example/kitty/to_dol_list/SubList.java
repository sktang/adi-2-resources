package com.example.kitty.to_dol_list;

import java.util.ArrayList;

/**
 * Created by kitty on 6/21/16.
 */
public class SubList {

    private String listName;
    private String listDescription;
    private ArrayList<Task> items;

    public SubList(String listName, String listDescription) {
        this.listName = listName;
        this.listDescription = listDescription;
        this.items = new ArrayList<>();
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public ArrayList<Task> getItems() {
        return items;
    }

    public void addItem(Task task) {
        items.add(task);
    }

    public void removeItem(Task task) {
        items.remove(task);
    }
}
