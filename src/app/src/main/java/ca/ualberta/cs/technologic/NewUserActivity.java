package ca.ualberta.cs.technologic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


/**
 * Created by gknoblau on 2016-02-16.
 */
public class NewUserActivity extends Activity {
    private ArrayList<User> existingUsers;
    private User pendingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newuser);

        // Set confirmUserButton click
        Button confirmUserButton = (Button) findViewById(R.id.confirmNewUser);
        confirmUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Check if the filled in information is correct. No missing information and valid Username
                // TODO: Return to LoginActivity if correct, otherwise stay and give notice
            }
        });

        Button cancelUserButton = (Button) findViewById(R.id.cancelNewUser);
        cancelUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Return to LoginActivity, do nothing
            }
        });
    }

    public void getUsers() {
        // TODO: Load up or get the ArrayList of Users
    }

    public boolean checkUsername(String wantedUsername) {
        // TODO: Check wantedUsername against existing Users
        return false;
    }

    // Helper function
    public String EditTextToString (EditText editText) {

        return editText.getText().toString();

    }

    public void getUserInput() {
        // TODO: get the user's input from the EditText fields
        /*
        android:id="@+id/name"
        android:id="@+id/username"
        android:id="@+id/password"
        android:id="@+id/email"
        android:id="@+id/phoneNum"
        android:id="@+id/address" */

        // Get all EditText views
        EditText ET_newName = (EditText) findViewById(R.id.name);
        EditText ET_newUserName = (EditText) findViewById(R.id.username);
        EditText ET_newPassword = (EditText) findViewById(R.id.password);
        EditText ET_newEmail = (EditText) findViewById(R.id.email);
        EditText ET_newPhoneNum = (EditText) findViewById(R.id.phoneNum);
        EditText ET_newAddress = (EditText) findViewById(R.id.address);


    }

}
