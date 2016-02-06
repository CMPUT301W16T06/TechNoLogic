package ca.ualberta.cs.technologic;

/**
 * Created by Jordan on 06/02/2016.
 */
public class Computer {
    private String description;
    private String harddrive;
    private String RAM;
    private String processor;
    private String model;
    private String make;
    private String OS;
    private int year;
    private int computerID;

    public Computer(String description, String harddrive, String RAM, String processor,
                    String model, String make, String OS, int year) {
        this.description = description;
        this.harddrive = harddrive;
        this.RAM = RAM;
        this.processor = processor;
        this.model = model;
        this.make = make;
        this.OS = OS;
        this.year = year;
    }
}
