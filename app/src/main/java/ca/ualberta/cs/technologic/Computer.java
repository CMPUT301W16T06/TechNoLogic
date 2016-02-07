package ca.ualberta.cs.technologic;

import java.util.Date;
import java.util.UUID;

/**
 * Created by gknoblau on 2016-02-06.
 */
public class Computer {
    private UUID id;
    private String make;
    private String model;
    private Integer year;
    private String processor;
    private Integer ram;
    private Integer hardDrive;
    private String os;
    private Float price;
    private String description;

    public Computer(String make, String model, Integer year, String processor, Integer ram,
                    Integer hardDrive, String os, Float price, String description) {
        this.id = UUID.randomUUID();
        this.make = make;
        this.model = model;
        this.year = year;
        this.processor = processor;
        this.ram = ram;
        this.hardDrive = hardDrive;
        this.os = os;
        this.price = price;
        this.description = description;
    }

    /**
     * Returns the make of the computer
     * @return
     */
    public String getMake() {
        return make;
    }

    /**
     * Sets the make of the computer
     * @param make
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Returns the model of the computer
     * @return
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the computer
     * @param model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns the year of the computer
     * @return
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Sets the year of the computer
     * @param year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Returns the processor type of the computer
     * @return
     */
    public String getProcessor() {
        return processor;
    }

    /**
     * Sets the type of processor in the computer
     * @param processor
     */
    public void setProcessor(String processor) {
        this.processor = processor;
    }

    /**
     * Returns the amount of ram in the computer
     * @return
     */
    public Integer getRam() {
        return ram;
    }

    /**
     * Sets the amount of ram in the computer
     * @param ram
     */
    public void setRam(Integer ram) {
        this.ram = ram;
    }

    /**
     * Returns the size of the harddrive of the computer in gigabytes
     * @return
     */
    public Integer getHardDrive() {
        return hardDrive;
    }

    /**
     * Sets the size of the computers harddrive in gigabytes
     * @param hardDrive
     */
    public void setHardDrive(Integer hardDrive) {
        this.hardDrive = hardDrive;
    }

    /**
     * Returns the operating system that is on the computer
     * @return
     */
    public String getOs() {
        return os;
    }

    /**
     * Sets the type of operating system on the computer
     * @param os
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     * Returns the minimum bid price on the computer
     * @return
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Sets the minimum bid price on the computer
     * @param price
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Returns the description of the computer
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the computer
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
