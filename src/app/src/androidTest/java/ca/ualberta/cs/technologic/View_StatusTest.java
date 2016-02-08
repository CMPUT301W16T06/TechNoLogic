package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Jordan on 06/02/2016.
 */


public class View_StatusTest extends ActivityInstrumentationTestCase2{
    public View_StatusTest(Class activityClass) {
        super(activityClass);
    }

    /**
     * Test if the status of a computer is Available
     */
    public void testStatusAvail() {
        Computer comp1 = new Computer("make", "9-aj3", 2011, "Intel Core i3", 4, 256, "Windows 7", (float)13.33, "Cheap Computer to borrow");

        ComputBids cmpBid = new ComputBids();
        CmputBorrows cmpBor = new CmputBorrows();

        // Assuming Computer.getStatus() returns a string by looking through
        // if the computer is not in the Bids or Borrows
        assertEquals("Available", comp1.getStatus());
    }

    /**
     * Test if the status of a computer is Bidding
     */
    public void testStatusBidding() {
        Computer comp2 = new Computer("make", "9-aj5", 2013, "Intel Core i5", 4, 500, "Windows 7", (float)12.44, "Cheap Computer to borrow");
        ComputBids cmpBid = new CmputBids();
        ComputBorrows cmpBor = new CmputBorrows();

        cmpBid.add(comp2);      // Assume some way to add into Bids
        assertEquals("Bidding", comp2.getStatus());
    }

    /**
     * Test if the status of a computer is Borrowing
     */
    public void testStatusBorrowing() {
        Computer comp3 = new Computer("make", "7-ag7", 2006, "Intel Core i3", 2, 256, "Windows XP", (float)8.43, "Cheap Computer to borrow");
        ComputBids cmpBid = new CmputBids();
        ComputBorrows cmpBor = new CmputBorrows();

        cmpBor.add(comp2);      // Assume some way to add into Borrowing
        assertEquals("Borrowing", comp3.getStatus());
    }
}
