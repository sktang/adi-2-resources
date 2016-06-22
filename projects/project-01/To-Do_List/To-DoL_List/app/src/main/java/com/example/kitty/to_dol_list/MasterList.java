package com.example.kitty.to_dol_list;

import java.util.ArrayList;

/**
 * Created by kitty on 6/21/16.
 */
public class MasterList {

    private static MasterList maststerList = null;
    private static ArrayList<SubList> toDoList;

    public MasterList() {
        toDoList = new ArrayList<>();
    }

    public static MasterList getInstance() {
        if(maststerList == null) {
            maststerList = new MasterList();
        }
        return maststerList;
    }

    public void addSubList(SubList subList) {
        toDoList.add(0, subList);
    }

    public void removeSubList(SubList subList) {
        toDoList.remove(subList);
    }

    public ArrayList<SubList> getSubLists() {
        return toDoList;
    }

    public SubList getSubList (int position) {
        return toDoList.get(position);
    }

    public void setSubList (int position, SubList updatedList) {
        toDoList.set(position, updatedList);
    }

}
