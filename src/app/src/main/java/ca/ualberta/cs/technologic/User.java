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
 * Changed everything to strings cause its not working and I don't wanna deal with typecasting right now... -greg
**/

public class User {
    private String name;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String address;

    //The user holds an array of all the computer objects they own
    private ArrayList<Computer> computers;

    //The user holds an array of all of their computers that have bids on them
    private ArrayList<Bid> myComputerBids;

    //The user holds an array of all computer ID that are lent out
    private ArrayList<UUID> LentOut;

    //The user will hold an array of all newBids for notification purposes
    private ArrayList<Bid> newBids;

    // Holds an array of all computer IDs that are in current possession
    private ArrayList<UUID> borrowing;

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

    //Will add a ID of Computer that has been bid on to the END of the myComputerBids array
    //Throws IllegalArgumentException if you try to add another computer with the same UUID
    public void addMyComputerBid(Bid newBid) throws IllegalArgumentException{
        //NEED TO IMPLEMENT

    }

    //add a computer id to the list of computers that are lent out to people
    public void addLentOut(UUID id){
        //NEED TO IMPLEMENT
    }

    //Returns all bids of computers of the specified ID
    public ArrayList<Bid> getComputerBids(UUID id){
        //NEED TO IMPLEMENT
        return new ArrayList<Bid>();
    }

    //after a bid is accpeted all bids with that computer id must be
    //removed from myComputerBids
    public void removeComputerBids(UUID id){
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

    //add a new bid to the bid list
    public void addNewBid(Bid bid){
        //NEED TO IMPLEMENT
    }

    public void clearNewBids(){
        // NEED TO IMPLEMENT
    }

    // decline a bid from myComputerBid list
    public void declineComputerBid(Bid bid) {
        //TODO
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

