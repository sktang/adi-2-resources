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

    public MasterList getInstance() {
        if(maststerList == null) {
            maststerList = new MasterList();
        }
        return maststerList;
    }

    public void addSubList(SubList subList) {
        toDoList.add(subList);
    }

    public void removeSubList(SubList subList) {
        toDoList.remove(subList);
    }

    public ArrayList<SubList> getSubList() {
        return toDoList;
    }

}
