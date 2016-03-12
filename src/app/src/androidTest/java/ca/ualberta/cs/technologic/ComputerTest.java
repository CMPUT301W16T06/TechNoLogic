package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

/**
 * Created by Jordan on 08/02/2016.
 */
public class ComputerTest extends ActivityInstrumentationTestCase2 {

    public ComputerTest() {
        super(Computer.class);
    }

    //Test to make sure the status of a Computer can only be Available, Bidded or Borrowed
    //Test for corresponding US 2.01.01 (View_Status)
    public void testsetStatus(){
        User user1 = new User("Tom");
        Computer testcomputer1 = new Computer(user1.getUsername(),"Apple", "MacBook", 2013, "intel i5", 8,
                500, "Ios", Float.parseFloat("18.3"), "Sort of fast");

        //Make sure the default value of Computer is "Available"
        assertEquals("Available", testcomputer1.getStatus());

        testcomputer1.setStatus("Bidded");
        assertEquals("Bidded",testcomputer1.getStatus());

        try {
            testcomputer1.setStatus("Hello");
            assertTrue(Boolean.FALSE);
        } catch (IllegalArgumentException e){
            assertTrue(Boolean.TRUE);
        }
    }
}
