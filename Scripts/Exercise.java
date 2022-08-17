package com.example.myapplication;

import android.widget.Button;

import java.util.ArrayList;

public class Exercise {
    private String name;
    private int sets_size;
    private ArrayList<exercise_set> Sets = new ArrayList<>();
/*    private Button remove_sets_button;*/
    public Exercise(String name, int sets_size, ArrayList<exercise_set> sets) {
        this.name = name;
        this.sets_size = sets_size;
        Sets = sets;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getSets_size() {
        return sets_size;
    }

    public void setSets_size(int sets_size) {
        this.sets_size = sets_size;
    }

    public ArrayList<exercise_set> getSets() {
        return Sets;
    }

    public void setSets(ArrayList<exercise_set> sets) {
        Sets = sets;
    }

    /*public Button getRemove_sets_button() {
        return remove_sets_button;
    }

    public void setRemove_sets_button(Button remove_sets_button) {
        this.remove_sets_button = remove_sets_button;
    }*/
}
