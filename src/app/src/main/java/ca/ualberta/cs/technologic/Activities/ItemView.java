package ca.ualberta.cs.technologic.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

import ca.ualberta.cs.technologic.Bid;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class ItemView extends ActionBarActivity {

    private String id;
    private Computer comp;
    private CurrentUser cu = CurrentUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Button placebid = (Button) findViewById(R.id.placebid);
        TextView username = (TextView) findViewById(R.id.infoUsername);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        TextView lblID = (TextView)findViewById(R.id.lblId);
        lblID.setText("ID: " + id);

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Bid has been placed on computer.");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

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

        setComputerValues(comp);

        username.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: go to the user's page when clicked
            }
        });

        placebid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeBid();
                AlertDialog alertBidPlaced = builder1.create();
                alertBidPlaced.show();
            }
        });

    }

    private void placeBid() {

        Float price = Float.parseFloat(((EditText) findViewById(R.id.infoBid)).getText().toString());
        String username = cu.getCurrentUser();

        final Bid bid;
        try {
            bid = new Bid(comp.getId(), price, username, comp.getUsername());

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ElasticSearchBidding.placeBid(bid);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Fill in all the fields of computer
    public void setComputerValues(Computer c) {
        ((TextView) findViewById(R.id.infoUsername)).setText(c.getUsername());
        ((TextView) findViewById(R.id.infoMake)).setText(c.getMake());
        ((TextView) findViewById(R.id.infoModel)).setText(c.getModel());
        ((TextView) findViewById(R.id.infoYear)).setText(c.getYear().toString());
        ((TextView) findViewById(R.id.infoProcessor)).setText(c.getProcessor());
        ((TextView) findViewById(R.id.infoMemory)).setText(c.getRam().toString());
        ((TextView) findViewById(R.id.infoHarddrive)).setText(c.getHardDrive().toString());
        ((TextView) findViewById(R.id.infoOs)).setText(c.getOs());
        ((TextView) findViewById(R.id.infoBaserate)).setText(c.getPrice().toString());
        ((TextView) findViewById(R.id.infoDescription)).setText(c.getDescription());
        ((TextView) findViewById(R.id.infoMake)).setText(c.getMake());

    }

}


