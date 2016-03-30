package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Eric on 2016-02-11.
 */
public class ReturningTest extends ActivityInstrumentationTestCase2 {

    public ReturningTest() {
        super(Computer.class);
    }

    /**
     * testReturn checks the state of a Computer before and after the return.
     *  Corresponds to US 07.01.01
     */
    //***EDITED
    public void testReturn() {
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User user1 = new User("Tom");
        Computer testcomputer1 = null;
        testcomputer1 = new Computer(testcomputer1.getId(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available",null);


        // Check before return
        ElasticSearchComputer.addComputer(testcomputer1);
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).setStatus("borrowed");
        assertEquals("Should be borrowed",
                ElasticSearchComputer.getComputersById(testcomputer1.getId()).getStatus(), "borrowed");

        // Check after return
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).setStatus("available");
        assertEquals("Should be available",
                ElasticSearchComputer.getComputersById(testcomputer1.getId()).getStatus(), "available");
    }
}
