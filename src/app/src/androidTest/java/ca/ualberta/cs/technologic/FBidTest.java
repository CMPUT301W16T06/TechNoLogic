package ca.ualberta.cs.technologic;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.technologic.Activities.Login;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jordan on 02/04/2016.
 */
public class FBidTest extends ActivityInstrumentationTestCase2<Login> {
    public FBidTest(){
        super(Login.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testBCheckBidded(){
        //Setup
        final Computer testComputer = new Computer(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"),
                "schraa", "Microsoft", "Surface", "2014",
                "intel i7", "8", "500", "windows", Float.parseFloat("34.2"),
                "Cool Computer", "Cool Computer", null, null);
        final Bid testBid = new Bid(testComputer.getId(),Float.parseFloat("23.42"),"jordan","schraa");
        final Bid testBid1 = new Bid(testComputer.getId(),Float.parseFloat("43.23"),"jordan","schraa");
        final Bid testBid2 = new Bid(testComputer.getId(),Float.parseFloat("35.32"),"jordan","schraa");


        Thread thread = new Thread(new Runnable() {
            public void run() {
                ArrayList<Bid> bids =
                        ElasticSearchBidding.getMyBids("jordan");
                for (Bid bidded : bids){
                    ElasticSearchBidding.deleteBid(bidded.getBidID());
                }

                ArrayList<Computer> cmput = ElasticSearchComputer.getComputers("schraa");
                for (Computer testComp : cmput){
                    ElasticSearchComputer.deleteComputer(testComp.getId().toString());
                }
                ElasticSearchComputer.addComputer(testComputer);
                ElasticSearchBidding.placeBid(testBid);
                ElasticSearchBidding.placeBid(testBid1);
                ElasticSearchBidding.placeBid(testBid2);

            }
        });
        thread.start();
        assertTrue(true);

        //Login for owner
        onView(withId(R.id.username)).perform(typeText("schraa"));
        onView(withId(R.id.login)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Received Bids")).perform(click());

        //US 05.04.01 | As an owner, I want to view a list of my things with bids
        onView(withId(R.id.myitemsbidlist)).check(matches(isDisplayed()));

        //US 05.05.01 | As an owner, I want to view the bids on one of my things
        //------------------------------------------------------------------------------------------
        onView(withId(R.id.myitemsbidlist)).perform(click());
        //check that you can see bids
        onView(withId(R.id.acceptbidslist)).check(matches(isDisplayed()));

        //US 05.07.01 | As an owner, I want to decline a bid on one of my things
        //------------------------------------------------------------------------------------------
        onView(withId(R.id.acceptbidslist)).perform(click());
        onView(withId(R.id.decline)).perform(click());

        //Make sure the bid has been Deleted to Elastic Search
        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                ArrayList<Bid> bids =
                        ElasticSearchBidding.getMyBids("jordan");
                assertEquals(bids.size(),2);
            }
        });
        thread3.start();

        //US 05.06.01 | As an owner, I want to accept a bid on one of my things,
        // setting its status to borrowed (Any other bids are declined)
        //------------------------------------------------------------------------------------------
        onView(withId(R.id.acceptbidslist)).perform(click());
        onView(withId(R.id.accept)).perform(click());

        //Make sure the bid has been Deleted to Elastic Search
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                ArrayList<Bid> bids =
                        ElasticSearchBidding.getMyBids("jordan");
                assertEquals(bids.size(),0);
            }
        });
        thread1.start();

    }
}
