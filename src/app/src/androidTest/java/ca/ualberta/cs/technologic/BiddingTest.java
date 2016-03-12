package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jessica on 2016-02-11.
 */
public class BiddingTest extends ActivityInstrumentationTestCase2 {

    public BiddingTest(Class activityClass) {
        super(activityClass);
    }

    //05.01.01 As a borrower, I want to bid for an available thing, with a monetary rate
    // (in dollars per hour)

    public void testPlaceBid () {
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        Computer testcomputer = new Computer("cooljohn123", "Mircosoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        testcomputer.setStatus("Bidded");

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("1.12"),"tom","bob");
        testUser.addMyComputerBid(bid);

        //check that it has the new bid
        ArrayList<Bid> newBids = testUser.getNewBids();
        assertEquals(newBids.size(), 1);
        assertEquals(newBids.get(0).getComputerID(), testcomputer.getId());

    }


    //05.02.01 | As a borrower, I want to view a list of things I have bidded on that are pending,
    // each thing with its description, owner username, and my bid
    public void testMyBids () {
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        User testUser2 = new User("thisssucks");
        Computer testcomputer = new Computer("cooljohn123", "Mircosoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");

        testcomputer.setStatus("Bidded");
        testUser2.addComputer(testcomputer);

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("1.12"),"tom","bob");
        testUser.addMyComputerBid(bid);

        //Gets list of all the computers I bidded on
        ArrayList<Bid> test = testUser.getMyComputerBids();

        //Makes sure there is only one bid
        assertEquals(test.size(), 1);

        //get's the only bid in the list
        Bid bidTest = test.get(0);

        //Tests to ensure it is the same bid
        assertEquals(bidTest.getComputerID(), testcomputer.getId());

        //Makes sure the correct bid price is shown
        assertEquals(bidTest.getPrice(), 1.12);

        //Makes sure the owner username is correct
        assertEquals(testUser2.getComputerIndex(bidTest.getComputerID()), testcomputer);

        //get list of all bids for a specific computer
        ArrayList<Bid> computerBids = testUser.getComputerBids(testcomputer.getId());



    }

    //Test for viewing the bids on an owners computer
    //Test that corresponds with US 5.03.01 (Bid_Notification)
    public void testNotifications(){
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        Computer testcomputer = new Computer("cooljohn123", "Mircosoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        testcomputer.setStatus("Bidded");


        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("1.12"),"tom","bob");
        testUser.addMyComputerBid(bid);

        //add to newBid list
        testUser.addNewBid(bid);

        //get the list of NewBids to notify the owner
        //check that it has the new bid
        ArrayList<Bid> newBids = testUser.getNewBids();
        assertEquals(newBids.size(), 1);
        assertEquals(newBids.get(0).getComputerID(), testcomputer.getId());

        //after the owner is notified the list should be clears
        testUser.clearNewBids();
        assertEquals(newBids.size(), 0);
    }

    //Test for viewing the bids on an owners computer
    //Test that corresponds with US 5.04.01 (View_My_Computer_Bids_List)
    public void testViewMyComputerBids(){
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        Computer testcomputer = new Computer("cooljohn123", "Mircosoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        testcomputer.setStatus("Bidded");

        Computer testcomputer2 = new Computer("cooljohn123", "Mircosoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        testcomputer.setStatus("Bidded");

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("1.12"),"tom","bob");
        testUser.addMyComputerBid(bid);
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("2.34"),"tom","bob");
        testUser.addMyComputerBid(bid2);

        //get list of computers with bids on them that will be displayed
        ArrayList<Bid> computerBids = testUser.getMyComputerBids();

        //check that the list of bids contains a bid
        assertEquals(computerBids.size(), 2);

        //check that the id matches the the computer that was added
        assertEquals(computerBids.get(0).getComputerID(), testcomputer.getId());
    }

    //Test for view of the bids on a specific computer
    //Test that corresponds with US 5.05.01 (View_Computer_Bids)
    public void testViewComputerBids(){
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        Computer testcomputer = new Computer("cooljohn123", "Mircosoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        testcomputer.setStatus("Bidded");

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("1.12"),"tom","bob");
        testUser.addMyComputerBid(bid);

        //get list of all bids for a specific computer
        ArrayList<Bid> computerBids = testUser.getComputerBids(testcomputer.getId());
        Computer computer = testUser.getComputers().get(testUser.getComputerIndex(testcomputer.getId()));

        //check that the id matches the the computer that was added
        assertEquals(computer.getId(), testcomputer.getId());

    }

    //Test for view of the bids on a specific computer
    //Test that corresponds with US 5.05.01 (View_Computer_Bids)
    public void acceptBid(){
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        Computer testcomputer = new Computer("cooljohn123", "Mircosoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        testcomputer.setStatus("Bidded");

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("1.12"),"tom","bob");
        testUser.addMyComputerBid(bid);

        //get list of all bids for a specific computer
        ArrayList<Bid> computerBids = testUser.getComputerBids(testcomputer.getId());

        //select a bid to accept
        //change the computer status to borrowed
        Computer computer = testUser.getComputers().get(testUser.getComputerIndex(testcomputer.getId()));
        computer.setStatus("borrowed");

        //check that the status has been changed
        assertEquals(computer.getStatus(), "borrowed");

        //Remove bid from the MyComputerBids List
        testUser.removeComputerBids(computer.getId());

        //get list of all bids on the computer that was accpeted
        //the list should be zero
        ArrayList<Bid> computerBids2 = testUser.getComputerBids(testcomputer.getId());
        assertEquals(computerBids2.size(), 0);

        //the computer must then be added to the LentOut list
        testUser.addLentOut(testcomputer.getId());

        //check that the Lent Out List has the computer
        ArrayList<UUID> lentOutCompters = testUser.getLentOut();
        assertEquals(lentOutCompters.size(), 1);
        assertEquals(lentOutCompters.get(0), testcomputer.getId());

    }

    /**
     * testDeclinebid tests if a declined bid still exists
     *  Corresponds to US 05.07.01
     */

    public void testDeclinebid() {
        // Initialize Users and Computers
        User user1 = new User("Tom");

        Computer comp1 = new Computer("cooljohn123", "Mircosoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");

        // Place three bids on comp1
        Bid bid1 = new Bid(comp1.getId(), Float.parseFloat("19.3"),"tom","bob");
        Bid bid2 = new Bid(testcomputer.getId(), Float.parseFloat("24.3"),"tom","bob");
        Bid bid3 = new Bid(testcomputer.getId(), Float.parseFloat("22.7"),"tom","bob");
        user1.addMyComputerBid(bid1);
        user1.addMyComputerBid(bid2);
        user1.addMyComputerBid(bid3);

        // user1 declines bid1
        user1.declineComputerBid(bid1);

        // Expecting bid2 and bid3
        ArrayList<Bid> expectedBids = new ArrayList<Bid>();
        expectedBids.add(bid2);
        expectedBids.add(bid3);

        // Expecting empty because comp1 is declined, not lent out
        ArrayList<UUID> expectedUUID = new ArrayList<UUID>();

        assertEquals("Expected same bid array", expectedBids, user1.getComputerBids(comp1.getId()));
        assertEquals("Expected expect empty", expectedUUID, user1.getLentOut());
    }

}
