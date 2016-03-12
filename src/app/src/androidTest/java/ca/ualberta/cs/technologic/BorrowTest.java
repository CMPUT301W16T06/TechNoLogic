package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Eric on 2016-02-11.
 */
public class BorrowTest extends ActivityInstrumentationTestCase2 {

    public BorrowTest() {
        super(Computer.class);
    }

    /**
     * testViewBorrower tests if the Borrowing view is correct
     *  Corresponds to US 06.01.01
     */
    public void testViewBorrower() {
        // Initialize Users and Computers
        User user1 = new User("Tom");
        User user2 = new User("Ashley");

        Computer comp1 = new Computer(user1.getUsername(),"Apple", "MacBook", 2013, "intel i5", 8,
                500, "Ios", Float.parseFloat("18.3"), "Sort of fast");
        Computer comp2 = new Computer(user1.getUsername(),"Apple", "MacBook", 2015, "intel i7", 8,
                500, "Ios", Float.parseFloat("18.3"), "Much faster");

        // User1 owns and lends out Computers to user2
        ArrayList<Borrow> testBorrow = new ArrayList<Borrow>();
        Borrow borrow1 = new Borrow(comp1.getId(),user2.getUsername());
        Borrow borrow2 = new Borrow(comp2.getId(),user2.getUsername());
        // user1 owns and lends out Computers

        ArrayList<UUID> compID = new ArrayList<UUID>();
        compID.add(comp1.getId());
        compID.add(comp2.getId());

        assertEquals("Should be same ArrayList", compID, testBorrow);
    }

    /**
     * testViewOwner tests if Lending out is correct
     * Corresponds to US 06.02.01
     */
    public void testViewOwner() {
        // Initialize Users and Computers
        User user1 = new User("Tom");
        User user2 = new User("Ashley");

        Computer comp1 = new Computer(user1.getUsername(),"Apple", "MacBook", 2013, "intel i5", 8,
                500, "Ios", Float.parseFloat("18.3"), "Sort of fast");
        Computer comp2 = new Computer(user1.getUsername(),"Apple", "MacBook", 2015, "intel i7", 8,
                500, "Ios", Float.parseFloat("18.3"), "Much faster");

        // user1 owns and lends out Computers
        user1.addComputer(comp1);
        user1.addComputer(comp2);
        user1.addLentOut(comp1.getId());
        user1.addLentOut(comp2.getId());

        ArrayList<UUID> compID = new ArrayList<UUID>();
        compID.add(comp1.getId());
        compID.add(comp2.getId());

        // user2 borrows Computers from user1
        user2.addBorrowing(comp1.getId());
        user2.addBorrowing(comp2.getId());

        assertEquals("Should be same UUID", compID, user1.getLentOut());
    }
}
