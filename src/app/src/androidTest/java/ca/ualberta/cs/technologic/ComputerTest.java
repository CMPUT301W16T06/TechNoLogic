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
        Computer testcomputer1 = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"COMPUTER 1");

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
