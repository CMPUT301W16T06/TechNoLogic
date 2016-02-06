package ca.ualberta.cs.technologic;

import java.util.ArrayList;

/**
 * Created by Jordan on 06/02/2016.
 */
public class User {
    private String name;
    private String username;
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
    }
}
