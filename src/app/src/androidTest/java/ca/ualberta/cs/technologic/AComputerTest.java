package ca.ualberta.cs.technologic;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Matcher;

import ca.ualberta.cs.technologic.Activities.AddComputer;
import ca.ualberta.cs.technologic.Activities.Login;
import ca.ualberta.cs.technologic.Activities.MyComputers;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by Jordan on 01/04/2016.
 */
public class AComputerTest extends ActivityInstrumentationTestCase2<Login> {
    public AComputerTest() {
        super(Login.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testAddComputer() {
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
            }
        });
        thread.start();
        assertNotNull(ElasticSearchComputer.getComputers("schraa"));
        //Login
        onView(withId(R.id.username)).perform(typeText("schraa"));
        onView(withId(R.id.login)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("My Computers")).perform(click());

        //US 01.01.01 | As an owner, I want to add a thing in my things,
        //each denoted with a clear, suitable description
        //------------------------------------------------------------------------------------------
        onView(withId(R.id.addnewitem)).perform(click());
        //Add information on computer
        onView(withId(R.id.make)).perform(typeText("Apple"));
        onView(withId(R.id.model)).perform(typeText("MacBook"));
        onView(withId(R.id.harddrive)).perform(typeText("500"));
        onView(withId(R.id.memory)).perform(typeText("8"));
        onView(withId(R.id.processor)).perform(typeText("Intel i7"));
        onView(withId(R.id.os)).perform(typeText("iOS"));
        onView(withId(R.id.year)).perform(typeText("2015"));
        onView(withId(R.id.baserate)).perform(typeText("50"));
        onView(withId(R.id.description)).perform(typeText("This is a fancy computer"));

        onView(withId(R.id.submit)).perform(click());

        //Make sure computer has been added with correct information
        ArrayList<Computer> compsTemp = new ArrayList<Computer>();
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                ArrayList<Computer> compsTemp =
                        ElasticSearchComputer.getComputers("schraa");
            }
        });
        thread1.start();
        assertNotNull(compsTemp);
        for (Computer cmput : compsTemp) {
            if (cmput.getDescription().equals("This is a fancy computer")) {
                assertEquals(cmput.getProcessor(), "Intel i7");
                assertEquals(cmput.getMake(), "Apple");
            }
        }
        Log.v("US 1.01.01", "Add Computer US Test complete");
        System.out.println("US 1.01.01 DONE");

        //US 01.02.01 | As an owner, I want to view a list of all my things,
        // and their descriptions and statuses
        //US 01.03.01 | As an owner, I want to view one of my things, its description and status

        //US 01.04.01 | As an owner, I want to edit a thing in my things
        //------------------------------------------------------------------------------------------
        onView(withId(R.id.myitemslist)).perform(click());
        try {
            onView(withId(R.id.model)).perform(click()).perform(clearText());
            onView(withId(R.id.model)).perform(typeText("MacBook Pro"));
            onView(withId(R.id.btnUpdate)).perform(scrollTo()).check(matches(isDisplayed()));
            onView(withId(R.id.btnUpdate)).perform(click());
        } catch (Exception e) {
            onView(withId(R.id.infoModel)).perform(click()).perform(clearText());
            onView(withId(R.id.infoModel)).perform(typeText("MacBook Pro"));
            onView(withId(R.id.btnUpdate)).perform(scrollTo()).check(matches(isDisplayed()));
            onView(withId(R.id.btnUpdate)).perform(click());
        }

        //Make sure the info was updated correctly
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                ArrayList<Computer> compsTemp =
                        ElasticSearchComputer.getComputers("schraa");
            }
        });
        thread2.start();
        assertNotNull(compsTemp);
        int length = (compsTemp).size();
        for (Computer cmput : compsTemp) {
            if (cmput.getDescription().equals("This is a fancy computer")) {
                assertEquals(cmput.getModel(), "MacBook Pro");
                assertEquals(cmput.getMake(), "Apple");
            }
        }

        ////US 01.05.01 | As an owner, I want to delete a thing in my things
        //------------------------------------------------------------------------------------------
        onView(withId(R.id.myitemslist)).perform(click());
        try {
            onView(withId(R.id.model)).perform(click());
            onView(withId(R.id.btnDelete)).perform(scrollTo()).check(matches(isDisplayed()));
            onView(withId(R.id.btnDelete)).perform(click());
        } catch (Exception e) {
            onView(withId(R.id.infoModel)).perform(click());
            onView(withId(R.id.btnDelete)).perform(scrollTo()).check(matches(isDisplayed()));
            onView(withId(R.id.btnDelete)).perform(click());
        }

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Log Out")).perform(click());

        //Make sure the computer has been deleted
        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                ArrayList<Computer> compsTemp =
                        ElasticSearchComputer.getComputers("schraa");
            }
        });
        thread3.start();
        assertEquals(compsTemp.size(), 0);

    }
}
