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
        assertEquals("COMPUTER 2", testUser.getComputers().get(1).getDescription());
        assertEquals("Available", testUser.getComputers().get(1).getStatus());
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

    //03.02.01 As a user, I want to edit the contact information in my profile
    public void testEditUser() {
        User testUser = new User("connor","mcdavid");

        //Initialize user contact information
        testUser.setName("Connor");
        testUser.setAddress("Rexall place");
        testUser.setEmail("allstar@oilers.ca");
        testUser.setPhone("7801231234");

        //Check to see if that is set to the user
        assertEquals(testUser.getName(), "Connor");
        assertEquals(testUser.getAddress(), "Rexall Place");
        assertEquals(testUser.getEmail(), "allstar@oilers.ca");
        assertEquals(testUser.getPhone(), "7801231234");

        //Edit the user information
        testUser.setName("Connor123");
        testUser.setAddress("Rexall place123");
        testUser.setEmail("allstar123@oilers.ca");
        testUser.setPhone("7801234567");

        //Check to see if the user information is updated
        assertEquals(testUser.getName(), "Connor123");
        assertEquals(testUser.getAddress(), "Rexall Place123");
        assertEquals(testUser.getEmail(), "allstar123@oilers.ca");
        assertEquals(testUser.getPhone(), "7801234567");

    }

    //03.03.01 As a user, I want to, when a username is presented for a thing, retrieve and show
    // its contact information
    public void testViewRentersInfo () {
        //Create User
        User testUser = new User("connor","mcdavid");
        testUser.setName("Connor");
        testUser.setAddress("Rexall place");
        testUser.setEmail("allstar@oilers.ca");
        testUser.setPhone("7801231234");
        Computer testcomputer = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"this is a cool computer");

        testcomputer.setStatus("Bidded");
        testUser.addComputer(testcomputer);

        //Simulate a cliok on an object where that user owns it
        assertTrue(testUser.getComputers().contains(testcomputer.getId()));

        assertEquals(testUser.getName(), "Connor");
        assertEquals(testUser.getAddress(), "Rexall Place");
        assertEquals(testUser.getEmail(), "allstar@oilers.ca");
        assertEquals(testUser.getPhone(), "7801231234");
    }


    //04.01.01 As a borrower, I want to specify a set of keywords, and search for all things not
    // currently borrowed whose description contains all the keywords

    // and 04.01.01 As a borrower, I want search results to show each thing not currently borrowed
    // with its description, owner username, and status
    public void testSearch () {
        //Create user and computer and add computer to user
        User testUser = new User("cooljohn123","password");
        User testUser2 = new User("thisssucks", "passwrord");
        Computer testcomputer = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"this is a cool computer");
        String testWords = "Microsoft surface this is a cool computer";

        testcomputer.setStatus("Bidded");
        testUser2.addComputer(testcomputer);

        //Tests that the search works
        if (testcomputer.getStatus() == "Bidded") {
            assertTrue(testcomputer.getDescription().contains(testWords));
            assertTrue(testcomputer.getMake().contains(testWords));
            assertTrue(testcomputer.getModel().contains(testWords));
            assertEquals(testUser2.getComputers().get(0).getDescription(), "this is a cool computer");
            assertEquals(testUser2.getUsername(), "cooljohn123");
            assertEquals(testUser2.getComputers().get(0).getStatus(), "bidded");

        } else {
            assertFalse(testcomputer.getDescription().contains(testWords));
            assertFalse(testcomputer.getMake().contains(testWords));
            assertFalse(testcomputer.getModel().contains(testWords));
        }


    }
}

