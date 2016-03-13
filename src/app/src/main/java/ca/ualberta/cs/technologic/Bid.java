package ca.ualberta.cs.technologic;

import android.app.Activity;
import java.util.ArrayList;
import java.util.UUID;

import io.searchbox.annotations.JestId;

/**
 * Created by Jessica on 2016-02-11.
 */
public class Bid{
    @JestId
    private UUID bidID;
    private UUID computerID;
    private Float price;
    private String username;
    private String owner;


    /**
     * Create a Bid Object
     * @param computerID Id that corresponds to the computer
     * @param price price of the bid
     */
    public Bid(UUID computerID, Float price, String username, String owner) {
        this.bidID = UUID.randomUUID();
        this.computerID = computerID;
        this.price = price;
        this.username = username;
        this.owner = owner;
    }

    /**
     * Returns the bid ID of the Bid
     * @return
     */
    public UUID getBidID() {
        return bidID;
    }

    /**
     * Returns the Computer ID of the bid
     * @return
     */
    public UUID getComputerID() {
        return computerID;
    }

    /**
     * Sets the computer ID of the bid
     * @param computerID
     */
    public void setComputerID(UUID computerID) {
        this.computerID = computerID;
    }

    /**
     * Returns the price of the bid
     * @return
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Sets the price of the bid
     * @param price
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Returns the username of the bidder
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the bidder
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    //I donno what to write for this class
    //@jklotz can you write the javadoc for this one
    @Override
    public String toString() {
        final Computer[] comp = {null};
        final UUID id = this.computerID;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                //comps = ElasticSearchComputer.getComputers(cu.getCurrentUser());
                comp[0] = ElasticSearchComputer.getComputersById(id);
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return comp[0].getDescription() + "\n" + "owner:" + this.owner + "\n" +
                "bidder:" + this.username + "\n" + "bid:" + this.price;
    }

}
