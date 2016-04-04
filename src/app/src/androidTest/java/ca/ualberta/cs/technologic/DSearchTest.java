package ca.ualberta.cs.technologic;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;

import ca.ualberta.cs.technologic.Activities.LentOut;
import ca.ualberta.cs.technologic.Activities.Login;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jordan on 01/04/2016.
 */
public class DSearchTest extends ActivityInstrumentationTestCase2<Login> {
    public DSearchTest(){
        super(Login.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testSearch() {
        //Add Computer for test
        final Computer testComputer = new Computer(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"),
                "schraa", "Microsoft", "Surface", "2014",
                "intel i7", "8", "500", "windows", Float.parseFloat("34.2"),
                "Cool Computer", "Cool Computer", null, null);

        //setup
        Thread thread = new Thread(new Runnable() {
            public void run() {
                ArrayList<Computer> cmput = ElasticSearchComputer.getComputers("schraa");
                for (Computer testComp : cmput){
                    ElasticSearchComputer.deleteComputer(testComp.getId().toString());
                }
                ArrayList<Bid> bids =
                        ElasticSearchBidding.getMyBids("jordan");
                for (Bid bidded : bids){
                    ElasticSearchBidding.deleteBid(bidded.getBidID());
                }
                ElasticSearchComputer.addComputer(testComputer);

            }
        });
        thread.start();
        //assertNotNull(ElasticSearchComputer.getComputers("schraa"));

        //US 04.01.01 | As a borrower, I want to specify a set of keywords, and search for all
        //things not currently borrowed whose description contains all the keywords

        //Login
        onView(withId(R.id.username)).perform(typeText("jordan"));
        onView(withId(R.id.login)).perform(click());

        //Search for a computer
        onView(withId(R.id.search)).perform(click()).perform(typeText("Cool Computer"));
        onView(withId(R.id.go)).perform(click());
        //US 04.02.01 | As a borrower, I want search results to show each thing not currently
        // borrowed with its description, owner username, and status
        //------------------------------------------------------------------------------------------

        //Make sure the computer matches the correct information and displays user and availbilty
        onView(withId(R.id.homelist)).perform(click());
        onView(withId(R.id.infoUsername)).check(matches(withText("schraa")));

        //US 05.01.01 | As a borrower, I want to bid for an available thing,
        // with a monetary rate (in dollars per hour)
        //------------------------------------------------------------------------------------------
        onView(withId(R.id.infoBid)).perform(click()).perform(typeText("50"));
        onView(withId(R.id.placebid)).perform(click());
        Espresso.pressBack();

    }

//    public void testCheckBidded(){
//        //US 05.02.01 | As a borrower, I want to view a list of things I have bidded on that are
//        // pending, each thing with its description, owner username, and my bid
//        onView(withId(R.id.username)).perform(typeText("jordan"));
//        onView(withId(R.id.login)).perform(click());
//        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
//        onView(withText("My Bids")).perform(click());
//
//        //Check to make sure the bid is displayed in a list
//        onView(withId(R.id.bidslist)).check(matches(isDisplayed()));
//
//        //Clean up
//        Thread thread = new Thread(new Runnable() {
//            public void run() {
//                ArrayList<Bid> bids =
//                        ElasticSearchBidding.getMyBids("jordan");
//                for (Bid bid : bids){
//                    ElasticSearchBidding.deleteBid(bid.getBidID());
//                }
//                ArrayList<Computer> testComp = ElasticSearchComputer.getComputers("schraa");
//                ElasticSearchComputer.deleteComputer(testComp.get(0).getId().toString());
//            }
//        });
//        thread.start();
//        assertTrue(true);
//
//        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
//        onView(withText("Log Out")).perform(click());
//    }

}
