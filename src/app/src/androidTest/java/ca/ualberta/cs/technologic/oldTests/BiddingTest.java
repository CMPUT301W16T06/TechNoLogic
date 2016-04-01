package ca.ualberta.cs.technologic.oldTests;

import android.test.ActivityInstrumentationTestCase2;
import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.technologic.Bid;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.User;


public class BiddingTest extends ActivityInstrumentationTestCase2 {
    public BiddingTest () {
        super(BiddingTest.class);
    }

    //05.01.01 As a borrower, I want to bid for an available thing, with a monetary rate
    // (in dollars per hour)
    //***EDITED
    public void testPlaceBid () {
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        Computer testComputer = null;
        testComputer = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available", testComputer.getTime(),
                testComputer.getThumbnail());

        //Have the user place a bid on the computer
        Bid bid = new Bid(testComputer.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testComputer.getUsername());
        testComputer.setStatus("Bidded");

        //assert that the bid is 1.12 and that the bid was placed by the user and the
        //computer is owned by bob
        assertEquals(bid.getComputerID(),testComputer.getId());
        assertEquals(bid.getPrice(),Float.parseFloat("1.12"));
        assertEquals(bid.getOwner(),testComputer.getUsername());

    }

    //05.02.01 | As a borrower, I want to view a list of things I have bidded on that are pending,
    // each thing with its description, owner username, and my bid
    //***EDITED
    public void testMyBids () {
        //initial setup of test variables
        User testUser = new User("cooljohn123");
        Computer testComputer = null;
        testComputer = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available", testComputer.getTime(),
                testComputer.getThumbnail());
        Computer testComputer2 = null;
        testComputer = new Computer(UUID.randomUUID(), "bob", "Mac",
                "Macbook",2012,"intel i7", 8, 500,"iOs",Float.parseFloat("34.2"),
                "urgh apple", "available", testComputer2.getTime(),
                testComputer2.getThumbnail());
        ArrayList<Bid> placedBids = new ArrayList<Bid>();

        //the user places a bid on two comupters for 1.12 and 2.23
        Bid bid = new Bid(testComputer.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testComputer.getUsername());
        testComputer.setStatus("Bidded");
        placedBids.add(bid);
        Bid bid2 = new Bid(testComputer2.getId(), Float.parseFloat("2.23"),testUser.getUsername(),
                testComputer2.getUsername());
        testComputer2.setStatus("Bidded");
        placedBids.add(bid2);

        assertEquals(placedBids.size(),2);
        //bids placed by same owner
        for (Bid item : placedBids){
            assertEquals(item.getUsername(),"cooljohn123");
        }
        assertEquals(placedBids.get(0).getPrice(),Float.parseFloat("1.12"));
        assertEquals(placedBids.get(1).getPrice(),Float.parseFloat("2.23"));
        assertEquals(placedBids.get(0).getComputerID(),testComputer.getId());
        assertEquals(placedBids.get(0),bid);
    }

    //Test for viewing the bids on an owners computer
    //Test that corresponds with US 5.03.01 (Bid_Notification)
    //***EDIT
    public void testNotifications(){
        //initial setup of test variables
        User testUser = new User("george");
        Computer testComputer = null;
        testComputer = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available", testComputer.getTime(),
                testComputer.getThumbnail());
        Computer testComputer2 = null;
        testComputer = new Computer(UUID.randomUUID(), "bob", "Mac",
                "Macbook",2012,"intel i7", 8, 500,"iOs",Float.parseFloat("34.2"),
                "urgh apple", "available", testComputer2.getTime(),
                testComputer2.getThumbnail());
        Computer testComputer3 = null;
        testComputer = new Computer(UUID.randomUUID(), "cooljohn123", "Linux",
                "Linux",2012,"intel i7", 8, 500,"linux",Float.parseFloat("34.2"),
                "fancy linux", "available", testComputer3.getTime(),
                testComputer3.getThumbnail());
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
        testComputer2.setStatus("Bidded");

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
        Computer testComputer1 = null;
        testComputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available", testComputer1.getTime(),
                testComputer1.getThumbnail());
        Computer testComputer2 = null;
        testComputer2 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available", testComputer2.getTime(),
                testComputer2.getThumbnail());

        //Have the user place bids on computers
        Bid bid = new Bid(testComputer1.getId(), Float.parseFloat("1.12"),testUser.getUsername(),
                testComputer1.getUsername());
        testComputer1.setStatus("Bidded");
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
        assertEquals(johnBids.get(0).getComputerID(), testComputer1.getId());
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
        Computer testComputer = null;
        testComputer = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available", testComputer.getTime(),
                testComputer.getThumbnail());

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
        Computer testComputer = null;
        testComputer = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available", testComputer.getTime(),
                testComputer.getThumbnail());

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
