package ca.ualberta.cs.technologic;

import android.app.Activity;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jessica on 2016-02-11.
 */
public class Bid{
    private UUID computerID;
    private Float price;
    private String username;
    private String owner;
    private static final String FILENAME = "bids.sav";
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    /**
     * Create a Bid Object
     * @param computerID Id that corresponds to the computer
     * @param price price of the bid
     */
    public Bid(UUID computerID, Float price, String username) {
        this.computerID = computerID;
        this.price = price;
        this.username = username;
        this.owner = getOwner(computerID);
    }


    public UUID getComputerID() {
        return computerID;
    }

    public void setComputerID(UUID computerID) {
        this.computerID = computerID;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    //Never will be used can be removed
    public void setOwner(String owner) {
        this.owner = owner;
    }
}
