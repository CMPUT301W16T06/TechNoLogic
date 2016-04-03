package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchUser;
import ca.ualberta.cs.technologic.R;
import ca.ualberta.cs.technologic.User;

/**
 * EditUser is the activity for when the User
 *  wants to edit their personal information.
 *  It will display their current information,
 *  and then allow the User to update all fields
 *  of information except for their username.
 */
public class EditUser extends ActionBarActivity {
    //variables
    private ArrayList<User> currentUsers;
    private CurrentUser cu;
    private User previousUser;
    private User updatedUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                break;
            case R.id.myitems:
                Intent intent = new Intent(this, MyComputers.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                finish();
                break;
            case R.id.accountsettings:
                Intent intent1 = new Intent(this, EditUser.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.logout:
                Intent intent2 = new Intent(this, Login.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
            case R.id.mybids:
                Intent intent3 = new Intent(this, MyBids.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                break;
            case R.id.myborrows:
                Intent intent4 = new Intent(this, MyBorrows.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent4);
                break;
            case R.id.lentout:
                Intent intent5 = new Intent(this, LentOut.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
                break;
            case R.id.myitembids:
                Intent intent6 = new Intent(this, ReceivedBids.class);
                intent6.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent6);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edituser);

        cu = CurrentUser.getInstance();
        previousUser = getUserInfo(cu.getCurrentUser());
        displayUserInfo(previousUser);

        Button submitEditInfoButton = (Button) findViewById(R.id.edit_userSubmit);
        submitEditInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedUser = getUserInput();
                if (updatedUser != null) {
                    update(previousUser, updatedUser);
                    finish();
                } else {
                    Toast errorToast = Toast.makeText(getApplicationContext(),
                            "One of the fields are empty!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        });
    }

    /**
     * Gets the User's input with updated account
     *  information.
     * @return User object
     */
    private User getUserInput() {
        // Get all Strings from EditText views. We want to check if any fields are empty or else
        // it may cause errors.
        String newUserName = ((TextView) findViewById(R.id.edit_userUsername)).getText().toString();
        String newName = EditTextToString((EditText) findViewById(R.id.edit_userName));
        String newEmail = EditTextToString((EditText) findViewById(R.id.edit_userEmail));
        String newPhoneNum = EditTextToString((EditText) findViewById(R.id.edit_userPhone));
        String newAddress = EditTextToString((EditText) findViewById(R.id.edit_userAddress));

        if (newUserName.equals("") || newName.equals("") || newEmail.equals("")
                || newPhoneNum.equals("") || newAddress.equals("")) {
            return null;
        }

        // Create new user with the input information
        User inputUser = new User(newUserName);

        inputUser.setName(newName);
        inputUser.setEmail(newEmail);
        inputUser.setPhone(newPhoneNum);
        inputUser.setAddress(newAddress);

        return inputUser;
    }

    /**
     * Displays the User's current information
     * @param user Current information to display
     */
    private void displayUserInfo(User user){
        TextView UserName = (TextView) findViewById(R.id.edit_userUsername);
        EditText Name = (EditText) findViewById(R.id.edit_userName);
        EditText Email = (EditText) findViewById(R.id.edit_userEmail);
        EditText PhoneNum = (EditText) findViewById(R.id.edit_userPhone);
        EditText Address = (EditText) findViewById(R.id.edit_userAddress);

        UserName.setText(user.getUsername());
        Name.setText(user.getName());
        Email.setText(user.getEmail());
        PhoneNum.setText(user.getPhone());
        Address.setText(user.getAddress());
    }

    /**
     * Helper function for getting strings
     * @param editText EditText object to get string from
     * @return String of the EditText
     */
    private String EditTextToString (EditText editText) {
        return editText.getText().toString();
    }

    /**
     *  Runs ElasticSearch in order to get the User's information
     * @param username String of a username
     * @return User object with the same username
     */
    private User getUserInfo(final String username) {
        //TODO: elastic search implementation
        currentUsers = new ArrayList<User>();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                currentUsers = ElasticSearchUser.getUsers(username);
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentUsers.get(0);
    }

    /**
     *  Runs ElasticSearch in order to update the User's
     *      personal information
     * @param oldUser User's information before submit
     * @param newUser User's information after submit
     */
    private void update(final User oldUser, final User newUser) {

        try {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ElasticSearchUser.updateUser(oldUser, newUser);
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
