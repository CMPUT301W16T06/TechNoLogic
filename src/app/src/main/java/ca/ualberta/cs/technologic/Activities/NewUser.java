package ca.ualberta.cs.technologic.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.ualberta.cs.technologic.R;
import ca.ualberta.cs.technologic.User;

public class NewUser extends ActionBarActivity {
        //private ArrayList<User> existingUsers;
        private User pendingUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Do not give access to Users who have not signed up
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newuser);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //existingUsers = getUsers();

        // Set confirmUserButton click. Return Intent will be implemented along with LoginActivity
        Button confirmUserButton = (Button) findViewById(R.id.userSubmit);
        confirmUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Check if the filled in information is correct. No missing information and valid Username
                // TODO: Return to LoginActivity if correct, otherwise stay and give notice

                pendingUser = getUserInput();

                //TODO: Validate the username. Do we want to validate all other String fields?
                if (validUsername(pendingUser.getUsername())) {
                    // Add to existingUsers, save and return Intent
                    finish();
                } else {
                    // Error message. Username already taken
                    Toast takenUser = Toast.makeText(getApplicationContext(),
                            "Username is already taken", Toast.LENGTH_SHORT);
                    takenUser.show();
                }

            }
        });
    }

    /**
     * Checks if the User's inputted username is unique, use elastic search
     * @param wantedUsername a String
     * @return true if the String is unique to the ArrayList, false if otherwise
     */
    public boolean validUsername(String wantedUsername) {
        // TODO: Check wantedUsername against existing Users
        return false;
    }

    /**
     * Gets the input text fields filled out by the User
     * @return User object
     */
    public User getUserInput() {
        // TODO: get the user's input from the EditText fields
        // Get all EditText view inputs
        EditText ET_newUserName = (EditText) findViewById(R.id.userUsername);
        EditText ET_newName = (EditText) findViewById(R.id.userName);
        EditText ET_newEmail = (EditText) findViewById(R.id.userEmail);
        EditText ET_newPhoneNum = (EditText) findViewById(R.id.userPhone);
        EditText ET_newAddress = (EditText) findViewById(R.id.userAddress);

        // Get all Strings from EditText views
        String newUserName = EditTextToString(ET_newUserName);
        String newName = EditTextToString(ET_newName);
        String newEmail = EditTextToString(ET_newEmail);
        String newPhoneNum = EditTextToString(ET_newPhoneNum);
        String newAddress = EditTextToString(ET_newAddress);

        // Create new user with the input information
        User inputUser = new User(newUserName);

        inputUser.setName(newName);
        inputUser.setEmail(newEmail);
        inputUser.setPhone(newPhoneNum);
        inputUser.setAddress(newAddress);

        return inputUser;
    }


    /**
     * Helper function for getting the text as a string from an EditText object
     * @param editText is an EditText object
     * @return returns the string of the EditText object
     */
    public String EditTextToString (EditText editText) {
        return editText.getText().toString();
    }

}

