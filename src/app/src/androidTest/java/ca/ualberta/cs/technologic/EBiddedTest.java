package ca.ualberta.cs.technologic;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.technologic.Activities.Login;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jordan on 03/04/2016.
 */
public class EBiddedTest extends ActivityInstrumentationTestCase2<Login> {
    public EBiddedTest(){
        super(Login.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testBidded(){

        Setuptest();
        //US 05.02.01 | As a borrower, I want to view a list of things I have bidded on that are
        // pending, each thing with its description, owner username, and my bid
        //Login
        onView(withId(R.id.username)).perform(typeText("jordan"));
        onView(withId(R.id.login)).perform(click());



        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("My Bids")).perform(click());
    }

    public void Setuptest(){
        //Setup
        final Computer testComputer = new Computer(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"),
                "schraa", "Microsoft", "Surface", "2014",
                "intel i7", "8", "500", "windows", Float.parseFloat("34.2"),
                "Cool Computer", "Cool Computer", null, null);
        final Bid testBid = new Bid(testComputer.getId(),Float.parseFloat("23.42"),"jordan","schraa");

        Thread thread7 = new Thread(new Runnable() {
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
            }
        });
        thread7.start();
        assertTrue(true);
    }
}
