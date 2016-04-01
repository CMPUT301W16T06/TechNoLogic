package ca.ualberta.cs.technologic.Activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentComputers;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class EditComputerInfo extends ActionBarActivity {
    private String id;
    private Computer comp;
    private CurrentUser cu = CurrentUser.getInstance();
    private CurrentComputers currentComputers = CurrentComputers.getInstance();
    private Bitmap thumbnail = null;
    private ImageButton pictureBtn;
    static final int REQUEST_IMAGE_CAPTURE = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        Button deleteBtn = (Button) findViewById(R.id.btnDelete);
        Button updateBtn = (Button) findViewById(R.id.btnUpdate);
        pictureBtn = (ImageButton) findViewById(R.id.pictureBtn);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        TextView lblID = (TextView)findViewById(R.id.lblId);
        lblID.setText("ID: " + id);

        //gets the selected informaiton of the selected computer
        Thread thread = new Thread(new Runnable() {
            public void run() {
                comp = ElasticSearchComputer.getComputersById(UUID.fromString(id));
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //links the vlaues of the computer to the UI
        setComputerValues(comp);
        thumbnail = comp.getThumbnail();
        pictureBtn.setImageBitmap(thumbnail);


        pictureBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (null != thumbnail) {
                    popUp();
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });

        //delete the selected computer
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteComputer();
                //Intent goToItems3 = new Intent(EditComputerInfo.this, HomePage.class);
                //startActivity(goToItems3);
                onBackPressed();
            }
        });

        //update the computer when button pressed
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateComputer();
                onBackPressed();
                //Toast toast1 = Toast.makeText(getApplicationContext(), "Computer has been updated", Toast.LENGTH_SHORT);
                //toast1.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * delete the computer
     */
    private void deleteComputer() {
        //first check the status of the computer
        //will not delete a computer if it is bidded or borrowed
        if (comp.getStatus().equals("available")) {
            currentComputers.deleteCurrentComputer(comp);
            try {
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        ElasticSearchComputer.deleteComputer(id);
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
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            Toast toast1 = Toast.makeText(getApplicationContext(), "Cannot delete computer," +
                    " status must be \"available\" to delete computer", Toast.LENGTH_SHORT);
            toast1.show();
        }
    }

    /**
     *    gets the new information when updating the
     *    and saved the information
     */
    private void updateComputer() {
        String make = ((EditText)findViewById(R.id.infoMake)).getText().toString();
        String model = ((EditText)findViewById(R.id.infoModel)).getText().toString();
        Integer year = Integer.parseInt(((EditText) findViewById(R.id.infoYear)).getText().toString());
        String processor= ((EditText)findViewById(R.id.infoProcessor)).getText().toString();
        Integer ram = Integer.parseInt(((EditText) findViewById(R.id.infoMemory)).getText().toString());
        Integer hardDrive = Integer.parseInt(((EditText) findViewById(R.id.infoHarddrive)).getText().toString());
        String os = ((EditText)findViewById(R.id.infoOs)).getText().toString();
        Float price = Float.parseFloat(((EditText) findViewById(R.id.infoBaserate)).getText().toString());
        String description = ((EditText)findViewById(R.id.infoDescription)).getText().toString();
        String status = comp.getStatus();
        String username = cu.getCurrentUser();

        Computer c = new Computer(comp.getId(), username, make, model, year, processor, ram,
                hardDrive, os, price, description, status, thumbnail);

        currentComputers.deleteCurrentComputer(comp);
        currentComputers.addCurrentComputer(c);


        final Computer computer;
        try {
            computer = new Computer(comp.getId(), username, make, model, year, processor, ram,
                    hardDrive, os, price, description, status, thumbnail);

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ElasticSearchComputer.updateComputer(computer);
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

    /**
     *     Fill in all the fields of computer
     *     on loading
     */
    private void setComputerValues(Computer c){
        ((TextView)findViewById(R.id.infoStatus)).setText(c.getStatus());
        ((TextView)findViewById(R.id.infoUsername)).setText(c.getUsername());
        ((EditText)findViewById(R.id.infoMake)).setText(c.getMake());
        ((EditText)findViewById(R.id.infoModel)).setText(c.getModel());
        ((EditText)findViewById(R.id.infoYear)).setText(c.getYear().toString());
        ((EditText)findViewById(R.id.infoProcessor)).setText(c.getProcessor());
        ((EditText)findViewById(R.id.infoMemory)).setText(c.getRam().toString());
        ((EditText)findViewById(R.id.infoHarddrive)).setText(c.getHardDrive().toString());
        ((EditText)findViewById(R.id.infoOs)).setText(c.getOs());
        ((EditText)findViewById(R.id.infoBaserate)).setText(String.format("%.2f", c.getPrice()));
        ((EditText)findViewById(R.id.infoDescription)).setText(c.getDescription());
        ((EditText)findViewById(R.id.infoMake)).setText(c.getMake());

        TextView status = (TextView) findViewById(R.id.infoStatus);

        if (c.getStatus().equals("available")){
            status.setTextColor(Color.parseColor("#008000"));
        }
        else if (c.getStatus().equals("bidded")){
            status.setTextColor(Color.parseColor("#ff8c00"));
        }
        else{
            status.setTextColor(Color.parseColor("#0077ea"));
        }

    }

    private void popUp() {

        //http://developer.android.com/guide/topics/ui/dialogs.html

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("What do you really wanna do?");
        //builder.setMessage("Specify Location for Pickup:");



        // Add the buttons
        builder.setPositiveButton("Take new photo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });
        builder.setNegativeButton("Delete Photo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                thumbnail = null;
                pictureBtn = (ImageButton) findViewById(R.id.pictureBtn);
                pictureBtn.setImageBitmap(thumbnail);

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

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

}