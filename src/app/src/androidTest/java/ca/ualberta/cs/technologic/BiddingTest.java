package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.technologic.Activities.Bids;

/**
 * Created by Jessica on 2016-02-11.
 */
public class BiddingTest extends ActivityInstrumentationTestCase2 {

    public BiddingTest() {
        super(BiddingTest.class);
    }

    //05.01.01 As a borrower, I want to bid for an available thing, with a monetary rate
    // (in dollars per hour)
    //***EDIT
    public void testPlaceBid () {
        //initial setup of test variables
        User testUser = new User("cooljohn123");
<<<<<<< HEAD
        Computer testcomputer1 = new Computer("bob", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");

        //Have the user place a bid on the computer
        Bid bid = new Bid(testcomputer1.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testcomputer1.getUsername());
        testcomputer1.setStatus("Bidded");

        //assert that the bid is 1.12 and that the bid was placed by the user and the
        //computer is owned by bob
        assertEquals(bid.getComputerID(),testcomputer1.getId());
        assertEquals(bid.getPrice(),Float.parseFloat("1.12"));
        assertEquals(bid.getOwner(),testcomputer1.getUsername());
=======
        Computer testcomputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        testcomputer.setStatus("Bidded");

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("1.12"),"tom","bob");
        testUser.addMyComputerBid(bid);

        //check that it has the new bid
        ArrayList<Bid> newBids = testUser.getNewBids();
        assertEquals(newBids.size(), 1);
        assertEquals(newBids.get(0).getComputerID(), testcomputer.getId());
>>>>>>> origin/master

    }

    //05.02.01 | As a borrower, I want to view a list of things I have bidded on that are pending,
    // each thing with its description, owner username, and my bid
    //***EDIT
    public void testMyBids () {
        //initial setup of test variables
<<<<<<< HEAD
        User testUser = new User("cooljohn123");
        Computer testcomputer = new Computer("george", "Microsoft", "surface", 2014, "intel i7", 8,
=======
        User testUser1 = new User("cooljohn123");
        User testUser2 = new User("thisssucks");
        Computer testcomputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
>>>>>>> origin/master
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        Computer testcomputer2 = new Computer("bob", "Mac", "Macbook", 2012, "intel i7", 8,
                500, "iOs", Float.parseFloat("34.2"), "urg apple");
        ArrayList<Bid> placedBids = new ArrayList<Bid>();

        //the user places a bid on two comupters for 1.12 and 2.23
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testcomputer.getUsername());
        testcomputer.setStatus("Bidded");
<<<<<<< HEAD
        placedBids.add(bid);
        Bid bid2 = new Bid(testcomputer2.getId(), Float.parseFloat("2.23"),testUser.getUsername(),
                testcomputer2.getUsername());
        testcomputer2.setStatus("Bidded");
        placedBids.add(bid2);

        assertEquals(placedBids.size(),2);
        //bids placed by same owner
        for (Bid item : placedBids){
            assertEquals(item.getUsername(),"cooljohn123");
        }
        assertEquals(placedBids.get(0).getPrice(),Float.parseFloat("1.12"));
        assertEquals(placedBids.get(1).getPrice(),Float.parseFloat("2.23"));
        assertEquals(placedBids.get(0).getComputerID(),testcomputer.getId());
        assertEquals(placedBids.get(0),bid);
=======
        testUser2.addComputer(testcomputer);

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("1.12"),"tom","bob");
        testUser1.addMyComputerBid(bid);

