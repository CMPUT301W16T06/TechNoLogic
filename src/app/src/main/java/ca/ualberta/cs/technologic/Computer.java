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
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by gknoblau on 2016-02-06.
 * I added a status variable that can only be Available, Bidded or Borrowed- Jordan
 */
public class Computer {
    private UUID id;
    private String username;
    private String make;
    private String model;
    private Integer year;
    private String processor;
    private Integer ram;
    private Integer hardDrive;
    private String os;
    private Float price;
    private String description;
    private String status = "Available";
    private static final String FILENAME = "computers.sav";
    private ArrayList<Computer> computers = new ArrayList<Computer>();

    /**
     * Contructor of a computer
     * @param username
     * @param make
     * @param model
     * @param year
     * @param processor
     * @param ram
     * @param hardDrive
     * @param os
     * @param price
     * @param description
     */
    public Computer(String username, String make, String model, Integer year, String processor, Integer ram,
                    Integer hardDrive, String os, Float price, String description) {
        loadFromFile();
        this.id = UUID.randomUUID();
        this.username = username;
        this.make = make;
        this.model = model;
        this.year = year;
        this.processor = processor;
        this.ram = ram;
        this.hardDrive = hardDrive;
        this.os = os;
        this.price = price;
        this.description = description;
        computers.add(this);
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
            Type listType = new TypeToken<ArrayList<Computer>>() {}.getType();
            computers = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            computers = new ArrayList<Computer>();
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
            gson.toJson(computers, out);
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
     * Returns the unique ID
     * @return
     */
    public UUID getId () {return id;}

    /**
     * Returns the username of the owner of the computer
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the owerner of the computer
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the make of the computer
     *
     * @return
     */

    public String getMake() {
        return make;
    }

    /**
     * Sets the make of the computer
     *
     * @param make
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Returns the model of the computer
     *
     * @return
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the computer
     *
     * @param model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns the year of the computer
     *
     * @return
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Sets the year of the computer
     *
     * @param year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Returns the processor type of the computer
     *
     * @return
     */
    public String getProcessor() {
        return processor;
    }

    /**
     * Sets the type of processor in the computer
     *
     * @param processor
     */
    public void setProcessor(String processor) {
        this.processor = processor;
    }

    /**
     * Returns the amount of ram in the computer
     *
     * @return
     */
    public Integer getRam() {
        return ram;
    }

    /**
     * Sets the amount of ram in the computer
     *
     * @param ram
     */
    public void setRam(Integer ram) {
        this.ram = ram;
    }

    /**
     * Returns the size of the harddrive of the computer in gigabytes
     *
     * @return
     */
    public Integer getHardDrive() {
        return hardDrive;
    }

    /**
     * Sets the size of the computers harddrive in gigabytes
     *
     * @param hardDrive
     */
    public void setHardDrive(Integer hardDrive) {
        this.hardDrive = hardDrive;
    }

    /**
     * Returns the operating system that is on the computer
     *
     * @return
     */
    public String getOs() {
        return os;
    }

    /**
     * Sets the type of operating system on the computer
     *
     * @param os
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     * Returns the minimum bid price on the computer
     *
     * @return
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Sets the minimum bid price on the computer
     *
     * @param price
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Returns the description of the computer
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the computer
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    public String getStatus() {
        return status;
    }


    //Sets string status, will not accept any value other than Available, Bidded or Borrowed
    public void setStatus(String status) throws IllegalArgumentException {
        ArrayList<String> statusOptions = new ArrayList<String>();
        statusOptions.add("Available");
        statusOptions.add("Bidded");
        statusOptions.add("Borrowed");
        if (statusOptions.contains(status)){
            this.status = status;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

}
