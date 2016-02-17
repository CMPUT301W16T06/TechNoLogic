package ca.ualberta.cs.technologic;

import android.provider.ContactsContract;
import android.location.Address;

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
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by gknoblau on 2016-02-06.
 * Changed the password back to just a string - Jordan
 * Added deleteComputer, addComputer, editComputer and getComputerIndex methods (not implemented)
 * We have to add checking that username is unique in constructor and setUsername
 * Changed everything to strings cause its not working and I don't wanna deal with typecasting right now... -greg
**/

public class User {
    private String name;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String address;
    private static final String FILENAME = "users.sav";
    private ArrayList<User> users = new ArrayList<User>();


    /**
     * Minimum amount required to create an account
     * @param username
     * @param password
     */
    public User(String username,String password ) {
        loadFromFile();
        this.username = username;
        this.password = password;
        //need to add a check to see if the username is unique
        users.add(this);
        saveInFile();
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
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            users = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            users = new ArrayList<User>();
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
            gson.toJson(users, out);
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

    /**
     * Returns the name for the user
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name for the user
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the username for the user
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username for the user
     * Will throw IllegalArgumentException if username is not Unique
     * @param username
     */
    public void setUsername(String username) throws IllegalArgumentException{
        this.username = username;
    }

    /**
     * Returns the email of the user
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email for the user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the user
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the phone number of the user
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the password from password protection of the user
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password through Password protection for the user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the address of the user
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get list of computers ID's that the user owns
     * @return
     */
    public ArrayList<Computer> getComputers() {
        return computers;
    }

    /**
     * Get list of computers bids on the owners computers
     * @return
     */
    public ArrayList<Bid> getMyComputerBids() {
        return myComputerBids;
    }

    /**
     * Gets the list of computer IDs of the owners computers that are lent out
     * @return
     */
    public ArrayList<UUID> getLentOut() {
        return LentOut;
    }

    /**
     * Gets the list of new bids
     * @return
     */
    public ArrayList<Bid> getNewBids() {
        return newBids;
    }

    public ArrayList<UUID> getBorrowing(){
        return borrowing;
    }
    /**
     * A Lent out computer has been returned
     */
    public void returnLent(UUID compID) {
        //TODO
    }

    /**
     * User has possession of another User's Computer
     */
    public void addBorrowing(UUID compID) {
        //TODO
    }


}

