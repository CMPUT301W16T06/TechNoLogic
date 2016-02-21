package ca.ualberta.cs.technologic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.security.KeyStore;
import java.util.ArrayList;

/**
 * Created by gknoblau on 2016-02-16.
 */
public class LoginActivity extends Activity{
    private static final String FILENAME = "users.sav";
    private ArrayList<User> users = new ArrayList<User>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.login);
        Button newUserButton = (Button) findViewById(R.id.newUser);

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Boolean usernameValid = userLookup(username.getText().toString());
                Boolean passwordValid = passwordLookup(password.getText().toString());
                if ((usernameValid || passwordValid) == true) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", username.toString());
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Wrong Username or password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        newUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(LoginActivity.this, newuser.class);
                //startActivity(intent);
            }
        });
    }

    public Boolean userLookup(String username) {
        loadFromFile();
        for(int i=0; i < users.size(); i++) {
            if(users.get(i).getUsername() == username){
                return true;
            }
        }
        return false;
    }
    public Boolean passwordLookup(String password) {
        loadFromFile();
        for(int i=0; i < users.size(); i++) {
            if(users.get(i).getPassword() == password){
                return true;
            }
        }
        return false;
    }
    /**
     * Load the array from the file
     */
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


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

}