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
 * Created by gknoblau on 2016-02-16.
 */
public class Borrow {
    private UUID computerID;
    private String username;
    private String owner;
    private static final String FILENAME = "borrows.sav";
    private ArrayList<Borrow> borrows = new ArrayList<Borrow>();

    /**
     * Create a Bid Object
     * @param computerID Id that corresponds to the computer
     */
    public Borrow(UUID computerID, String username) {
        loadFromFile();
        this.computerID = computerID;
        this.username = username;
        this.owner = getOwner(computerID);
        borrows.add(this);
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
            Type listType = new TypeToken<ArrayList<Borrow>>() {}.getType();
            borrows = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            borrows = new ArrayList<Borrow>();
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
            gson.toJson(borrows, out);
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
}
