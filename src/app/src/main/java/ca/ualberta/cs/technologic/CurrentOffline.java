package ca.ualberta.cs.technologic;

import android.app.Activity;

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

/**
 * Created by Jessica on 2016-03-31.
 */
public class CurrentOffline {
    private ArrayList<Computer> currentComputerFile;
    private static CurrentOffline firstInstance = null;
    private String fileName = "computers.sav";

    private CurrentOffline() {}

    /**
     * Makes a singleton
     * @return the instance of the computers that need to be saved to file
     * and provides access to loading and saving file methods
     */
    public static CurrentOffline getInstance(){
        if(firstInstance == null){
            synchronized(CurrentUser.class){
                if(firstInstance == null){
                    firstInstance = new CurrentOffline();
                }
            }
        }
        return firstInstance;
    }

    /**
     * gets the singleton for offline behaviour, will be computers that
     * have been added but not saved
     * @return arraylist of computers
     */
    public ArrayList<Computer> getCurrentOffline() {
        return this.currentComputerFile;
    }

    /**
     * sets the singleton to the given computers
     * @param comps computers for the singleton
     */
    public void setCurrentOffline(ArrayList<Computer> comps) {
        this.currentComputerFile = comps;
    }

    /**
     * adds a new computer to the singleton
     * @param newCurrentComputer computer to add
     */
    public void addCurrentOffline(Computer newCurrentComputer){
        this.currentComputerFile.add(newCurrentComputer);
    }

    /**
     * when internet connected save computers to elastic search
     * @param fos file output strem for writing
     */
    public void saveCurrentOffline(FileOutputStream fos){
        saveComputerFile(this.currentComputerFile, fos);
        this.currentComputerFile.clear();
    }

    /**
     * Takes an array of computers from a file and saves to database
     * @param compsToSave computers to save
     */
    public void saveComputerFile(ArrayList<Computer> compsToSave, FileOutputStream fos){
        //try to save to file
        try {
            //create file stream to write to file, 0 for default write mode
//            FileOutputStream fos = openFileOutput(fileName, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();

            //converts array of Fuel objects to Json to store in file
            //and writes to file
            gson.toJson(compsToSave, out);
            out.flush();
            fos.close();

            //catch errors and throws runtime exceptions
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * loads the computers that have been saved to file
     * @return array of computers
     */
    public ArrayList<Computer> loadComputersFile(FileInputStream fis) {
        //computers from file will be stored in this array
        ArrayList<Computer> fileComputers;
        //try to read in fuel entries from file
        try {
            //create file stream to read a file
//            FileInputStream fis = openFileInput(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            //Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html Jan-21-2016
            //convert the type stored in file to an array of Computer objects
            Type listType = new TypeToken<ArrayList<Computer>>() {
            }.getType();
            fileComputers = gson.fromJson(in, listType);

            //catching errors
            //if file does not exist then create and empty array
        } catch (Exception e){
            if (e.getClass() == FileNotFoundException.class){
                fileComputers = new ArrayList<Computer>();
            } else {
                throw new RuntimeException();
            }
        }
        //return the array of Fuel objects
        return fileComputers;
    }

}
