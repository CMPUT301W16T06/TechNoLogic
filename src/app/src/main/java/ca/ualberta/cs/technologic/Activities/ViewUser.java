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
 *  //TODO: Do something with UI, buttons do not do anything currently.
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
                Intent intent0 = new Intent(this, HomePage.class);
                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent0);
                break;
            case R.id.myitems:
                Intent intent = new Intent(this, MyComputers.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
