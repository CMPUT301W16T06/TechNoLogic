package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

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
    public void testReturn() {
        User user1 = new User("Tom", "thetankengine");
        Computer comp = new Computer("Apple","MacBook",2013,"intel i5", 8,
                500,"Ios",Float.parseFloat("18.3"),"Sort of fast");

        // Check before return
        user1.addLentOut(comp.getId());
        assertEquals("Should be borrowed", comp.getStatus(), "Borrowed");

        // Check after return
        user1.returnLent(comp.getId());
        assertEquals("Should be available", comp.getStatus(), "Available");
    }
}
