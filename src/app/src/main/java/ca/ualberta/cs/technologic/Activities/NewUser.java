package ca.ualberta.cs.technologic.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.ElasticSearchUser;
import ca.ualberta.cs.technologic.R;
import ca.ualberta.cs.technologic.User;

/**
 * The NewUser class is an activity for creating a new user or editing an existing user.
 * It will take inputs from the User and check if the Username exists in ElasticSearch.
 *
 *
 */
public class NewUser extends ActionBarActivity {
    private User pendingUser;
    private ArrayList<User> currentUsers;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newuser);

        // Set confirmUserButton click. Return Intent will be implemented along with Login
        Button confirmUserButton = (Button) findViewById(R.id.userSubmit);
        confirmUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pendingUser = getUserInput();

                if (pendingUser == null) {
                    Toast takenUser = Toast.makeText(getApplicationContext(),
                            "One or more fields are empty", Toast.LENGTH_SHORT);
                    takenUser.show();

                // PendingUser is not null, check availability
                } else if (availUsername(pendingUser.getUsername())) {
                    Toast takenUser = Toast.makeText(getApplicationContext(),
                            "Is new user", Toast.LENGTH_SHORT);
                    takenUser.show();
                    add(pendingUser);
                    finish();

                } else {
                    // Error message. Username already taken
                    Toast takenUser = Toast.makeText(getApplicationContext(),
                            "Username already taken!", Toast.LENGTH_SHORT);
                    takenUser.show();
                }
            }
        });
    }

    /**
     * Checks if the User's inputted username is available, use elastic search
     * @param wantedUsername a String
     * @return true if the String is unique to the ArrayList, false if otherwise
     */
    private boolean availUsername(final String wantedUsername) {
        currentUsers = new ArrayList<User>();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                currentUsers = ElasticSearchUser.getUsers(wantedUsername);
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (currentUsers.size() == 0);
    }

    /**
     * Gets the input text fields filled out by the User
     * @return User object
     */
    private User getUserInput() {
        // Get all Strings from EditText views
        String newUserName = EditTextToString((EditText) findViewById(R.id.userUsername));
        String newName = EditTextToString((EditText) findViewById(R.id.userName));
        String newEmail = EditTextToString((EditText) findViewById(R.id.userEmail));
        String newPhoneNum = EditTextToString((EditText) findViewById(R.id.userPhone));
        String newAddress = EditTextToString((EditText) findViewById(R.id.userAddress));

        if (newUserName.equals("") || newName.equals("") || newEmail.equals("") || newPhoneNum.equals("") || newAddress.equals("")) {
            return null;
        }
        // Create new user with the input information
        User inputUser = new User(newUserName);

        inputUser.setName(newName);
        inputUser.setEmail(newEmail);
        inputUser.setPhone(newPhoneNum);
        inputUser.setAddress(newAddress);

        Toast takenUser = Toast.makeText(getApplicationContext(),
                inputUser.getUsername() + '|' + inputUser.getName() + '|' +inputUser.getEmail() + '|' + inputUser.getPhone() + '|' + inputUser.getAddress(), Toast.LENGTH_SHORT);
        takenUser.show();

        return inputUser;
    }

    /**
     * Helper function for getting the text as a string from an EditText object
     * @param editText is an EditText object
     * @return returns the string of the EditText object
     */
    private String EditTextToString (EditText editText) {

        return editText.getText().toString();
    }

    /**
     * Runs ElasticSearch and adds a new User
     * @param user User object to add in
     */
    private void add(final User user) {
        try {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ElasticSearchUser.addUser(user);
                }
            });

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Everything is OK!
            setResult(RESULT_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}