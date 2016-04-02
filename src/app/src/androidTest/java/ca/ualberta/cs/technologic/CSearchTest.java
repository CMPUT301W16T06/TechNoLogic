package ca.ualberta.cs.technologic;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Matcher;

import ca.ualberta.cs.technologic.Activities.Login;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jordan on 01/04/2016.
 */
public class CSearchTest extends ActivityInstrumentationTestCase2<Login> {
    public CSearchTest(){
        super(Login.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        SystemClock.sleep(5000);
    }

    public void testASearch(){
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

        onView(withId(R.id.infoBid)).perform(click()).perform(clearText()).perform(typeText("100"));
        onView(withId(R.id.placebid)).perform(click());
        Espresso.pressBack();

        onView(withId(R.id.infoBid)).perform(click()).perform(clearText()).perform(typeText("150"));
        onView(withId(R.id.placebid)).perform(click());
        Espresso.pressBack();

        //Make sure the bid has been added to Elastic Search
        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                ArrayList<Bid> bids =
                        ElasticSearchBidding.getMyBids("jordan");
                assertEquals(bids.size(),3);
            }
        });
        thread3.start();
         //TODO: Tests fails here
    }

}
