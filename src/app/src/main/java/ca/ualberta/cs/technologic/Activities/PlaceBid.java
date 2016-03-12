package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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

/**
 * Created by Jessica on 2016-03-12.
 */

public class PlaceBid extends ActionBarActivity {
    private String id;
    private Computer comp;
    private CurrentUser cu = CurrentUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_bid);
        Button bidBtn = (Button) findViewById(R.id.btnBid);
        TextView owner = (TextView)findViewById(R.id.info_Owner);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        TextView lblID = (TextView)findViewById(R.id.lbl_Id);
        lblID.setText("ID: " + id);

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

        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: display the owners profile
//                Intent goToItems = new Intent(PlaceBid.this, NewUser.class);
//                startActivity(goToItems);
            }
        });

        bidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeBid();
                //Intent goToItems = new Intent(PlaceBid.this, MyItems.class);
                //startActivity(goToItems);
                goBack(v);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void placeBid() {

        Float price = Float.parseFloat(((EditText) findViewById(R.id.info_bid)).getText().toString());
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

            // Everything is OK!
            setResult(RESULT_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setComputerValues(Computer c){
        ((TextView)findViewById(R.id.info_Make)).setText(c.getMake());
        ((TextView)findViewById(R.id.info_Model)).setText(c.getModel());
        ((TextView)findViewById(R.id.info_Year)).setText(c.getYear().toString());
        ((TextView)findViewById(R.id.info_Processor)).setText(c.getProcessor());
        ((TextView)findViewById(R.id.info_Memory)).setText(c.getRam().toString());
        ((TextView)findViewById(R.id.info_Harddrive)).setText(c.getHardDrive().toString());
        ((TextView)findViewById(R.id.info_Os)).setText(c.getOs());
        ((TextView)findViewById(R.id.info_Baserate)).setText(c.getPrice().toString());
        ((TextView)findViewById(R.id.info_Description)).setText(c.getDescription());
        ((TextView)findViewById(R.id.info_Make)).setText(c.getMake());
        ((TextView)findViewById(R.id.info_Owner)).setText(c.getUsername());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                startActivity(new Intent(this, HomePage.class));
                break;
            case R.id.myitems:
                startActivity(new Intent(this, MyItems.class));
                break;
            case R.id.accountsettings:
                startActivity(new Intent(this, NewUser.class));
                break;
            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.mybids:
                startActivity(new Intent(this, Bids.class));
                break;
            case R.id.myborrows:
                startActivity(new Intent(this, MyBorrows.class));
                break;
            case R.id.lentout:
                startActivity(new Intent(this, LentOut.class));
                break;
            case R.id.myitembids:
                startActivity(new Intent(this, MyItemBids.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //once bid is placed then return
    public void goBack(View view) {
        onBackPressed();
    }

}
