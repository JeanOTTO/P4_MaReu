package fr.ottobruc.p4_mareu.model;

/**
 * The User class represents a user who can participate in a meeting.
 */
public class User {
    private static int userCounter = 0;
    private int id;
    private String name;
    private String email;
    private String job;

    /**
     * Construct a User instance with provided details.
     *
     * @param name  Name of the user.
     * @param email Email of the user.
     * @param job   Job of the user.
     */
    public User(String name, String email, String job) {
        id = userCounter;
        this.name = name;
        this.email = email;
        this.job = job;
        userCounter++;
    }

    /**
     * Construct a User instance with only the email provided.
     *
     * @param email Email of the user.
     */
    public User(String email) {
        id = userCounter;
        this.email = email;
        userCounter++;
    }

    /**
     * @return Identifier for the user.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Job of the user.
     */
    public String getJob() {
        return job;
    }
}
