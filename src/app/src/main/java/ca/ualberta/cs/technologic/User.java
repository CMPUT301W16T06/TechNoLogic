package ca.ualberta.cs.technologic;

import android.provider.ContactsContract;
import android.location.Address;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by gknoblau on 2016-02-06.
 * Changed the password back to just a string - Jordan
 * Added deleteComputer, addComputer, editComputer and getComputerIndex methods (not implemented)
 * We have to add checking that username is unique in constructor and setUsername
**/

public class User {
    private String name;
    private String username;
    private ContactsContract.CommonDataKinds.Email email;
    private ContactsContract.CommonDataKinds.Phone phone;
    private String password;
    private Address address;

    //The user holds an array of all the computer objects they own
    private ArrayList<Computer> computers;

    //Will add a Computer to the END of the computers array
    //Throws IllegalArgumentException if you try to add another computer with the same UUID
    public void addComputer(Computer newComputer) throws IllegalArgumentException{
        //NEED TO IMPLEMENT

    }

    //Will delete the computer that matches the UUID passed in from the list of computers
    //that the user owns
    //If ID not found in computer list throws IllegalArgumentException
    public void deleteComputer(UUID ID) throws IllegalArgumentException{
        //NEED TO IMPLEMENT
    }

    //Pass in the UUID of the computer you wish to change as well as all the information about
    //that computer and "editComputer" will find the computer that match the UUID and change
    //it's attributes
    public void editComputer(UUID ID,String make, String model, Integer year, String processor,
                             Integer ram, Integer hardDrive, String os, Float price,
                             String description){
        //NEED TO IMPLEMENT
    }


    //Searches for computer in list user's computers, returns index
    //if not in the user's computers returns -1
    public int getComputerIndex(UUID ID){
        //NEED TO IMPLEMENT
        return -1;
    }


    /**
     * Minimum amount required to create an account
     * @param username
     * @param password
     */
    public User(String username,String password ) {
        this.username = username;
        this.password = password;
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
    public ContactsContract.CommonDataKinds.Email getEmail() {
        return email;
    }

    /**
     * Set the email for the user
     * @param email
     */
    public void setEmail(ContactsContract.CommonDataKinds.Email email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the user
     * @return
     */
    public ContactsContract.CommonDataKinds.Phone getPhone() {
        return phone;
    }

    /**
     * Set the phone number of the user
     * @param phone
     */
    public void setPhone(ContactsContract.CommonDataKinds.Phone phone) {
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
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the user
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Get list of computers ID's that the user owns
     * @return
     */
    public ArrayList<Computer> getComputers() {
        return computers;
    }

}
