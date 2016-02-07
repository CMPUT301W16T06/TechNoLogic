package ca.ualberta.cs.technologic;

<<<<<<< HEAD
import android.location.Address;
import android.provider.ContactsContract;

import java.net.PasswordAuthentication;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by gknoblau on 2016-02-06.
=======
import java.util.ArrayList;

/**
 * Created by Jordan on 06/02/2016.
>>>>>>> 99e611386d39c74913553686d9e78c157c11c687
 */
public class User {
    private String name;
    private String username;
<<<<<<< HEAD
    private ContactsContract.CommonDataKinds.Email email;
    private ContactsContract.CommonDataKinds.Phone phone;
    private KeyStore.PasswordProtection password;
    private Address address;

    //I think this should be an array of ints for the ID's of the computers they own
    // If you think this is the wrong implimentation let me know
    private ArrayList<UUID> computers;

    /**
     * Minimum amount required to create an account
     * @param username
     * @param password
     */
    public User(String username,KeyStore.PasswordProtection password ) {
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
     * @param username
     */
    public void setUsername(String username) {
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
    public KeyStore.PasswordProtection getPassword() {
        return password;
    }

    /**
     * Sets the password through Password protection for the user
     * @param password
     */
    public void setPassword(KeyStore.PasswordProtection password) {
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
    public ArrayList<UUID> getComputers() {
        return computers;
    }

    public void addComputer(UUID ID) {
        computers.add(ID);
=======
    private String email;
    private String phonenum;
    private String password;
    private String address;
    private ArrayList<Computer> CmputArray;
    private int userID;

    public User(String name, String username, String email, String phonenum, String password, String address) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phonenum = phonenum;
        this.password = password;
        this.address = address;
    }

    public ArrayList<Computer> getCmputArray() {
        return CmputArray;
    }

    public void setCmputArray(ArrayList<Computer> cmputArray) {
        CmputArray = cmputArray;
>>>>>>> 99e611386d39c74913553686d9e78c157c11c687
    }
}
