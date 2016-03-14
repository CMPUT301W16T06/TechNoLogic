
package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import java.util.UUID;

/**
 * Created by Jordan on 08/02/2016.
 *
 * Tests for the User class, covers tests for use cases: Add_Computer, Edit_Computer
 *          Delete_Computer and View_Computer
 */
public class UserTest extends ActivityInstrumentationTestCase2 {

    public UserTest() {
        super(ElasticSearchComputer.class);
    }

    //Tests the adding of a computer item to the user's computer list
    //Test that corresponds with US 1.01.01 (Add_Computer)
    // ***EDITED
    public void testaddComputer() {
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft", "surface",
                2014, "intel i7", 8, 500, "windows", Float.parseFloat("34.2"),
                "this is a cool computer", "available");


        //setting status to bidded
        testcomputer1.setStatus("bidded");
        ElasticSearchComputer.addComputer(testcomputer1);

        //checks if the computer is in the elastic search
        assertEquals(testcomputer1, ElasticSearchComputer.getComputersById(testcomputer1.getId()));

        //try adding the same computer twice, and make sure it raises error
        try {
            ca.ualberta.cs.technologic.ElasticSearchComputer.addComputer(testcomputer1);
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
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft", "surface",
                2014, "intel i7", 8, 500, "windows", Float.parseFloat("34.2"), "COMPUTER 1", "available");
        Computer testcomputer2 = new Computer(UUID.randomUUID(), "cooljohn123", "Apple", "MacBook",
                2014, "intel i7", 8, 500, "Mac", Float.parseFloat("56.2"), "COMPUTER 2", "available");

        //add computer objects to elastic search
        ElasticSearchComputer.addComputer(testcomputer1);
        ElasticSearchComputer.addComputer(testcomputer2);

        //checks status
        assertEquals("available", ElasticSearchComputer.getComputersById(testcomputer1.getId()).getStatus());
        assertEquals("available", ElasticSearchComputer.getComputersById(testcomputer2.getId()).getStatus());

