package ca.ualberta.cs.technologic;

import java.util.UUID;

/**
 * Created by Jessica on 2016-02-11.
 */
public class Bid {
    UUID computerID;
    Double price;

    /**
     * Create a Bid Object
     * @param computerID Id that corresponds to the computer
     * @param price price of the bid
     */
    public Bid(UUID computerID, Double price) {
        this.computerID = computerID;
        this.price = price;
    }

    /**
     * Get the computer ID
     * @return Computer ID
     */
    public UUID getComputerID() {
        return computerID;
    }

    /**
     * Set the computer ID
     * @param computerID ID of computer
     */
    public void setComputerID(UUID computerID) {
        this.computerID = computerID;
    }

    /**
     * Get the price of the bid
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * set the price of the bid
     * @param price price of bid
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}
