package ca.ualberta.cs.technologic.oldTests;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.technologic.Borrow;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.User;

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

        Computer testComputer1 = null;
        testComputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "COMPUTER 1", "available", testComputer1.getTime(),
                testComputer1.getThumbnail());
        Computer testComputer2 = null;
        testComputer2 = new Computer(UUID.randomUUID(), "bob", "Apple",
                "iMac",2014,"intel i7", 8, 500,"iOS",Float.parseFloat("34.2"),
                "COMPUTER 2", "available", testComputer2.getTime(),
                testComputer2.getThumbnail());

        // User1 owns and lends out Computers to user2
        ArrayList<Borrow> testBorrow = new ArrayList<Borrow>();
        Borrow borrow1 = new Borrow(testComputer1.getId(),testUser2.getUsername());
        Borrow borrow2 = new Borrow(testComputer2.getId(),testUser2.getUsername());
        testBorrow.add(borrow1);
        testBorrow.add(borrow2);
        // user1 owns and lends out Computers

        ArrayList<UUID> compID = new ArrayList<UUID>();
        compID.add(testComputer1.getId());
        compID.add(testComputer2.getId());

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

        Computer testComputer1 = null;
        testComputer1 = new Computer(UUID.randomUUID(), "cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "COMPUTER 1", "available", testComputer1.getTime(),
                testComputer1.getThumbnail());
        Computer testComputer2 = null;
        testComputer2 = new Computer(UUID.randomUUID(), "bob", "Apple",
                "iMac",2014,"intel i7", 8, 500,"iOS",Float.parseFloat("34.2"),
                "COMPUTER 2", "available", testComputer2.getTime(),
                testComputer2.getThumbnail());

        // user1 owns and lends out Computers
        //ashley is borrowing both computers
        ArrayList<Borrow> lentOut = new ArrayList<Borrow>();
        Borrow borrow1 = new Borrow(testComputer1.getId(),testUser2.getUsername());
        Borrow borrow2 = new Borrow(testComputer2.getId(),testUser2.getUsername());
        lentOut.add(borrow1);
        lentOut.add(borrow2);
        // user1 owns and lends out Computers

        ArrayList<UUID> compID = new ArrayList<UUID>();
        compID.add(testComputer1.getId());
        compID.add(testComputer2.getId());

        assertEquals("Should be same ArrayList", compID.get(0), lentOut.get(0).getBorrowID());
    }
}
