package ca.ualberta.cs.technologic;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import ca.ualberta.cs.technologic.Activities.Login;

/**
 * Created by Jordan on 03/04/2016.
 */
public class GBorrowTest extends ActivityInstrumentationTestCase2<Login> {
    public GBorrowTest(){
        super(Login.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testBorrow(){

        //Login for owner
        onView(withId(R.id.username)).perform(typeText("jordan"));
        onView(withId(R.id.login)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("My Borrows")).perform(click());

        //US 06.01.01 | As a borrower, I want to view a list of things I am borrowing,
        // each thing with its description and owner username
        //check that list of borrowed items is on the screen
        onView(withId(R.id.borrowlist)).check(matches(isDisplayed()));

        //US 06.02.01 | As an owner, I want to view a list of my things being borrowed,
        // each thing with its description and borrower username
        //click on item and check to see if you can look at its description
        onView(withId(R.id.borrowlist)).perform(click());
        onView(withId(R.id.infoDescription)).check(matches(isDisplayed()));
        Espresso.pressBack();

        //US 10.02.01 | As a borrower, I want to view the geo location of where to receive a
        // thing I will be borrowing
        onView(withId(R.id.location)).perform(click());
        //check map is displayed
        onView(withId(R.id.map)).check(matches(isDisplayed()));


        //US 07.01.01 | As an owner, I want to set a borrowed thing to be available when it is returned
        onView(withId(R.id.returnComp)).perform(click());
        //return item and check that you have no more borrows

        Thread thread = new Thread(new Runnable() {
            public void run() {
                ArrayList<Borrow> testborrows = ElasticSearchBorrowing.getMyBorrows("jordan");
                assertEquals(testborrows.size(),0);
            }
        });
        thread.run();
    }

    public void Setuptest(){
        //Setup
        final Computer testComputer = new Computer(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"),
                "schraa", "Microsoft", "Surface", "2014",
                "intel i7", "8", "500", "windows", Float.parseFloat("34.2"),
                "Cool Computer", "Cool Computer", null, null);
        final Bid testBid = new Bid(testComputer.getId(),Float.parseFloat("23.42"),"jordan","schraa");
        final Borrow testBorrow = new Borrow(testComputer.getId(),"jordan");

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
                ElasticSearchBorrowing.addBorrow(testBorrow);
            }
        });
        thread7.start();
        assertTrue(true);
    }
}
