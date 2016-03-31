package ca.ualberta.cs.technologic;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
 * Created by Eric on 2016-02-12.
 *  OfflineMode class automatically gets the current
 *      OfflineMode status of the phone. This sets
 *      if the application is in online mode or
 *      offline mode.
 */

public class OfflineMode extends Activity {
    private boolean isEnabled;
    private String fileName = "computers.sav";

    /**
     * Constructor for OfflineMode class
     */
    public OfflineMode() {
        //TODO automatically get Airplane mode
    }

    /**
     * gets the current online or offline status the app
     *  taken from stackoverflow Mar-3-2016
     *  http://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android
     * @return true if mode is on, false if mode is off
     */
    public static boolean getEnabled(Context ctx) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        //return isEnabled;
        return connected;
    }

    //Takes an array of Computer objects that need to be saved
    // and saves it to file while offline
    public void saveComputerFile(ArrayList<Computer> compsToSave){
        //try to save to file
        try {
            //create file stream to write to file, 0 for default write mode
            FileOutputStream fos = openFileOutput(fileName, 0);
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

    //Read the file and store the Computer objects into an Array and return that Array
    public ArrayList<Computer> loadComputersFile() {
        //computers from file will be stored in this array
        ArrayList<Computer> fileComputers;

        //try to read in fuel entries from file
        try {
            //create file stream to read a file
            FileInputStream fis = openFileInput(fileName);
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

    /**
     * sets the status of the app to online or offline
     * @param bool true if mode is on, false if mode is off
     */
    public void setEnabled(boolean bool) {
        //TODO change phone's airplane mode to online or offline
        this.isEnabled = bool;
    }

}
