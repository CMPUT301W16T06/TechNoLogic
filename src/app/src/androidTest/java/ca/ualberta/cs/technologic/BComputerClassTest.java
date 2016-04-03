package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import java.util.UUID;

/**
 * Created by Jordan on 01/04/2016.
 */
public class BComputerClassTest extends ActivityInstrumentationTestCase2 {
    public BComputerClassTest(){
        super(BComputerClassTest.class);
    }

    //US 02.01.01 | As an owner or borrower, I want a thing to have a status of one of: available,
    // bidded, or borrowed
    public void testStatus(){
        Computer testComputer = new Computer(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"),
                "cooljohn123","Microsoft","Surface","2014",
                "intel i7","8","500","windows",Float.parseFloat("34.2"),"computer","available",null,null);

        testComputer.setStatus("bidded");
        assertEquals(testComputer.getStatus(),"bidded");
    }
}
