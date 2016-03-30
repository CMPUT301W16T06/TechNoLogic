package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import java.util.UUID;


public class ReturningTest extends ActivityInstrumentationTestCase2 {

    public ReturningTest(Class activityClass) {
        super(activityClass);
    }

    /**
     * testReturn checks the state of a Computer before and after the return.
     *  Corresponds to US 07.01.01
     */
    //***EDITED
    public void testReturn() {
        //initial setup of test variables
        Computer testcomputer1 = null;
        testcomputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available", testcomputer1.getTime(),
                testcomputer1.getThumbnail());


        // Check before return
        ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testcomputer1);
        ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testcomputer1.getId()).setStatus("borrowed");
        assertEquals("Should be borrowed",
                ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testcomputer1.getId()).getStatus(), "borrowed");

        // Check after return
        ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testcomputer1.getId()).setStatus("available");
        assertEquals("Should be available",
                ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testcomputer1.getId()).getStatus(), "available");
    }
}
