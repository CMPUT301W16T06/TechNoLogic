package ca.ualberta.cs.technologic;


import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Eric on 2016-02-11.
 */
public class OfflineModeTest extends ActivityInstrumentationTestCase2 {

    public OfflineModeTest() {
        super(Computer.class);
    }

    /**
     * testOfflineMode checks the state of AirplaneMode on the phone
     *  Corresponds to US 08.01.01
     */
    public void testOfflineMode() {

        AirplaneMode mode = new AirplaneMode();
        mode.setEnabled(false);

        assertEquals("Expect airplane mode off", false, mode.getEnabled());

        mode.setEnabled(true);
        assertEquals("Expect airplane mode on", true, mode.getEnabled());
    }

}
