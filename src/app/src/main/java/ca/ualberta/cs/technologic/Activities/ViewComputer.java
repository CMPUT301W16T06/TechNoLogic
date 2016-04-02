package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import ca.ualberta.cs.technologic.Bid;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentComputers;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class ViewComputer extends ActionBarActivity {

    private String id;
    private Computer comp;
    private CurrentUser cu = CurrentUser.getInstance();
    private CurrentComputers cc = CurrentComputers.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Button placebid = (Button) findViewById(R.id.placebid);
        TextView username = (TextView) findViewById(R.id.infoUsername);
        ImageView image = (ImageView) findViewById(R.id.image);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        TextView lblID = (TextView)findViewById(R.id.lblId);
        lblID.setText("ID: " + id);

        //gets the computer information to display
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

        //binds the computer values to UI objects
        setComputerValues(comp);
        image.setImageBitmap(comp.getThumbnail());

        username.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: go to the user's page when clicked
            }
        });

        //when place bid button is pressed the bid is made on the current computer
        placebid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeBid();
                Toast bidplaced = Toast.makeText(getApplicationContext(),
                        "Your bid has been placed", Toast.LENGTH_SHORT);
                bidplaced.show();
            }
        });

    }

    /**
     * bid is made on the current computer object
     */
    private void placeBid() {
        //get the inputted bid price
        Float price = Float.parseFloat(((EditText) findViewById(R.id.infoBid)).getText().toString());
        String username = cu.getCurrentUser();

        final Bid bid;
        //save the bid that was made
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

    /**
     * bind computer vlaues to the UI
     * @param c the computer
     */
    private void setComputerValues(Computer c) {
        ((TextView) findViewById(R.id.infoUsername)).setText(c.getUsername());
        ((TextView) findViewById(R.id.infoMake)).setText(c.getMake());
        ((TextView) findViewById(R.id.infoModel)).setText(c.getModel());
        ((TextView) findViewById(R.id.infoYear)).setText(c.getYear());
        ((TextView) findViewById(R.id.infoProcessor)).setText(c.getProcessor());
        ((TextView) findViewById(R.id.infoMemory)).setText(c.getRam());
        ((TextView) findViewById(R.id.infoHarddrive)).setText(c.getHardDrive());
        ((TextView) findViewById(R.id.infoOs)).setText(c.getOs());
        ((TextView) findViewById(R.id.infoBaserate)).setText(String.format("%.2f", c.getPrice()));
        ((TextView) findViewById(R.id.infoDescription)).setText(c.getDescription());
        ((TextView) findViewById(R.id.infoMake)).setText(c.getMake());

    }

}


