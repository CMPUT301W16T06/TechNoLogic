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

    //Test for viewing the bids on an owners computer
    //Test that corresponds with US 5.03.01 (Bid_Notification)
    public void testNotifications(){
        //initial setup of test variables
        User testUser = new User("cooljohn123","password");
        Computer testcomputer = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"this is a cool computer");
        testcomputer.setStatus("Bidded");


        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), 1.12);
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
        User testUser = new User("cooljohn123","password");
        Computer testcomputer = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"this is a cool computer");
        testcomputer.setStatus("Bidded");

        Computer testcomputer2 = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"this is a cool computer");
        testcomputer.setStatus("Bidded");

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), 1.12);
        testUser.addMyComputerBid(bid);
        Bid bid2 = new Bid(testcomputer2.getId(), 2.34);
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
        User testUser = new User("cooljohn123","password");
        Computer testcomputer = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"this is a cool computer");
        testcomputer.setStatus("Bidded");

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), 1.12);
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
        User testUser = new User("cooljohn123","password");
        Computer testcomputer = new Computer("Mircosoft","surface",2014,"intel i7", 8,
                500,"windows",Float.parseFloat("34.2"),"this is a cool computer");
        testcomputer.setStatus("Bidded");

        //add into list that holds bids on a owners computers
        Bid bid = new Bid(testcomputer.getId(), 1.12);
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


}
