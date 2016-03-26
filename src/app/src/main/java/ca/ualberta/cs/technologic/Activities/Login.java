package ca.ualberta.cs.technologic.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.CurrentBids;
import ca.ualberta.cs.technologic.CurrentBorrows;
import ca.ualberta.cs.technologic.CurrentComputers;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.ElasticSearchUser;
import ca.ualberta.cs.technologic.R;
import ca.ualberta.cs.technologic.User;

public class Login extends Activity{

    private ArrayList<User> users =  new ArrayList<User>();
    private CurrentUser cu = CurrentUser.getInstance();
    private CurrentComputers cc = CurrentComputers.getInstance();
    //private CurrentBids cb = CurrentBids.getInstance();
    //private CurrentBorrows cbo = CurrentBorrows.getInstance();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText username = (EditText) findViewById(R.id.username);
        //final EditText password = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.login);
        TextView newUserButton = (TextView) findViewById(R.id.newUser);

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Boolean usernameValid = userLookup(username.getText().toString().toLowerCase());
                //usernameValid = true;

                if (usernameValid) {
                    Intent intent = new Intent(Login.this, HomePage.class);
                    cu.setCurrentUser(username.getText().toString());
                    getComputers();
                    //cb.setCurrentBids();
                    //cbo.setCurrentBorrows();
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Wrong Username or password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        newUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, NewUser.class);
                startActivity(intent);
            }
        });
    }

    private Boolean userLookup(final String username) {
            // TODO: Check wantedUsername against existing Users
            // TODO: Fix lowercase problem
            if (username.equals("")) {
                return false;
            }

            Thread thread = new Thread(new Runnable() {
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
            return (users.size() == 1);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }
    private void getComputers() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                cc.setCurrentComputers(ElasticSearchComputer.getComputers(cu.getCurrentUser()));
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
