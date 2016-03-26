package ca.ualberta.cs.technologic.Activities;


import android.content.Intent;
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

import java.util.UUID;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class EditComputerInfo extends ActionBarActivity {
    private String id;
    private Computer comp;
    private CurrentUser cu = CurrentUser.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        Button deleteBtn = (Button) findViewById(R.id.btnDelete);
        Button updateBtn = (Button) findViewById(R.id.btnUpdate);
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
     *    gets the new infomation when updating the
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


        final Computer computer;
        try {
            computer = new Computer(comp.getId(), username, make, model, year, processor, ram,
                    hardDrive, os, price, description, status);

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
        ((EditText)findViewById(R.id.infoBaserate)).setText(String.format("%.2f",c.getPrice()));
        ((EditText)findViewById(R.id.infoDescription)).setText(c.getDescription());
        ((EditText)findViewById(R.id.infoMake)).setText(c.getMake());

        TextView status = (TextView) findViewById(R.id.infoStatus);

        if (c.getStatus().equals("available")){
            status.setTextColor(Color.parseColor("#3b5323"));
        }
        else if (c.getStatus().equals("bidded")){
            status.setTextColor(Color.parseColor("#e6e600"));
        }
        else{
            status.setTextColor(Color.parseColor("#b20000"));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                startActivity(new Intent(this, HomePage.class));
                break;
            case R.id.myitems:
                startActivity(new Intent(this, MyComputers.class));
                break;
            case R.id.accountsettings:
                startActivity(new Intent(this, EditUser.class));
                break;
            case R.id.logout:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.mybids:
                startActivity(new Intent(this, MyBids.class));
                break;
            case R.id.myborrows:
                startActivity(new Intent(this, MyBorrows.class));
                break;
            case R.id.lentout:
                startActivity(new Intent(this, LentOut.class));
                break;
            case R.id.myitembids:
                startActivity(new Intent(this, ReceivedBids.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
