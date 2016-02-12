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

    public void testViewBorrower() {
        User user1 = new User("Tom", "thetankengine");
        User user2 = new User("Ashley", "strongpassword");


        Computer comp1 = new Computer("Apple", "MacBook", 2013, "intel i5", 8,
                500, "Ios", Float.parseFloat("18.3"), "Sort of fast");
        Computer comp2 = new Computer("Apple", "MacBook", 2015, "intel i7", 8,
                500, "Ios", Float.parseFloat("18.3"), "Much faster");

        user1.addComputer(comp1);
        user1.addComputer(comp2);
        user1.addLentOut(comp1.getId());
        user1.addLentOut(comp2.getId());

        ArrayList<UUID> compID = new ArrayList<>();
        compID.add(comp1.getId());
        compID.add(comp2.getId());

        user2.addBorrowing(comp1.getId());
        user2.addBorrowing(comp2.getId());

        assertEquals("Should be same UUID", compID, user2.getBorrowing());
    }

    public void testViewOwner() {
        User user1 = new User("Tom", "thetankengine");
        User user2 = new User("Ashley", "strongpassword");

        Computer comp1 = new Computer("Apple", "MacBook", 2013, "intel i5", 8,
                500, "Ios", Float.parseFloat("18.3"), "Sort of fast");
        Computer comp2 = new Computer("Apple", "MacBook", 2015, "intel i7", 8,
                500, "Ios", Float.parseFloat("18.3"), "Much faster");

        user1.addComputer(comp1);
        user1.addComputer(comp2);
        user1.addLentOut(comp1.getId());
        user1.addLentOut(comp2.getId());

        ArrayList<UUID> compID = new ArrayList<>();
        compID.add(comp1.getId());
        compID.add(comp2.getId());

        user2.addBorrowing(comp1.getId());
        user2.addBorrowing(comp2.getId());

        assertEquals("Should be same UUID", compID, user1.getLentOut());
    }
}
