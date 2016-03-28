package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.ElasticSearchUser;
import ca.ualberta.cs.technologic.R;
import ca.ualberta.cs.technologic.User;

/**
 * ViewUser allows the User to view other User's
 *  personal information.
 *
 *  //TODO: Put into rest of app. When starting an Intent to ViewUser, pass in a string with the tag "username"
 */
public class ViewUser extends ActionBarActivity {
    private ArrayList<User> users;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                startActivity(new Intent(this, HomePage.class));
                finish();
                break;
            case R.id.myitems:
                startActivity(new Intent(this, MyComputers.class));
                finish();
                break;
            case R.id.accountsettings:
                startActivity(new Intent(this, EditUser.class));
                finish();
                break;
            case R.id.logout:
                startActivity(new Intent(this, Login.class));
                finish();
                break;
            case R.id.mybids:
                startActivity(new Intent(this, MyBids.class));
                finish();
                break;
            case R.id.myborrows:
                startActivity(new Intent(this, MyBorrows.class));
                finish();
                break;
            case R.id.lentout:
                startActivity(new Intent(this, LentOut.class));
                finish();
                break;
            case R.id.myitembids:
                startActivity(new Intent(this, ReceivedBids.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewuser);

        Intent intent = getIntent();
        String user = intent.getStringExtra("username");
        User userToDisplay = getUserInfo(user);
        displayUserInfo(userToDisplay);
    }

    private User getUserInfo(final String username) {
        users = new ArrayList<User>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                users = ElasticSearchUser.getUsers(username);
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return users.get(0);
    }

    private void displayUserInfo(User user){
        TextView UserName = (TextView) findViewById(R.id.view_userUsername);
        TextView Name = (TextView) findViewById(R.id.view_userName);
        TextView Email = (TextView) findViewById(R.id.view_userEmail);
        TextView PhoneNum = (TextView) findViewById(R.id.view_userPhone);
        TextView Address = (TextView) findViewById(R.id.view_userAddress);

        UserName.setText(user.getUsername());
        Name.setText(user.getName());
        Email.setText(user.getEmail());
        PhoneNum.setText(user.getPhone());
        Address.setText(user.getAddress());
    }
}
