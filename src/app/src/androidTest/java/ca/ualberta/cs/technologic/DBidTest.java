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
public class DBidTest extends ActivityInstrumentationTestCase2<Login> {
    public DBidTest(){
        super(Login.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        SystemClock.sleep(5000);
    }

    public void testBCheckBidded(){
        //US 05.02.01 | As a borrower, I want to view a list of things I have bidded on that are
        // pending, each thing with its description, owner username, and my bid
        //Login
        onView(withId(R.id.username)).perform(typeText("jordan"));
        onView(withId(R.id.login)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("My Bids")).perform(click());

        //Check to make sure the bid is displayed in a list
        onView(withId(R.id.bidslist)).check(matches(isDisplayed()));

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Log Out")).perform(click());


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



//        //Make sure the bid has been Deleted to Elastic Search
//        Thread thread = new Thread(new Runnable() {
//            public void run() {
//                ArrayList<Bid> bids =
//                        ElasticSearchBidding.getMyBids("jordan");
//                assertEquals(bids.size(),0);
//            }
//        });
//        thread.start();
//
    }
}
