
package ca.ualberta.cs.technologic.oldTests;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import java.util.UUID;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.User;

/**
 * Created by Jordan on 08/02/2016.
 *
 * Tests for the User class, covers tests for use cases: Add_Computer, Edit_Computer
 *          Delete_Computer and View_Computer
 */
public class UserTest extends ActivityInstrumentationTestCase2 {

    public UserTest () {
        super(UserTest.class);
    }

    //public UserTest(Class activityClass) {
        //super(activityClass);
    //}

    //Tests the adding of a computer item to the user's computer list
    //Test that corresponds with US 1.01.01 (Add_Computer)
    // ***EDITED
    public void testaddComputer() {
        //initial setup of test variables
        Computer testComputer1 = null;
        testComputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available", null, null);

        assertNotNull(testComputer1);

        //setting status to bidded
        testComputer1.setStatus("bidded");
        ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testComputer1);

        //checks if the computer is in the elastic search
        Assert.assertEquals(testComputer1, ElasticSearchComputer.getComputersById(testComputer1.getId()));
        assertEquals(testComputer1, ca.ualberta.cs.technologic.ElasticSearchComputer.getComputers(testComputer1.getUsername()));


        //try adding the same computer twice, and make sure it raises error
        try {
            ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testComputer1);
            assertTrue(Boolean.FALSE);
        } catch (IllegalArgumentException e) {
            assertTrue(Boolean.TRUE);
        }
    }

    //Test to make sure getComputers works and that you can view status and
    //                  descriptions of all computers
    //Test that corresponds with US 1.02.01 (View_Computer_List)
    //***EDITED
    public void testgetComputers() {
        //initial setup of test variables
        Computer testComputer1 = null;
        testComputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "COMPUTER 1", "available", testComputer1.getTime(),
                testComputer1.getThumbnail());
        Computer testComputer2 = null;
        testComputer2 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "COMPUTER 2", "available", testComputer2.getTime(),
                testComputer2.getThumbnail());

        //add computer objects to elastic search
        ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testComputer1);
        ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testComputer2);

        //checks status
        assertEquals("available", ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getStatus());
        assertEquals("available", ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer2.getId()).getStatus());

        //checks description
        assertEquals("available", ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getDescription());
        assertEquals("available", ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer2.getId()).getDescription());

    }

    //Test to find computer index by UUID also test that you can view description and status
    //     after you've found the computer
    //Test that corresponds with US 1.03.01 (View_Computer)
    //***EDITED
    public void testgetComputerIndex() {
        //initial setup of test variables
        Computer testComputer1 = null;
        testComputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "COMPUTER 1", "available", testComputer1.getTime(),
                testComputer1.getThumbnail());
        Computer testComputer2 = null;
        testComputer2 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "COMPUTER 2", "available", testComputer2.getTime(),
                testComputer2.getThumbnail());

        //add computer objects to elastic search
        ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testComputer1);
        ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testComputer2);

        //checks if you can get the id
        assertEquals(testComputer1.getId(), ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getId());

        assertEquals("COMPUTER 2", ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer2.getId()).getDescription());
        assertEquals("available", ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer2.getId()).getStatus());
    }

    // Tests the ability to edit a computer if you know it's UUID
    // Test that corresponds to US 1.04.01 (Edit_Computer)
    //***EDITED
    public void testeditComputer() {
        //initial setup of test variables
        Computer testComputer1 = null;
        testComputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "COMPUTER 1", "available", testComputer1.getTime(),
                testComputer1.getThumbnail());

        //stores initial UUID
        UUID tempId = testComputer1.getId();

        //add computer object to elastic search
        ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testComputer1);

        //edited computer
        testComputer1 = new Computer(testComputer1.getId(), "cooljohn123", "Apple", "MacBook", 2014,
                "intel i7", 8, 500, "Mac", Float.parseFloat("56.2"), "COMPUTER 2", "available", testComputer1.getTime(),
                testComputer1.getThumbnail());

        //updates computer with information
        ca.ualberta.cs.technologic.ElasticSearchComputer.updateComputer(testComputer1);

        //make sure the userComputer maintains unique UUID/
        assertNotSame(tempId, ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getId());

    }

    //Test that corresponds with US 1.05.01 (Delete_Computer)
    //***EDITED
    public void testdeleteComputer() {
        //initial setup of test variables
        Computer testComputer1 = null;
        testComputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "COMPUTER 1", "available", testComputer1.getTime(),
                testComputer1.getThumbnail());
        Computer testComputer2 = null;
        testComputer2 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "COMPUTER 2", "available", testComputer2.getTime(),
                testComputer2.getThumbnail());

        //add computer objects to elastic search
        ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testComputer1);
        ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testComputer2);

        //delete the first computer in the array
        ca.ualberta.cs.technologic.ElasticSearchComputer.deleteComputer(String.valueOf(testComputer1.getId()));
        assertEquals(testComputer2, ca.ualberta.cs.technologic.ElasticSearchComputer.getComputers(testComputer1.getUsername()));

        //empty the array
        ca.ualberta.cs.technologic.ElasticSearchComputer.deleteComputer(String.valueOf(testComputer2.getId()));

        //try deleting item not in the array
        //make sure it throws and exception
        try {
            ca.ualberta.cs.technologic.ElasticSearchComputer.deleteComputer(String.valueOf(testComputer1.getId()));
            assertFalse(Boolean.TRUE);
        } catch (Exception e) {
            assertTrue(Boolean.TRUE);
        }

    }


    //test creation of account and unique usernames as well as some getters and setters for User
    //Test corresponds to US 03.01.01 (Unique_Profile)
    //***EDITED
    public void testsetUsername() {
        User testUser1 = new User("cooljohn123");

        //Make sure your username is unique (when you change your username)
        //If not unique it will throw IllegalArgumentException
        try {
            testUser1.setUsername("coolbob123");

        } catch (IllegalArgumentException e) {
            assertTrue(Boolean.TRUE);
        }

        testUser1.setName("John Buckingham");
        assertEquals("John Buckingham", testUser1.getName());
    }

    //03.02.01 As a user, I want to edit the contact information in my profile
    //***EDITED
    public void testEditUser() {
        User testUser = new User("connor");

        //Initialize user contact information
        testUser.setName("Connor");
        testUser.setAddress("Rexall Place");
        testUser.setEmail("allstar@oilers.ca");
        testUser.setPhone("7801231234");

        //Check to see if that is set to the user
        assertEquals(testUser.getName(), "Connor");
        assertEquals(testUser.getAddress(), "Rexall Place");
        assertEquals(testUser.getEmail(), "allstar@oilers.ca");
        assertEquals(testUser.getPhone(), "7801231234");

        //Edit the user information
        testUser.setName("Connor123");
        testUser.setAddress("Rexall Place123");
        testUser.setEmail("allstar123@oilers.ca");
        testUser.setPhone("7801234567");

        //Check to see if the user information is updated
        assertEquals(testUser.getName(), "Connor123");
        assertEquals(testUser.getAddress(), "Rexall Place123");
        assertEquals(testUser.getEmail(), "allstar123@oilers.ca");
        assertEquals(testUser.getPhone(), "7801234567");

    }

    // and 04.01.01 As a borrower, I want search results to show each thing not currently borrowed
    // with its description, owner username, and status
    //***EDIT
    public void testSearch() {
        //initial setup of test variables
        Computer testComputer1 = null;
        testComputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "COMPUTER 1", "available", testComputer1.getTime(),
                testComputer1.getThumbnail());
        String testWords = "Microsoft surface this is a cool computer";

        testComputer1.setStatus("bidded");
        ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testComputer1);

        //Tests that the search works
        if (testComputer1.getStatus() == "bidded") {
            assertTrue(ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getDescription().contains(testWords));
            assertTrue(ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getMake().contains(testWords));
            assertTrue(ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getModel().contains(testWords));
            assertEquals(ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getDescription(), "this is a cool computer");
            assertEquals(ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getUsername(), "cooljohn123");
            assertEquals(ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getStatus(), "bidded");

        } else {
            assertFalse(ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getDescription().contains(testWords));
            assertFalse(ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getMake().contains(testWords));
            assertFalse(ca.ualberta.cs.technologic.ElasticSearchComputer.getComputersById(testComputer1.getId()).getModel().contains(testWords));
        }


    }

}

