package fr.ottobruc.p4_mareu.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.Room;
import fr.ottobruc.p4_mareu.model.User;
import fr.ottobruc.p4_mareu.utils.Colors;

/**
 * A class for generating dummy data of Users, Rooms, and Meetings.
 */
public abstract class DummyMeetingGenerator {

    /**
     * A list of dummy user data.
     */
    public static List<User> DUMMY_USERS = Arrays.asList(
            new User("Néo", "neo@lamzone.com", "Programmeur"),
            new User("Luke Skywalker", "luke@lamzone.com", "Jedi"),
            new User("Hermione Granger", "hermione@lamzone.com", "Sorcière"),
            new User("James Bond", "jamesbond@lamzone.com", "Agent secret"),
            new User("Indiana Jones", "indianajones@lamzone.com", "Archéologue"),
            new User("Bob l'éponge", "bob@lamzone.com", "Eponge"),
            new User("Tony Stark", "tonystark@lamzone.com", "Iron Man"),
            new User("Ellen Ripley", "ripley@lamzone.com", "Astronaute"),
            new User("Marty McFly", "marty@lamzone.com", "Voyageur du temps"),
            new User("Leia Organa", "leia@lamzone.com", "Princesse"),
            new User("Harry Potter", "harry@lamzone.com", "Sorcier"),
            new User("Trinity", "trinity@lamzone.com", "Hackeuse"),
            new User("Capitaine Jack Sparrow", "jack@lamzone.com", "Pirate"),
            new User("Sarah Connor", "sarahconnor@lamzone.com", "Résistante"),
            new User("Frodon Sacquet", "frodo_bogoss@lamzone.com", "Hobbit"),
            new User("Romain", "romain@lamzone.com", "Mentor")
    );

    /**
     * A list of dummy room data.
     */
    public static List<Room> DUMMY_ROOMS = Arrays.asList(
            new Room(1, "Mario", Colors.MARIO),
            new Room(2, "Luigi", Colors.LUIGI),
            new Room(3, "Link", Colors.LINK),
            new Room(4, "Peach", Colors.PEACH),
            new Room(5, "Pikachu", Colors.PIKACHU),
            new Room(6, "Donkey Kong", Colors.DONKEY_KONG),
            new Room(7, "Bowser", Colors.BOWSER),
            new Room(8, "Yoshi", Colors.YOSHI),
            new Room(9, "Kirby", Colors.KIRBY),
            new Room(10, "Toad", Colors.TOAD)
    );

    /**
     * A list of dummy meeting data.
     */
    public static List<Meeting> DUMMY_MEETINGS;

    static {
        try {
            DUMMY_MEETINGS = Arrays.asList(
                    new Meeting(1, "09:00", "09:45", "14/07/2023", DUMMY_ROOMS.get(0), "Réunion A", Arrays.asList(DUMMY_USERS.get(10), DUMMY_USERS.get(1))),
                    new Meeting(2, "10:00", "10:45", "14/07/2023", DUMMY_ROOMS.get(1), "Réunion B", Arrays.asList(DUMMY_USERS.get(3), DUMMY_USERS.get(4))),
                    new Meeting(3, "11:00", "11:45", "14/07/2023", DUMMY_ROOMS.get(2), "Réunion C", Arrays.asList(DUMMY_USERS.get(5), DUMMY_USERS.get(6))),
                    new Meeting(4, "13:00", "13:45", "14/07/2023", DUMMY_ROOMS.get(3), "Réunion D", Arrays.asList(DUMMY_USERS.get(9), DUMMY_USERS.get(2))),
                    new Meeting(5, "14:00", "14:45", "14/07/2023", DUMMY_ROOMS.get(4), "Réunion E", Arrays.asList(DUMMY_USERS.get(1), DUMMY_USERS.get(2), DUMMY_USERS.get(3))),
                    new Meeting(6, "15:00", "15:45", "14/07/2023", DUMMY_ROOMS.get(5), "Réunion F", Arrays.asList(DUMMY_USERS.get(0), DUMMY_USERS.get(8), DUMMY_USERS.get(9))),
                    new Meeting(7, "16:00", "16:45", "14/07/2023", DUMMY_ROOMS.get(6), "Réunion G", Arrays.asList(DUMMY_USERS.get(11), DUMMY_USERS.get(6))),
                    new Meeting(8, "17:00", "17:45", "14/07/2023", DUMMY_ROOMS.get(7), "Réunion H", Arrays.asList(DUMMY_USERS.get(13), DUMMY_USERS.get(0))),
                    new Meeting(9, "18:00", "18:45", "14/07/2023", DUMMY_ROOMS.get(8), "Réunion I", Arrays.asList(DUMMY_USERS.get(12), DUMMY_USERS.get(2), DUMMY_USERS.get(3))),
                    new Meeting(10,"10:00", "10:45", "15/07/2023", DUMMY_ROOMS.get(9), "Réunion J", Arrays.asList(DUMMY_USERS.get(4), DUMMY_USERS.get(7)))
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a list of dummy meeting data.
     *
     * @return a new ArrayList of meetings copied from DUMMY_MEETINGS.
     */
    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    /**
     * Generates a list of dummy room data.
     *
     * @return a new ArrayList of rooms copied from DUMMY_ROOMS.
     */
    static List<Room> generateRooms() {
        return new ArrayList<>(DUMMY_ROOMS);
    }

    /**
     * Generates a list of dummy user data.
     *
     * @return a new ArrayList of users copied from DUMMY_USERS.
     */
    static List<User> generateUsers() {
        return new ArrayList<>(DUMMY_USERS);
    }
}
