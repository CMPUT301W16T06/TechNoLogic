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

    /**
     * sets the status of the app to online or offline
     * @param bool true if mode is on, false if mode is off
     */
    public void setEnabled(boolean bool) {
        //TODO change phone's airplane mode to online or offline
        this.isEnabled = bool;
    }

}
