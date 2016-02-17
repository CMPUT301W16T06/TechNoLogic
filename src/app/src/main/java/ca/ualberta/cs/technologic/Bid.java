package ca.ualberta.cs.technologic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jessica on 2016-02-11.
 */
public class Bid {
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
        loadFromFile();
        this.computerID = computerID;
        this.price = price;
        this.username = username;
        this.owner = getOwner(computerID);
        bids.add(this);
    }
    /**
     * Load the array from the file
     */
    private void loadFromFile() {
        try {
            //I dont know why this is giving me errors!! I copied it from lonely twitter
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<Bid>>() {}.getType();
            bids = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            bids = new ArrayList<Bid>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    /**
     * Save the array into the file
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(bids, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
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
