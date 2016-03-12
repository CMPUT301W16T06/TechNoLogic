package ca.ualberta.cs.technologic;

import java.util.ArrayList;
import java.util.UUID;

import io.searchbox.annotations.JestId;

/**
 * Created by gknoblau on 2016-02-06.
 * I added a status variable that can only be Available, Bidded or Borrowed- Jordan
 */
public class Computer {
    @JestId
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
//    private static final String FILENAME = "computers.sav";
//    private ArrayList<Computer> computers = new ArrayList<Computer>();

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
        //computers.add(this);

    }

    public Computer(UUID id, String username, String make, String model, Integer year, String processor, Integer ram,
                    Integer hardDrive, String os, Float price, String description, String stauts) {
        this.id = id;
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
        this.setStatus(status);
        //computers.add(this);

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
        status = status.toLowerCase();
        ArrayList<String> statusOptions = new ArrayList<String>();
        statusOptions.add("available");
        statusOptions.add("bidded");
        statusOptions.add("borrowed");
        if (statusOptions.contains(status)){
            this.status = status;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return "Computer:" + this.id.toString() + " | " + this.description
                + " | status:" + this.status;
    }

}