        //Gets list of all the computers I bidded on
       // ArrayList<Bid> test = testUser.getMyComputerBids();

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
        ArrayList<Bid> computerBids = testUser1.getComputerBids(testcomputer.getId());



>>>>>>> origin/master
    }

    //Test for viewing the bids on an owners computer
    //Test that corresponds with US 5.03.01 (Bid_Notification)
    //***EDIT
    public void testNotifications(){
        //initial setup of test variables
<<<<<<< HEAD
        User testUser = new User("george");
        Computer testComputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
=======
        User testUser = new User("cooljohn123");
        Computer testcomputer = null;
        testcomputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
>>>>>>> origin/master
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        Computer testComputer2 = new Computer("bob", "Mac", "Macbook", 2012, "intel i7", 8,
                500, "iOs", Float.parseFloat("34.2"), "urg apple");
        Computer testComputer3 = new Computer("cooljohn123", "Linux", "Linux", 2012, "intel i7", 8,
                500, "linux", Float.parseFloat("34.2"), "fancy linux");
        //NewBidsAlert for cooljohn123
        ArrayList<Bid> newBidsAlert = new ArrayList<Bid>();
        //List of all computers in the system
        ArrayList<Computer> allComputers = new ArrayList<Computer>();
        allComputers.add(testComputer);
        allComputers.add(testComputer2);
        allComputers.add(testComputer3);

        //Have the user place bids on computers
        Bid bid = new Bid(testComputer.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testComputer.getUsername());
        testComputer.setStatus("Bidded");
        Bid bid2 = new Bid(testComputer2.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testComputer2.getUsername());
        testComputer2.setStatus("Bidded");
        //All bids in the system
        ArrayList<Bid> allBids = new ArrayList<Bid>();

        //Add all the bids for cooljohn123 items
        newBidsAlert.add(bid);

        assertEquals(newBidsAlert.size(), 1);
        assertEquals(newBidsAlert.get(0).getComputerID(),testComputer.getId());
        //make sure the computer isnt testcomputer3 which hasn't been bid on
        assertNotSame(newBidsAlert.get(0).getComputerID(),testComputer3.getId());

        //when he has been notified clear the list
        newBidsAlert.clear();
        assertEquals(newBidsAlert.size(), 0);
    }

    //Test for viewing the bids on an owners computer
    //Test that corresponds with US 5.04.01 (View_My_Computer_Bids_List)
    //***EDIT
    public void testViewMyComputerBids(){
        //initial setup
        User testUser = new User("george");
        Computer testComputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        Computer testComputer2 = new Computer("cooljohn123", "Mac", "Macbook", 2012, "intel i7", 8,
                500, "iOs", Float.parseFloat("34.2"), "urg apple");
        ArrayList<Computer> testArray = new ArrayList<Computer>();

        //Have the user place bids on computers
        Bid bid = new Bid(testComputer.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testComputer.getUsername());
        testComputer.setStatus("Bidded");
        Bid bid2 = new Bid(testComputer2.getId(), Float.parseFloat("2.23"),testUser.getUsername(),
                testComputer2.getUsername());
        testComputer2.setStatus("Bidded");
        //All bids in the system
        ArrayList<Bid> johnBids = new ArrayList<Bid>();
        johnBids.add(bid);
        johnBids.add(bid2);

        //check that the list of bids contains a bid
        assertEquals(johnBids.size(), 2);

        //check that the id matches the the computer that was added
        //and that the computer belongs to cooljohn123
        assertEquals(johnBids.get(0).getComputerID(), testComputer.getId());
        assertEquals(johnBids.get(1).getComputerID(), testComputer2.getId());
        assertEquals(johnBids.get(0).getOwner(), "cooljohn123");
        assertEquals(johnBids.get(1).getOwner(), "cooljohn123");
    }

    //Test for view of the bids on a specific computer
    //Test that corresponds with US 5.05.01 (View_Computer_Bids)
    //***EDIT
    public void testViewComputerBids(){
        //initial setup of test variables
        //initial setup
        User testUser = new User("george");
        User testUser2 = new User("bob");
        Computer testComputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testComputer.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testComputer.getUsername());
        testComputer.setStatus("Bidded");
        Bid bid2 = new Bid(testComputer.getId(), Float.parseFloat("1.12"),testUser2.getUsername(),
                testComputer.getUsername());
        //All bids for testComputer1
        ArrayList<Bid> allBids = new ArrayList<Bid>();
        allBids.add(bid);
        allBids.add(bid2);

        assertEquals(allBids.size(),2);

        //all the same computer
        assertEquals(allBids.get(0).getComputerID(),testComputer.getId());
        assertEquals(allBids.get(1).getComputerID(),testComputer.getId());

        assertEquals(allBids.get(0).getOwner(),"cooljohn123");
    }

    //testDeclinebid tests if a declined bid still exists
    //Corresponds to US 05.07.01
    //***EDIT
