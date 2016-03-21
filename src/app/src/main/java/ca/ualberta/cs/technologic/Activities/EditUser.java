package ca.ualberta.cs.technologic.Activities;

import android.graphics.Color;
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
 * Created by Eric on 2016-03-21.
 */
public class EditUser extends ActionBarActivity {
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
                update(previousUser, updatedUser);
                //TODO Maybe not needed
                cu.setCurrentUser(updatedUser.getUsername());
                finish();
            }
        });
    }

    public User getUserInput() {
        // Get all Strings from EditText views
        String newUserName = findViewById(R.id.edit_userUsername).toString();
        String newName = EditTextToString((EditText) findViewById(R.id.edit_userName));
        String newEmail = EditTextToString((EditText) findViewById(R.id.edit_userEmail));
        String newPhoneNum = EditTextToString((EditText) findViewById(R.id.edit_userPhone));
        String newAddress = EditTextToString((EditText) findViewById(R.id.edit_userAddress));

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

    public void displayUserInfo(User user){
        TextView ET_newUserName = (TextView) findViewById(R.id.edit_userUsername);
        EditText ET_newName = (EditText) findViewById(R.id.edit_userName);
        EditText ET_newEmail = (EditText) findViewById(R.id.edit_userEmail);
        EditText ET_newPhoneNum = (EditText) findViewById(R.id.edit_userPhone);
        EditText ET_newAddress = (EditText) findViewById(R.id.edit_userAddress);

        ET_newUserName.setText(user.getUsername());
        ET_newName.setText(user.getName());
        ET_newEmail.setText(user.getEmail());
        ET_newPhoneNum.setText(user.getPhone());
        ET_newAddress.setText(user.getAddress());
    }

    public String EditTextToString (EditText editText) {
        return editText.getText().toString();
    }

    public User getUserInfo(final String username) {
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

    public void update(final User oldUser, final User newUser) {

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
