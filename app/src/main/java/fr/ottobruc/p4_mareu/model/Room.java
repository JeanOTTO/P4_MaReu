package fr.ottobruc.p4_mareu.model;

import androidx.annotation.NonNull;

/**
 * The Room class represents a room where a meeting can be held.
 */
public class Room {
    private int id;
    private String name;
    private int color;

    /**
     * Construct a Room instance with provided details.
     *
     * @param id    Identifier for the room.
     * @param name  Name of the room.
     * @param color Color associated with the room.
     */
    public Room(int id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    /**
     * Construct a Room instance with only the name provided.
     *
     * @param name Name of the room.
     */
    public Room(String name) {
        this.name = name;
    }

    /**
     * @return Identifier for the room.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Name of the room.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Color associated with the room.
     */
    public int getColor() {
        return color;
    }

    /**
     * Overrides the default toString() method to provide a custom string representation of the Room instance.
     *
     * @return The name of the room.
     */
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
