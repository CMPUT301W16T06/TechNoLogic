package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
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

/**
 * Created by Eric on 2016-03-08.
 */
public class EditUser extends ActionBarActivity {
        private User loggedUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newuser);

        // Set username uneditable
        EditText ET_newUserName = (EditText) findViewById(R.id.userUsername);
        ET_newUserName.setEnabled(false);

        // Get the User and fill out current information
        Intent intent = getIntent();
        //String username = intent.getStringExtra("user");
        //loggedUser = getUserInfo(username);
        //displayUserInfo(loggedUser);

        Button confirmUserButton = (Button) findViewById(R.id.userSubmit);
        confirmUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Check if the filled in information is correct. No missing information and valid Username
                // TODO: Return to LoginActivity if correct, otherwise stay and give notice

                loggedUser = getUserInput();

                //TODO: Update the existing user with new information
                // Add to existingUsers, save and return Intent
                finish();
                }
        });
    }

    /**
     * Uses elastic search and gets the User infomation
     *  of the given username
     * @param username string to search for
     * @return user information
     */
    //TODO: Finish elastic search, search for the username
    public User getUserInfo(String username) {
        return null;
    }

    public void displayUserInfo(User user){
        EditText ET_newUserName = (EditText) findViewById(R.id.userUsername);
        EditText ET_newName = (EditText) findViewById(R.id.userName);
        EditText ET_newEmail = (EditText) findViewById(R.id.userEmail);
        EditText ET_newPhoneNum = (EditText) findViewById(R.id.userPhone);
        EditText ET_newAddress = (EditText) findViewById(R.id.userAddress);

        ET_newUserName.setText(user.getUsername());
        ET_newName.setText(user.getName());
        ET_newEmail.setText(user.getEmail());
        ET_newPhoneNum.setText(user.getPhone());
        ET_newAddress.setText(user.getAddress());
    }
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
