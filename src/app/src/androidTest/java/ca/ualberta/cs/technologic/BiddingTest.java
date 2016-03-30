package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by Jessica on 2016-02-11.
 */
public class BiddingTest extends ActivityInstrumentationTestCase2 {

    public BiddingTest() {
        super(BiddingTest.class);
    }

    //05.01.01 As a borrower, I want to bid for an available thing, with a monetary rate
    // (in dollars per hour)
    //***EDITED
    public void testPlaceBid () {
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = new Computer("bob", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer",null);

        //Have the user place a bid on the computer
        Bid bid = new Bid(testcomputer1.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testcomputer1.getUsername());
        testcomputer1.setStatus("Bidded");

        //assert that the bid is 1.12 and that the bid was placed by the user and the
        //computer is owned by bob
        assertEquals(bid.getComputerID(),testcomputer1.getId());
        assertEquals(bid.getPrice(),Float.parseFloat("1.12"));
        assertEquals(bid.getOwner(),testcomputer1.getUsername());

    }

    //05.02.01 | As a borrower, I want to view a list of things I have bidded on that are pending,
    // each thing with its description, owner username, and my bid
    //***EDITED
    public void testMyBids () {
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        Computer testcomputer = new Computer("george", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer",null);
        Computer testcomputer2 = new Computer("bob", "Mac", "Macbook", 2012, "intel i7", 8,
                500, "iOs", Float.parseFloat("34.2"), "urg apple",null);
        ArrayList<Bid> placedBids = new ArrayList<Bid>();

        //the user places a bid on two comupters for 1.12 and 2.23
        Bid bid = new Bid(testcomputer.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testcomputer.getUsername());
        testcomputer.setStatus("Bidded");
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
    }

    //Test for viewing the bids on an owners computer
    //Test that corresponds with US 5.03.01 (Bid_Notification)
    //***EDITED
    public void testNotifications(){
        //initial setup of test variables
        User testUser = new User("george");
        Computer testComputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer",null);
        Computer testComputer2 = new Computer("bob", "Mac", "Macbook", 2012, "intel i7", 8,
                500, "iOs", Float.parseFloat("34.2"), "urg apple",null);
        Computer testComputer3 = new Computer("cooljohn123", "Linux", "Linux", 2012, "intel i7", 8,
                500, "linux", Float.parseFloat("34.2"), "fancy linux",null);
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
    //***EDITED
    public void testViewMyComputerBids(){
        //initial setup
        User testUser = new User("george");
        Computer testComputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer",null);
        Computer testComputer2 = new Computer("cooljohn123", "Mac", "Macbook", 2012, "intel i7", 8,
                500, "iOs", Float.parseFloat("34.2"), "urg apple",null);
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
    //***EDITED
    public void testViewComputerBids(){
        //initial setup of test variables
        //initial setup
        User testUser = new User("george");
        User testUser2 = new User("bob");
        Computer testComputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer",null);

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
    //***EDITED
    public void testDeclineBid() {
        //initial setup
        User testUser = new User("george");
        User testUser2 = new User("bob");
        User testUser3 = new User("DONALD TRUMP, MASTER OF THE UNIVERSE");
        Computer testComputer = new Computer("cooljohn123", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer",null);

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
    }
}
