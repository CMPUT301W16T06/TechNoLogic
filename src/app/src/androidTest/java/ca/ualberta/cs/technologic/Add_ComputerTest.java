package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Jordan on 06/02/2016.
 *
 * Test for adding computer to list of computers owner wants to sell
 * Assumes there will be a computer class that takes (Description, hard drive, RAM, processor,
 *         model, make, OS, year)
 */
public class Add_ComputerTest extends ActivityInstrumentationTestCase2 {
    public Add_ComputerTest(Class activityClass) {
        super(activityClass);
    }

    public void testAddComputer(){
        //Add new user, because user contains the array of computers
        User testUser =  new User("John", "cooljohn123", "john@gmail.com", "403-132-4567",
                "password", "123 7th Ave NE Calgary");

        //test to add computer to the computer array
        //make sure the new computer object is added into the array
        Computer testcomputer = new Computer("Description","500gb","8gb","intel core i7",
                "surface pro 3","microsoft","windows",2014);
        ArrayList<Computer> temp_array = testUser.getCmputArray();
        ArrayList<Computer> test_array = new ArrayList<Computer>();

        temp_array.add(testcomputer);
        testUser.setCmputArray(temp_array);

        assertEquals(test_array,testUser.getCmputArray());

    }
}
