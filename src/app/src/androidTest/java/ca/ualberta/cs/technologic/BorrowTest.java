package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.UUID;

public class BorrowTest extends ActivityInstrumentationTestCase2 {

    public BorrowTest(Class activityClass) {
        super(activityClass);
    }

    /**
     * testViewBorrower tests if the Borrowing view is correct
     *  Corresponds to US 06.01.01
     */
    //***EDIT
    public void testViewBorrower() {
        // Initialize Users and Computers
        User testUser2 = new User("Ashley");

        Computer testcomputer1 = new Computer("Tom", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        Computer testcomputer2 = new Computer("Tom", "Mac", "Macbook", 2012, "intel i7", 8,
                500, "iOs", Float.parseFloat("34.2"), "urg apple");

        // User1 owns and lends out Computers to user2
        ArrayList<Borrow> testBorrow = new ArrayList<Borrow>();
        Borrow borrow1 = new Borrow(testcomputer1.getId(),testUser2.getUsername());
        Borrow borrow2 = new Borrow(testcomputer2.getId(),testUser2.getUsername());
        testBorrow.add(borrow1);
        testBorrow.add(borrow2);
        // user1 owns and lends out Computers

        ArrayList<UUID> compID = new ArrayList<UUID>();
        compID.add(testcomputer1.getId());
        compID.add(testcomputer2.getId());

        assertEquals("Should be same ArrayList", compID.get(0), testBorrow.get(0).getComputerID());
    }

    /**
     * testViewOwner tests if Lending out is correct
     * Corresponds to US 06.02.01
     */
    //***EDIT
    public void testViewOwner() {
        // Initialize Users and Computers
        User testUser2 = new User("Ashley");

        Computer testcomputer1 = new Computer("Tom", "Microsoft", "surface", 2014, "intel i7", 8,
                500, "windows", Float.parseFloat("34.2"), "this is a cool computer");
        Computer testcomputer2 = new Computer("Tom", "Mac", "Macbook", 2012, "intel i7", 8,
                500, "iOs", Float.parseFloat("34.2"), "urg apple");

        // user1 owns and lends out Computers
        //ashley is borrowing both computers
        ArrayList<Borrow> lentOut = new ArrayList<Borrow>();
        Borrow borrow1 = new Borrow(testcomputer1.getId(),testUser2.getUsername());
        Borrow borrow2 = new Borrow(testcomputer2.getId(),testUser2.getUsername());
        lentOut.add(borrow1);
        lentOut.add(borrow2);
        // user1 owns and lends out Computers

        ArrayList<UUID> compID = new ArrayList<UUID>();
        compID.add(testcomputer1.getId());
        compID.add(testcomputer2.getId());

        assertEquals("Should be same ArrayList", compID.get(0), lentOut.get(0).getBorrowID());
    }
}
