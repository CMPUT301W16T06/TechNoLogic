//
//
////I changed the structure of the activities but I didn't want to just get rid of gregs work
//
//package ca.ualberta.cs.technologic.Activities;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//        import android.widget.Button;
//        import android.widget.EditText;
//        import android.widget.Toast;
//
//        import java.util.ArrayList;
//
//import ca.ualberta.cs.technologic.R;
//import ca.ualberta.cs.technologic.User;
//
//
///**
// * Created by gknoblau on 2016-02-16.
// */
//public class NewUserActivity extends Activity {
//    private ArrayList<User> existingUsers;
//    private User pendingUser;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.newuser);
//
//        existingUsers = getUsers();
//
//        // Set confirmUserButton click. Return Intent will be implemented along with LoginActivity
//        Button confirmUserButton = (Button) findViewById(R.id.confirmNewUser);
//        confirmUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO: Check if the filled in information is correct. No missing information and valid Username
//                // TODO: Return to LoginActivity if correct, otherwise stay and give notice
//
//                pendingUser = getUserInput();
//                // Validate the username. Do we want to validate all other String fields?
//                if (validUsername(pendingUser.getUsername())) {
//                    // Add to existingUsers and return Intent
//
//                } else {
//                    // Error message. Username already taken
//                    Toast takenUser = Toast.makeText(getApplicationContext(),
//                            "Username is already taken", Toast.LENGTH_SHORT);
//                    takenUser.show();
//                }
//            }
//        });
//
//        // Set cancelUserButton click. Return Intent will be implemented along with LoginActivity
//        Button cancelUserButton = (Button) findViewById(R.id.cancelNewUser);
//        cancelUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO: Return to LoginActivity, do nothing
//            }
//        });
//    }
//
//    /**
//     * gets the Users
//     * @return an ArrayList<User> of all User's who currently exist
//     */
//    public ArrayList<User> getUsers() {
//        // TODO: Load up or get the ArrayList of current Users
//        return null;
//    }
//
//    /**
//     * Checks if the User's inputted username is unique
//     * @param wantedUsername a String
//     * @return true if the String is unique to the ArrayList, false if otherwise
//     */
//    public boolean validUsername(String wantedUsername) {
//        // TODO: Check wantedUsername against existing Users
//        return false;
//    }
//
//    /**
//     * Gets the input text fields filled out by the User
//     */
//    public User getUserInput() {
//        // TODO: get the user's input from the EditText fields
//        /*
//        android:id="@+id/name"
//        android:id="@+id/username"
//        android:id="@+id/password"
//        android:id="@+id/email"
//        android:id="@+id/phoneNum"
//        android:id="@+id/address" */
//
//        // Get all EditText views
//        EditText ET_newName = (EditText) findViewById(R.id.name);
//        EditText ET_newUserName = (EditText) findViewById(R.id.username);
//        EditText ET_newPassword = (EditText) findViewById(R.id.password);
//        EditText ET_newEmail = (EditText) findViewById(R.id.email);
//        EditText ET_newPhoneNum = (EditText) findViewById(R.id.phoneNum);
//        EditText ET_newAddress = (EditText) findViewById(R.id.address);
//
//        String newName = EditTextToString(ET_newName);
//        String newUserName = EditTextToString(ET_newUserName);
//        String newPassword = EditTextToString(ET_newPassword);
//        String newEmail = EditTextToString(ET_newEmail);
//        String newPhoneNum = EditTextToString(ET_newPhoneNum);
//        String newAddress = EditTextToString(ET_newAddress);
//
//        //TODO: Create a new user object. Depends on our constructor
//        //User inputUser = new User();
//        //return inputUser;
//        return null;
//    }
//
//
//    /**
//     * Helper function for getting the text as a string from an EditText object
//     * @param editText is an EditText object
//     * @return returns the string of the EditText object
//     */
//    public String EditTextToString (EditText editText) {
//        return editText.getText().toString();
//    }
//
//}
//
