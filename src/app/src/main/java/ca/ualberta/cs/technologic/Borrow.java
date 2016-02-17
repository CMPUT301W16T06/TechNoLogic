package ca.ualberta.cs.technologic;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by gknoblau on 2016-02-16.
 */
public class Borrow{
    private UUID computerID;
    private String username;
    private String owner;
    private static final String FILENAME = "borrows.sav";
    private ArrayList<Borrow> borrows = new ArrayList<Borrow>();

    /**
     * Create a Bid Object
     * @param computerID Id that corresponds to the computer
     */
    public Borrow(UUID computerID, String username) {
        this.computerID = computerID;
        this.username = username;
        this.owner = getOwner(computerID);

    }

    public UUID getComputerID() {
        return computerID;
    }

    public void setComputerID(UUID computerID) {
        this.computerID = computerID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    //Has to be implemented that it gets the owner from the computer ID
    public String getOwner(UUID computerID) {
        return owner;
    }
}
