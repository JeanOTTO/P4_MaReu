package fr.ottobruc.p4_mareu.model;

import androidx.annotation.NonNull;

public class Room {
    private int id;
    private String name;
    private int color;

    public Room(int id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Room(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public int getColor() {
        return color;
    }

    @NonNull
    @Override
    public String toString() {
            return name;
    }
}
