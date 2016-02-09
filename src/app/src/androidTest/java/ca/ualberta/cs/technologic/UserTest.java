package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import java.security.KeyStore;
import java.util.ArrayList;

/**
 * Created by Jordan on 08/02/2016.
 *
 * Tests for the User class, covers tests for use cases: Add_Computer, Edit_Computer
 *          Delete_Computer and View_Computer
 */
public class UserTest extends ActivityInstrumentationTestCase2 {

    public UserTest(Class activityClass) {
        super(activityClass);
    }

    //Tests the adding of a computer item to the user's computer list
    //Test that corresponds with US 1.01.01 (Add_Computer)
    public void testaddComputer(){
        //initial setup of test variables
        User testUser = new User("cooljohn123","password");
        Computer testcomputer = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"this is a cool computer");
        ArrayList<Computer> testArray = new ArrayList<Computer>();

        testUser.addComputer(testcomputer);
        testArray.add(testcomputer);

        assertEquals(testArray,testUser.getComputers());

        //try adding the same computer twice, and make sure it raises error
        try{
            testUser.addComputer(testcomputer);
            assertTrue(Boolean.FALSE);
        }catch (IllegalArgumentException e) {
            assertTrue(Boolean.TRUE);
        }
    }

    //Test to make sure getComputers works and that you can view status and
    //                  descriptions of all computers
    //Test that correstponds with US 1.02.01 (View_Computer_List)
    public void testgetComputers(){
        User testUser = new User("cooljohn123","password");
        Computer testcomputer1 = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"COMPUTER 1");
        Computer testcomputer2 = new Computer("Apple","MacBook",2014,"intel i7", 8,
                500,"Mac",Float.parseFloat("56.2"),"COMPUTER 2");

        //add computer objects to both the user and test arrays
        testUser.addComputer(testcomputer1);
        testUser.addComputer(testcomputer2);

        //Make sure you can correctly access the description and status from the user's computers
        for (int i = 0; i < testUser.getComputers().size(); i++){
            assertEquals("Available", testUser.getComputers().get(i).getStatus());
        }
        assertEquals("COMPUTER 1",testUser.getComputers().get(0).getDescription());
        assertEquals("COMPUTER 2",testUser.getComputers().get(0).getDescription());

    }

    //Test to find computer index by UUID also test that you can view description and status
    //     after you've found the computer
    //Test that corresponds with US 1.03.01 (View_Computer)
    public void testgetComputerIndex(){
        //initial setup of test variables
        User testUser = new User("cooljohn123","password");
        Computer testcomputer1 = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"COMPUTER 1");
        Computer testcomputer2 = new Computer("Apple","MacBook",2014,"intel i7", 8,
                500,"Mac",Float.parseFloat("56.2"),"COMPUTER 2");
        Computer testcomputer3 = new Computer("Linux","Computer",2014,"intel i7", 8,
                500,"Linux",Float.parseFloat("51.2"),"COMPUTER 3");


        ArrayList<Computer> testArray = new ArrayList<Computer>();

        //add computer objects to both the user and test arrays
        testUser.addComputer(testcomputer1);
        testUser.addComputer(testcomputer2);

        //check that it returns the right index
        assertEquals(1, testUser.getComputerIndex(testcomputer2.getId()));
        assertEquals("COMPUTER 2",testUser.getComputers().get(1).getDescription());
        assertEquals("Available",testUser.getComputers().get(1).getStatus());
        //check to make sure if not in the user's computers returns -1
        assertEquals(-1,testUser.getComputerIndex(testcomputer3.getId()));
    }

    // Tests the ability to edit a computer if you know it's UUID
    // Test that corresponds to US 1.04.01 (Edit_Computer)
    public void testeditComputer(){
        //initial setup of test variables
        User testUser = new User("cooljohn123","password");
        Computer testcomputer1 = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"COMPUTER 1");
        Computer testcomputer2 = new Computer("Apple","MacBook",2014,"intel i7", 8,
                500,"Mac",Float.parseFloat("56.2"),"COMPUTER 2");
        ArrayList<Computer> testArray = new ArrayList<Computer>();

        //add DIFFERENT computer objects to each array
        testUser.addComputer(testcomputer1);
        testArray.add(testcomputer2);

        testUser.editComputer(testcomputer1.getId(), "Apple", "MacBook", 2014, "intel i7", 8,
                500, "Mac", Float.parseFloat("56.2"), "COMPUTER 2");
        //make sure the userComputer maintains  unique UUID
        assertNotSame(testArray,testUser.getComputers());

        assertEquals("Apple",testUser.getComputers().get(0).getMake());

    }

    //Test that corresponds with US 1.05.01 (Delete_Computer)
    public void testdeleteComputer(){
        //initial setup of test variables
        User testUser = new User("cooljohn123","password");
        Computer testcomputer1 = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"COMPUTER 1");
        Computer testcomputer2 = new Computer("Apple","MacBook",2014,"intel i7", 8,
                500,"Mac",Float.parseFloat("56.2"),"COMPUTER 2");
        ArrayList<Computer> testArray = new ArrayList<Computer>();

        //add computer objects to both the user and test arrays
        testUser.addComputer(testcomputer1);
        testUser.addComputer(testcomputer2);
        testArray.add(testcomputer1);
        testArray.add(testcomputer2);

        //delete the first computer in the array
        testUser.deleteComputer(testcomputer1.getId());
        testArray.remove(0);
        assertEquals(testArray, testUser.getComputers());

        //empty the array
        testUser.deleteComputer(testcomputer2.getId());

        //try deleting item not in the array
        //make sure it throws and exception
        try {
            testUser.deleteComputer(testcomputer1.getId());
            assertFalse(Boolean.TRUE);
        } catch (Exception e){
            assertTrue(Boolean.TRUE);
        }

    }


    //test creation of account and unique usernames as well as some getters and setters for User
    //Test corresponds to US 03.01.01 (Unique_Profile)
    public void testsetUsername (){
        User testUser = new User("cooljohn123","password");
        User testUser2 = new User("coolbob123","password");

        //Make sure your username is unique (when you change your username)
        //If not unique it will throw IllegalArgumentException
        try {
            testUser.setUsername("coolbob123");
            assertTrue(Boolean.FALSE);
        } catch (IllegalArgumentException e){
            assertTrue(Boolean.TRUE);
        }

        //Make sure your username is unique when you create an account
        try{
            User testUser3 = new User("coolbob123","password");
            assertTrue(Boolean.FALSE);
        } catch (IllegalArgumentException e){
            assertTrue(Boolean.TRUE);
        }

        testUser.setName("John Buckingham");
        assertEquals("John Buckingham",testUser.getName());
    }
}
