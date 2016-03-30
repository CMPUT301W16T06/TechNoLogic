package ca.ualberta.cs.technologic;

import android.text.format.Time;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import io.searchbox.annotations.JestId;


public class Bid{
    @JestId
    private UUID bidID;
    private UUID computerID;
    private Float price;
    private String username;
    private String owner;
    private Date time;


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
        this.time = new Date(System.currentTimeMillis());
    }

    /**
     *
     * @return bid ID of the Bid
     */
    public UUID getBidID() {
        return bidID;
    }

    /**
     *
     * @return Computer ID of the bid
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
     *
     * @return price of the bid
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
     *
     * @return username of the bidder
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

    public String getOwner() {
        return owner;
    }

    /**
     * get the time the bid was made
     * @return time bid was made
     */
    public Date getTime() {
        return time;
    }

    /**
     * format the bid object for display when listed
     * shows the description of computer, owner, bidder and price
     * @return
     */
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
