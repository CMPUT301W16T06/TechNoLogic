package ca.ualberta.cs.technologic;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.technologic.Activities.Login;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Jordan on 01/04/2016.
 */
public class GUITest extends ActivityInstrumentationTestCase2<Login> {
    public GUITest () {
        super(Login.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        getActivity();
    }

    public void testClick(){
        onView(withId(R.id.username)).perform(typeText("schraa"));
        onView(withId(R.id.login)).perform(click());
    }

}
