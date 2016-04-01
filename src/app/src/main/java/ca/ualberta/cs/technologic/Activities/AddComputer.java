package ca.ualberta.cs.technologic.Activities;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.technologic.CurrentOffline;
import ca.ualberta.cs.technologic.OfflineMode;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentComputers;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

/**
 * Allows user to enter all the information on a new computer
 * Press add to at the end to add the computer to the database
 * Will not accept strings in the place of ints
 */

public class AddComputer extends ActionBarActivity {
    final private CurrentUser cu = CurrentUser.getInstance();
    static final int REQUEST_IMAGE_CAPTURE = 1234;
    private Bitmap thumbnail = null;
    private ImageButton pictureBtn;
    private CurrentComputers currentComputers = CurrentComputers.getInstance();
    private boolean connection;
    private String fileName = "computers.sav";
    private CurrentOffline currentOffline = CurrentOffline.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        Button submitBtn = (Button) findViewById(R.id.submit);
        pictureBtn = (ImageButton) findViewById(R.id.pictureButton);

        pictureBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComputer();
                onBackPressed();
                //Intent goToItems1 = new Intent(AddComputer.this, HomePage.class);
                //startActivity(goToItems1);
            }
        });

        //checkCompsToSave();
    }

    /**
     * save the new computer
     * needs to retrieve all values from the UI
     */
    private void saveComputer(){

        connection = OfflineMode.getEnabled(this);

        UUID id = UUID.randomUUID();
        String make = ((EditText)findViewById(R.id.make)).getText().toString();
        String model = ((EditText)findViewById(R.id.model)).getText().toString();
        Integer year = Integer.parseInt(((EditText) findViewById(R.id.year)).getText().toString());
        String processor= ((EditText)findViewById(R.id.processor)).getText().toString();
        Integer ram = Integer.parseInt(((EditText) findViewById(R.id.memory)).getText().toString());
        Integer hardDrive = Integer.parseInt(((EditText) findViewById(R.id.harddrive)).getText().toString());
        String os = ((EditText)findViewById(R.id.os)).getText().toString();
        Float price = Float.parseFloat(((EditText) findViewById(R.id.baserate)).getText().toString());
        String description = ((EditText)findViewById(R.id.description)).getText().toString();
        String username = cu.getCurrentUser();

        Computer c = new Computer(id, username, make, model, year, processor, ram,
                hardDrive, os, price, description, "available", thumbnail);

        currentComputers.addCurrentComputer(c);


        if (connection) {
            final Computer computer;
            try {
                computer = new Computer(id, username, make, model, year, processor, ram,
                        hardDrive, os, price, description, "available", thumbnail);

                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        ElasticSearchComputer.addComputer(computer);
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
        } else {
            ArrayList<Computer> fileComps = loadComputersFile();
            fileComps.add(c);
            saveComputerFile(fileComps);

            //add to singleton too
            currentOffline.addCurrentOffline(c);
        }
    }

    //Took this from 301 Lab 10
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data .getExtras();
            thumbnail = (Bitmap) extras.get("data");
            pictureBtn.setImageBitmap(thumbnail);
        }
    }

    private void checkCompsToSave(){
        connection = OfflineMode.getEnabled(this);
        if (connection) {
            final ArrayList<Computer> fileComps = loadComputersFile();
            if (fileComps.size() > 0) {
                try {
                    final Thread thread = new Thread(new Runnable() {
                        public void run() {
                            for (final Computer cFile : fileComps) {
                                ElasticSearchComputer.addComputer(cFile);
                            }
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
                fileComps.clear();
                saveComputerFile(fileComps);
            }
        }
    }

    /**
     * Takes an array of computers from a file and saves to database
     * @param compsToSave computers to save
     */
    public void saveComputerFile(ArrayList<Computer> compsToSave){
        //try to save to file
        try {
            //create file stream to write to file, 0 for default write mode
            FileOutputStream fos = openFileOutput(fileName, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();

            //converts array of Fuel objects to Json to store in file
            //and writes to file
            gson.toJson(compsToSave, out);
            out.flush();
            fos.close();

            //catch errors and throws runtime exceptions
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    //Read the file and store the Computer objects into an Array and return that Array
    public ArrayList<Computer> loadComputersFile() {
        //computers from file will be stored in this array
        ArrayList<Computer> fileComputers;

        //try to read in fuel entries from file
        try {
            //create file stream to read a file
            FileInputStream fis = openFileInput(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            //Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html Jan-21-2016
            //convert the type stored in file to an array of Computer objects
            Type listType = new TypeToken<ArrayList<Computer>>() {
            }.getType();
            fileComputers = gson.fromJson(in, listType);

            //catching errors
            //if file does not exist then create and empty array
        } catch (Exception e){
            if (e.getClass() == FileNotFoundException.class){
                fileComputers = new ArrayList<Computer>();
            } else {
                throw new RuntimeException();
            }
        }

        //return the array of Fuel objects
        return fileComputers;
    }


}
