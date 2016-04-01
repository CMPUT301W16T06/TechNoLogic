package ca.ualberta.cs.technologic;

import java.util.UUID;

import io.searchbox.annotations.JestId;

/**
 * Created by Jessica on 2016-03-31.
 */
public class Notification {
    @JestId
    private UUID id;
    private String username;
    private Integer count;

    //constructor
    public Notification(String username) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.count = 1;
    }

    /**
     * gets the id of the notification
     * @return notification id
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * returns the username associated wiht the notification
     * @return username of notification
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * sets the username to the new value passed in
     * @param username new username for the notification
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the current count of notifications
     * @return number of notifications
     */
    public Integer getCount() {
        return this.count;
    }

    /**
     * sets the number of notifications
     * @param count number of notifications
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}
