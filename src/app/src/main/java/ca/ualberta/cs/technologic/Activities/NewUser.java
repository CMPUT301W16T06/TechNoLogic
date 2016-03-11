package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.technologic.ElasticSearchController;
import ca.ualberta.cs.technologic.R;
import ca.ualberta.cs.technologic.User;

public class NewUser extends ActionBarActivity {
    boolean isEdit;
    private User pendingUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Do not give access to Users who have not signed up
        if (!isEdit) {
            menu.clear();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newuser);

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("bool",false);
        if (isEdit) {
            EditText ET_newUserName = (EditText) findViewById(R.id.userUsername);
            ET_newUserName.setEnabled(false);
            ET_newUserName.setTextColor(Color.parseColor("#ffffff"));
            ET_newUserName.setBackgroundResource(R.drawable.edittextdark);
            String username = intent.getStringExtra("user");
            ET_newUserName.setText("USERNAME");
            //pendingUser = getUserInfo(username);
            //displayUserInfo(pendingUser);
        }

        // Set confirmUserButton click. Return Intent will be implemented along with LoginActivity
        Button confirmUserButton = (Button) findViewById(R.id.userSubmit);
        confirmUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Check if the filled in information is correct. No missing information and valid Username
                // TODO: Return to LoginActivity if correct, otherwise stay and give notice

                pendingUser = getUserInput();

                //TODO: Validate the username. Do we want to validate all other String fields?
                if (isEdit) {
                    Toast takenUser = Toast.makeText(getApplicationContext(),
                            "Is edit user", Toast.LENGTH_SHORT);
                    takenUser.show();
                    finish();
                } else if (validUsername(pendingUser.getUsername())) {
                    // Add to existingUsers, save and return Intent
                    Toast takenUser = Toast.makeText(getApplicationContext(),
                            "Is new user", Toast.LENGTH_SHORT);
                    takenUser.show();
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
        ElasticSearchController.GetUsersTask getUserTask = new ElasticSearchController.GetUsersTask();
        getUserTask.execute(wantedUsername);
        try {
            ArrayList<User> foundUsers;
            foundUsers = getUserTask.get();
            return foundUsers.size() == 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the input text fields filled out by the User
     * @return User object
     */
    public User getUserInput() {
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
     *  Get the user information on the given username
     * @param username elastic search for this user
     * @return User corrisponding to the username
     */
    public User getUserInfo(String username) {
        //TODO: elastic search implementation
        return null;
    }

    /**
     *  Displays the User's current information so that they
     *      can edit.
     * @param user information to set the textfields to edit
     */
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

    /**
     * Helper function for getting the text as a string from an EditText object
     * @param editText is an EditText object
     * @return returns the string of the EditText object
     */
    public String EditTextToString (EditText editText) {
        return editText.getText().toString();
    }

}

