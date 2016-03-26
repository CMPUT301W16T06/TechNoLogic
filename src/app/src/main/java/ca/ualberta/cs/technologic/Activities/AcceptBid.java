package ca.ualberta.cs.technologic.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import ca.ualberta.cs.technologic.Bid;
import ca.ualberta.cs.technologic.BidAdapter;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.R;

public class AcceptBid extends ActionBarActivity {
    private ArrayList<Bid> bids = null;
    private String compID;
    private UUID bidID;
    private Bid selectedBid;
    private boolean selected = false;
    private ListView bidslist;
    private BidAdapter listAdapter;
    private Double longitude;
    private Double latitude;
    private Boolean done = Boolean.FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_bids);
        bidslist = (ListView) findViewById(R.id.acceptbidslist);
        Button acceptBtn = (Button) findViewById(R.id.accept);
        Button declineBtn = (Button) findViewById(R.id.decline);
        Intent intent = getIntent();
        compID = intent.getStringExtra("id");

        //when accept button is pressed, bid is converted to borrowed
        //all other bids must be rejected and status of computer changes
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if bid is selected
                if (selected){
                    getLocation();
                } else {
                    Toast noBidSelected = Toast.makeText(getApplicationContext(), "You must select a bid!", Toast.LENGTH_SHORT);
                    noBidSelected.show();
                }
            }
        });

        //decline button is pressed
        //the bid is declined, check if there are any other bids
        //if no other bid, then status is changed back to available
        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if an entry(bid) is selected
                if (selected) {
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            ElasticSearchBidding.declineBid(selectedBid, bids.size());
                        }
                    });
                    thread.start();

                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast bidDeclined = Toast.makeText(getApplicationContext(), "Bid has been declined.", Toast.LENGTH_SHORT);
                    bidDeclined.show();

                    bids.remove(selectedBid);
                    listAdapter.notifyDataSetChanged();
                }
            }
        });

        //selects the selected bid as the bid for action
        bidslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = true;
                selectedBid = (Bid) parent.getAdapter().getItem(position);
                bidID = selectedBid.getBidID();
            }
        });

    }
    private void getLocation() {

        //http://developer.android.com/guide/topics/ui/dialogs.html
        //http://stackoverflow.com/questions/12799751/android-how-do-i-retrieve-edittext-gettext-in-custom-alertdialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater linf = LayoutInflater.from(this);
        final View inflator = linf.inflate(R.layout.specifylocation, null);
        builder.setView(inflator);

        builder.setTitle("Pickup Location");
        builder.setMessage("Specify Location for Pickup:");



        // Add the buttons
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText locationEdit = (EditText) inflator.findViewById(R.id.location);
                String location = locationEdit.getText().toString();
                getCoordinates(location);

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void getCoordinates(String location) {
        //http://developer.android.com/reference/android/location/Geocoder.html
        //http://stackoverflow.com/questions/3641304/get-latitude-and-longitude-using-zipcode
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(location, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
                longitude = address.getLongitude();
                latitude = address.getLatitude();
                //String message = String.format("Latitude: %f, Longitude: %f",
                //        latitude, longitude);
                //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                sendBid();
            } else {
                // Display appropriate message when Geocoder services are not available
                Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void sendBid() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                ElasticSearchBidding.acceptBid(selectedBid, bids, longitude, latitude);
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast bidAccepted = Toast.makeText(getApplicationContext(), "Bid has been accepted!", Toast.LENGTH_SHORT);
        bidAccepted.show();
        bids.clear();
        listAdapter.notifyDataSetChanged();
        //onBackPressed();

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        //gets all bids for the current computer
        Thread thread = new Thread(new Runnable() {
            public void run() {
                bids = ElasticSearchBidding.getAllBids(UUID.fromString(compID));
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //listAdapter = new ArrayAdapter<Bid>(this, R.layout.listviewtext, bids);
        listAdapter = new BidAdapter(this, bids, false);
        bidslist.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
