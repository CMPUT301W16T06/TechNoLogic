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

import ca.ualberta.cs.technologic.ElasticSearchUser;
import ca.ualberta.cs.technologic.R;
import ca.ualberta.cs.technologic.User;

/**
 * Created by gknoblau on 2016-02-16.
 */
public class LoginActivity extends Activity{
    private static final String FILENAME = "users.sav";
    private ArrayList<User> users;

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
                Boolean usernameValid = userLookup(username.getText().toString());
                //Boolean passwordValid = passwordLookup(password.getText().toString());
                //usernameValid = true;
                //passwordValid = true;
                if (usernameValid) {
                    Intent intent = new Intent(LoginActivity.this, HomePage.class);
                    intent.putExtra("USERNAME", username.toString());
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Wrong Username or password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        newUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NewUser.class);
                startActivity(intent);
            }
        });
    }

    public Boolean userLookup(final String username) {
        users = new ArrayList<User>();
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

    /*
    public Boolean passwordLookup(String password) {
        loadFromFile();
        for(int i=0; i < users.size(); i++) {
            if(users.get(i).getPassword() == password){
                return true;
            }
        }
        return false;
    }
    */
    /**
     * Load the array from the file
     */
    /*
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            users = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            users = new ArrayList<User>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
    */

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

}
