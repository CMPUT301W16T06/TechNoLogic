package ca.ualberta.cs.technologic;

import android.app.Activity;
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

public class User{
    private String name;
    private String username;
    private String email;
    private String phone;
    private String address;
    private static final String FILENAME = "users.sav";
    private ArrayList<User> users = new ArrayList<User>();


    /**
     * Minimum amount required to create an account
     * @param username
     */
    public User(String username) {
        this.username = username;
        //need to add a check to see if the username is unique
        //jk check is done in the controller
        users.add(this);
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