        //checks description
        assertEquals("available", ElasticSearchComputer.getComputersById(testcomputer1.getId()).getDescription());
        assertEquals("available", ElasticSearchComputer.getComputersById(testcomputer2.getId()).getDescription());

    }

    //Test to find computer index by UUID also test that you can view description and status
    //     after you've found the computer
    //Test that corresponds with US 1.03.01 (View_Computer)
    //***EDITED
    public void testgetComputerIndex() {
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft", "surface",
                2014, "intel i7", 8, 500, "windows", Float.parseFloat("34.2"), "COMPUTER 1", "available");
        Computer testcomputer2 = new Computer(UUID.randomUUID(), "cooljohn123", "Apple", "MacBook", 2014,
                "intel i7", 8, 500, "Mac", Float.parseFloat("56.2"), "COMPUTER 2", "available");
        Computer testcomputer3 = new Computer(UUID.randomUUID(), "cooljohn123", "Linux", "Computer",
                2014, "intel i7", 8, 500, "Linux", Float.parseFloat("51.2"), "COMPUTER 3", "available");

        //add computer objects to elastic search
        ElasticSearchComputer.addComputer(testcomputer1);
        ElasticSearchComputer.addComputer(testcomputer2);

        //checks if you can get the id
        assertEquals(testcomputer1.getId(), ElasticSearchComputer.getComputersById(testcomputer1.getId()).getId());

        assertEquals("COMPUTER 2", ElasticSearchComputer.getComputersById(testcomputer2.getId()).getDescription());
        assertEquals("available", ElasticSearchComputer.getComputersById(testcomputer2.getId()).getStatus());
    }

    // Tests the ability to edit a computer if you know it's UUID
    // Test that corresponds to US 1.04.01 (Edit_Computer)
    //***EDITED
    public void testeditComputer() {
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft", "surface",
                2014, "intel i7", 8, 500, "windows", Float.parseFloat("34.2"), "COMPUTER 1", "available");

        //stores initial UUID
        UUID tempId = testcomputer1.getId();

        //add computer object to elastic search
        ElasticSearchComputer.addComputer(testcomputer1);

        //edited computer
        testcomputer1 = new Computer(testcomputer1.getId(), "cooljohn123", "Apple", "MacBook", 2014,
                "intel i7", 8, 500, "Mac", Float.parseFloat("56.2"), "COMPUTER 2", "available");

        //updates computer with information
        ElasticSearchComputer.updateComputer(testcomputer1);

        //make sure the userComputer maintains unique UUID/
        assertNotSame(tempId, ElasticSearchComputer.getComputersById(testcomputer1.getId()).getId());

    }

    //Test that corresponds with US 1.05.01 (Delete_Computer)
    //***EDITED
    public void testdeleteComputer() {
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft", "surface",
                2014, "intel i7", 8, 500, "windows", Float.parseFloat("34.2"),
                "COMPUTER 1", "available");
        Computer testcomputer2 = new Computer(UUID.randomUUID(), "cooljohn123", "Apple", "MacBook",
                2014, "intel i7", 8, 500, "Mac", Float.parseFloat("56.2"), "COMPUTER 2", "available");

        //add computer objects to elastic search
        ElasticSearchComputer.addComputer(testcomputer1);
        ElasticSearchComputer.addComputer(testcomputer2);

        //delete the first computer in the array
        ElasticSearchComputer.deleteComputer(String.valueOf(testcomputer1.getId()));
        assertEquals(testcomputer2, ElasticSearchComputer.getComputers(testcomputer1.getUsername()));

        //empty the array
        ElasticSearchComputer.deleteComputer(String.valueOf(testcomputer2.getId()));

        //try deleting item not in the array
        //make sure it throws and exception
        try {
            ElasticSearchComputer.deleteComputer(String.valueOf(testcomputer1.getId()));
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
        User testUser2 = new User("coolbob123");

        //Make sure your username is unique (when you change your username)
        //If not unique it will throw IllegalArgumentException
        try {
            testUser1.setUsername("coolbob123");

        } catch (IllegalArgumentException e) {
            assertTrue(Boolean.TRUE);
        }

        //Make sure your username is unique when you create an account
        try {
            User testUser3 = new User("coolbob123");
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
    //03.03.01 As a user, I want to, when a username is presented for a thing, retrieve and show
    // its contact information
    //***EDIT

//    public void testViewRentersInfo() {
//        //initial setup of test variables
//        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
//        User testUser = new User("connor");
//        testUser.setName("Connor");
//        testUser.setAddress("Rexall place");
//        testUser.setEmail("allstar@oilers.ca");
//        testUser.setPhone("7801231234");
//        Computer testcomputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
//                "surface", 2014, "intel i7", 8, 500, "windows", Float.parseFloat("34.2"),
//                "COMPUTER 1", "available");
//
//        testcomputer1.setStatus("bidded");
//        ElasticSearchComputer.addComputer(testcomputer1);
//
//        //Simulate a click on an object where that user owns it
//        assertTrue(ElasticSearchComputer.getComputers(testcomputer1.getUsername()).contains(testcomputer1.getId()));
//
//
//        assertEquals(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getUserName(), "Connor");
//        assertEquals(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getAddress(), "Rexall Place");
//        assertEquals(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getEmail(), "allstar@oilers.ca");
//        assertEquals(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getPhone(), "7801231234");
//    }

    //04.01.01 As a borrower, I want to specify a set of keywords, and search for all things not
    // currently borrowed whose description contains all the keywords

    // and 04.01.01 As a borrower, I want search results to show each thing not currently borrowed
    // with its description, owner username, and status
    //***EDIT
    public void testSearch() {
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser1 = new User("cooljohn123");
        User testUser2 = new User("thissucks");
        Computer testcomputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft", "surface",
                2014, "intel i7", 8, 500, "windows", Float.parseFloat("34.2"),
                "this is a cool computer", "available");
        String testWords = "Microsoft surface this is a cool computer";

        testcomputer1.setStatus("bidded");
        ElasticSearchComputer.addComputer(testcomputer1);

        //Tests that the search works
        if (testcomputer1.getStatus() == "bidded") {
            assertTrue(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getDescription().contains(testWords));
            assertTrue(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getMake().contains(testWords));
            assertTrue(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getModel().contains(testWords));
            assertEquals(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getDescription(), "this is a cool computer");
            assertEquals(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getUsername(), "cooljohn123");
            assertEquals(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getStatus(), "bidded");

        } else {
            assertFalse(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getDescription().contains(testWords));
            assertFalse(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getMake().contains(testWords));
            assertFalse(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getModel().contains(testWords));
        }


    }

}

