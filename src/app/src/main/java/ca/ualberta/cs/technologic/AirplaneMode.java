package ca.ualberta.cs.technologic;

/**
 * Created by Eric on 2016-02-12.
 *  AirplaneMode class automatically gets the current
 *      AirplaneMode status of the phone. This sets
 *      if the application is in online mode or
 *      offline mode.
 */

public class AirplaneMode {
    private boolean isEnabled;

    /**
     * Constructor for AirplaneMode class
     */
    public AirplaneMode() {
        //TODO automatically get Airplane mode
    }

    /**
     * gets the current online or offline status the app
     * @return true if mode is on, false if mode is off
     */
    public boolean getEnabled() {
        return isEnabled;
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
