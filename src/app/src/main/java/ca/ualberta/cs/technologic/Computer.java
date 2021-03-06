package ca.ualberta.cs.technologic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import io.searchbox.annotations.JestId;

/**
 * One of the main classes contians all the information on computer including a picture
 * Main item that is being traded on the app
 */
public class Computer {
    @JestId
    private UUID id;
    private String username;
    private String make;
    private String model;
    private String year;
    private String processor;
    private String ram;
    private String hardDrive;
    private String os;
    private Float price;
    private String description;
    private String status = "available";
    private Date time;
    protected transient Bitmap thumbnail;
    protected String thumbnailBase64;
    static int TARGET_IMAGE_SIZE_BYTES = 65536;


    /**
     * Contructor of a computer
     * @param id
     * @param username
     * @param make
     * @param model
     * @param year
     * @param processor
     * @param ram
     * @param hardDrive
     * @param os
     * @param price
     * @param s
     * @param description
     * @param time
     */
    public Computer(UUID id, String username, String make, String model, String year, String processor, String ram,
                    String hardDrive, String os, Float price, String s, String description, Date time, Bitmap thumbnail) {
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
        this.setStatus(status);
        this.time = new Date(System.currentTimeMillis());
        addThumbnail(thumbnail);
    }

    public Computer(UUID id, String username, String make, String model, String year, String processor, String ram,
                    String hardDrive, String os, Float price, String description, String status, Bitmap thumbnail) {
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
        this.time = new Date(System.currentTimeMillis());
        addThumbnail(thumbnail);
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
    public String getYear() {
        return year;
    }

    /**
     * Sets the year of the computer
     *
     * @param year
     */
    public void setYear(String year) {
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
    public String getRam() {
        return ram;
    }

    /**
     * Sets the amount of ram in the computer
     *
     * @param ram
     */
    public void setRam(String ram) {
        this.ram = ram;
    }

    /**
     * Returns the size of the harddrive of the computer in gigabytes
     *
     * @return
     */
    public String getHardDrive() {
        return hardDrive;
    }

    /**
     * Sets the size of the computers harddrive in gigabytes
     *
     * @param hardDrive
     */
    public void setHardDrive(String hardDrive) {
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


    /**
     * Returns the status of the computer
     * @return
     */
    public String getStatus() {
        return status;
    }


    /**
     * Sets string status, will not accept any value other than Available, Bidded or Borrowed
     * @param status
     */
    public void setStatus(String status) {
        status = status.toLowerCase();
        ArrayList<String> statusOptions = new ArrayList<String>();
        statusOptions.add("available");
        statusOptions.add("bidded");
        statusOptions.add("borrowed");
        if (statusOptions.contains(status)){
            this.status = status;
        }
        else {
            this.status = "available";
        }
    }

    /**
     * get the date and time the computer was added
     * @return time cmputer was added
     */
    public Date getTime() {
        return time;
    }

    public void addThumbnail(Bitmap newThumbnail){
        if (newThumbnail != null) {
            thumbnail = newThumbnail;

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            newThumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
            thumbnailBase64 = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);

            while(thumbnailBase64.length() > TARGET_IMAGE_SIZE_BYTES){
                newThumbnail = Bitmap.createScaledBitmap(newThumbnail, newThumbnail.getWidth() / 2, newThumbnail.getHeight() / 2, false);
                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                newThumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                thumbnailBase64 = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            }
        }
    }

    public Bitmap getThumbnail(){
        if (thumbnail == null && thumbnailBase64 != null){
            byte[] decodeString = Base64.decode(thumbnailBase64, Base64.DEFAULT);
            thumbnail = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        return thumbnail;
    }

    @Override
    public String toString() {
        return "Computer:" + this.id.toString() + " | " + this.description
                + " | status:" + this.status;
    }

}
