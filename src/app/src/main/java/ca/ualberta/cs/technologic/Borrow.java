package ca.ualberta.cs.technologic;

import java.util.ArrayList;
import java.util.UUID;

import io.searchbox.annotations.JestId;

/**
 * Created by gknoblau on 2016-02-16.
 */
public class Borrow{
    @JestId
    private UUID borrowID;
    private UUID computerID;
    private String username;
    private String owner;
//    private static final String FILENAME = "borrows.sav";
//    private ArrayList<Borrow> borrows = new ArrayList<Borrow>();

    /**
     * Create a Bid Object
     * @param computerID Id that corresponds to the computer
     */
    public Borrow(UUID computerID, String username) {
        this.borrowID = UUID.randomUUID();
        this.computerID = computerID;
        this.username = username;
        this.owner = getOwner(computerID);
    }

    public Borrow(UUID computerID, String username, String owner) {
        this.borrowID = UUID.randomUUID();
        this.computerID = computerID;
        this.username = username;
        this.owner = owner;
    }

    public UUID getBorrowID() {
        return borrowID;
    }

    /**
     * Returns the computer Id of the borrowed computer
     * @return
     */
    public UUID getComputerID() {
        return computerID;
    }

    /**
     * Sets the computer ID of the borrowed computer
     * @param computerID
     */
    public void setComputerID(UUID computerID) {
        this.computerID = computerID;
    }

    /**
     * Returns the owner of the computer being borrowed
     * @return
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owener of the computer being borrowed
     * @param owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Returns the username of the borrower of the computer
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the borrower of the computer
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    //Has to be implemented that it gets the owner from the computer ID
    public String getOwner(UUID computerID) {
        return owner;
    }
}
