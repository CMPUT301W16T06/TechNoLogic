package ca.ualberta.cs.technologic.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentBids;
import ca.ualberta.cs.technologic.CurrentComputers;
import ca.ualberta.cs.technologic.CurrentOffline;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.ElasticSearchUser;
import ca.ualberta.cs.technologic.R;
import ca.ualberta.cs.technologic.User;

/**
 * Allows the user to login,
 * the user can also create a new account if they dont already have one
 */

public class Login extends Activity{

    //singletons
    private CurrentUser cu = CurrentUser.getInstance();
    private CurrentComputers cc = CurrentComputers.getInstance();
    private CurrentBids cb = CurrentBids.getInstance();
    private CurrentOffline co = CurrentOffline.getInstance();

    //variables
    private ArrayList<User> users =  new ArrayList<User>();

    //UI elements
    private EditText username;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.username);
        Button loginButton = (Button) findViewById(R.id.login);
        TextView newUserButton = (TextView) findViewById(R.id.newUser);

        try {
            cu.clear();
            cc.clear();
            cb.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Boolean usernameValid = userLookup(username.getText().toString().toLowerCase());
                //boolean usernameValid = true;

                if (usernameValid) {
                    Intent intent = new Intent(Login.this, HomePage.class);
                    setUser();
                    getComputers();
                    getBids();
                    getOffline();
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

    /**
     * check if the user exists in the systems
     * @param username the username entered
     * @return true if username exists, false if not
     */
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
    private  void setUser() {
        cu.setCurrentUser(username.getText().toString());

    }

    /**
     * gets the computers for the computer singleton
     */
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

    /**
     * gets the bids for the bids singleton
     */
    private void getBids() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                cb.setCurrentBids(ElasticSearchComputer.getComputersBidded(cu.getCurrentUser()));
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * get information for Offline singleton
     */
    private void getOffline() {
        Thread thread = new Thread(new Runnable() {
            ArrayList<Computer> c = new ArrayList<Computer>();
            public void run() {
                try {
                    FileInputStream fis = openFileInput("computers.sav");
                    c = co.loadComputersFile(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                co.setCurrentOffline(c);
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