<<<<<<< HEAD
    public void testDeclineBid() {
        //initial setup
        User testUser = new User("george");
        User testUser2 = new User("bob");
        User testUser3 = new User("DONALD TRUMP, MASTER OF THE UNIVERSE");
        Computer testComputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");

        //Place three bids on the computer
        Bid bid = new Bid(testComputer.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testComputer.getUsername());
        testComputer.setStatus("Bidded");
        Bid bid2 = new Bid(testComputer.getId(), Float.parseFloat("1.12"),testUser2.getUsername(),
                testComputer.getUsername());
        Bid bid3 = new Bid(testComputer.getId(), Float.parseFloat("3.33"),testUser3.getUsername(),
                testComputer.getUsername());
        ArrayList<Bid> allBids = new ArrayList<Bid>();
        allBids.add(bid); allBids.add(bid2); allBids.add(bid3);

        // user1 declines bid1, make sure the computer is still bidded
        allBids.remove(0);
        assertEquals(allBids.size(), 2);
        assertEquals(testComputer.getStatus(), "bidded");

        // user excepts bid 3 make sure computer is borrowed
        allBids.remove(0);
        testComputer.setStatus("borrowed");
        assertEquals(allBids.size(), 1);
        assertEquals(testComputer.getStatus(),"borrowed");
        //make sure the accepted bid was bid 3
        assertEquals(allBids.get(0),bid3);
=======
    public void acceptBid(){
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = null;
        testcomputer1 = new Computer(testcomputer1.getId(), "cooljohn123", "Microsoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"this is a cool computer", "bidded");

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer1.getId(), Float.parseFloat("1.12"),"tom","bob");
        testUser.addMyComputerBid(bid);

        //get list of all bids for a specific computer
        ArrayList<Bid> computerBids = testUser.getComputerBids(testcomputer1.getId());

        //select a bid to accept
        //change the computer status to borrowed
        Computer computer = testUser.getComputers().get(testUser.getComputerIndex(testcomputer1.getId()));
        computer.setStatus("borrowed");

        //check that the status has been changed
        assertEquals(computer.getStatus(), "borrowed");

        //Remove bid from the MyComputerBids List
        testUser.removeComputerBids(computer.getId());

        //get list of all bids on the computer that was accpeted
        //the list should be zero
        ArrayList<Bid> computerBids2 = testUser.getComputerBids(testcomputer1.getId());
        assertEquals(computerBids2.size(), 0);

        //the computer must then be added to the LentOut list
        testUser.addLentOut(testcomputer1.getId());

        //check that the Lent Out List has the computer
        ArrayList<UUID> lentOutCompters = testUser.getLentOut();
        assertEquals(lentOutCompters.size(), 1);
        assertEquals(lentOutCompters.get(0), testcomputer1.getId());

    }

    /**
     * testDeclinebid tests if a declined bid still exists
     *  Corresponds to US 05.07.01
     */
    //***EDIT
    public void testDeclinebid() {
        // Initialize Users and Computers
        User testuser1 = new User("Tom");

        Computer testcomputer1 = null;
        testcomputer1 = new Computer(testcomputer1.getId(), "cooljohn123", "Microsoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"this is a cool computer", "available");

        // Place three bids on comp1
        Bid bid1 = new Bid(testcomputer1.getId(), Float.parseFloat("19.3"),"tom","bob");
        Bid bid2 = new Bid(testcomputer1.getId(), Float.parseFloat("24.3"),"tom","bob");
        Bid bid3 = new Bid(testcomputer1.getId(), Float.parseFloat("22.7"),"tom","bob");
        testuser1.addMyComputerBid(bid1);
        testuser1.addMyComputerBid(bid2);
        testuser1.addMyComputerBid(bid3);

        // user1 declines bid1
        testuser1.declineComputerBid(bid1);

        // Expecting bid2 and bid3
        ArrayList<Bid> expectedBids = new ArrayList<Bid>();
        expectedBids.add(bid2);
        expectedBids.add(bid3);

        // Expecting empty because comp1 is declined, not lent out
        ArrayList<UUID> expectedUUID = new ArrayList<UUID>();

        assertEquals("Expected same bid array", expectedBids, testuser1.getComputerBids(testcomputer1.getId()));
        assertEquals("Expected expect empty", expectedUUID, testuser1.getLentOut());
>>>>>>> origin/master
    }
}
