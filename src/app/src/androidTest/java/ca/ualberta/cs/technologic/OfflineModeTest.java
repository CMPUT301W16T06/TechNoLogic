package ca.ualberta.cs.technologic;


import android.test.ActivityInstrumentationTestCase2;

public class OfflineModeTest extends ActivityInstrumentationTestCase2 {

    public OfflineModeTest(Class activityClass) {
        super(activityClass);
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
