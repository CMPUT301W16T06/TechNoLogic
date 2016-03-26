package ca.ualberta.cs.technologic.Activities;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class AddComputer extends ActionBarActivity {
    final private CurrentUser cu = CurrentUser.getInstance();
    static final int REQUEST_IMAGE_CAPTURE = 1234;
    private Bitmap thumbnail;
    private ImageButton pictureBtn;

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
    }

    /**
     * save the new computer
     * needs to retrieve all values from the UI
     */
    private void saveComputer(){
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


        final Computer computer;
        try {
            computer = new Computer(username,make, model, year, processor, ram,
                    hardDrive, os, price, description, thumbnail);
            computer.addThumbnail(thumbnail);

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

}
