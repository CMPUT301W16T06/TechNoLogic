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

import ca.ualberta.cs.technologic.Activities.Login;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jordan on 01/04/2016.
 */

public class CUserInfoTest extends ActivityInstrumentationTestCase2<Login> {
    public CUserInfoTest() {
        super(Login.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }


    public void testNewUser(){
        //US 03.01.01 | As a user, I want a profile with a unique username and my contact information
        //clear billybob as a username
        try {
            ArrayList<User> users = new ArrayList<User>();
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ArrayList<User> users =
                            ElasticSearchUser.getUsers("BillyBob");
                    ElasticSearchUser.deleteUser(users.get(0).getId());
                }
            });
            thread.run();
        }catch (Exception e){

        }
        //enter user information
        onView(withId(R.id.newUser)).perform(click());

        onView(withId(R.id.userUsername)).perform(typeText("BillyBob"));
        onView(withId(R.id.userName)).perform(typeText("Billy Bob"));
        onView(withId(R.id.userEmail)).perform(typeText("billy.bob@gmail.com"));
        onView(withId(R.id.userPhone)).perform(typeText("403 123 4567"));
        onView(withId(R.id.userAddress)).perform(typeText("Edmonton"));

        onView(withId(R.id.userSubmit)).perform(click());

        //login
        onView(withId(R.id.username)).perform(click()).perform(typeText("BillyBob"));
        onView(withId(R.id.login)).perform(click());

        //US 03.02.01 | As a user, I want to edit the contact information in my profile
        //------------------------------------------------------------------------------------------
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Account Settings")).perform(click());

        onView(withId(R.id.edit_userName)).perform(clearText()).perform(typeText("Billy Joel"));
        onView(withId(R.id.edit_userSubmit)).perform(click());

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Log Out")).perform(click());

        //Make sure the user was actually edited
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<User> users = ElasticSearchUser.getUsers("BillyBob");
                assertEquals(users.get(0).getName(),"Billy Joel");
            }
        });

        //Delete the test user
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                ArrayList<User> users =
                        ElasticSearchUser.getUsers("BillyBob");
                ElasticSearchUser.deleteUser(users.get(0).getId());
            }
        });
        thread2.run();

    }


}
