package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import ca.ualberta.cs.technologic.Bid;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.ComputerAdapter;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.R;

public class AcceptBid extends ActionBarActivity {
    private ArrayList<Bid> bids = null;
    private CurrentUser cu = CurrentUser.getInstance();
    private String compID;
    private UUID bidID;
    private Bid selectedBid;
    private boolean selected = false;
    private ArrayAdapter<Bid> listAdapter;
    private ListView bidslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_bids);
        bidslist = (ListView) findViewById(R.id.acceptbidslist);
        Button acceptBtn = (Button) findViewById(R.id.accept);
        Button declineBtn = (Button) findViewById(R.id.decline);
        Intent intent = getIntent();
        compID = intent.getStringExtra("id");

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected) {
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            ElasticSearchBidding.acceptBid(selectedBid, bids);
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
            }
        });

        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//
            }
        });

        bidslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = true;
                selectedBid = (Bid) parent.getAdapter().getItem(position);
                bidID = selectedBid.getBidID();
//                Intent goToInfo = new Intent(AcceptBid.this, ItemInfo.class);
//                startActivity(goToInfo);
            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

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

        listAdapter = new ArrayAdapter<Bid>(this, R.layout.listviewtext, bids);
        bidslist.setAdapter(listAdapter);
//        ListView myitemlist = (ListView) findViewById(R.id.myitemsbidlist);
//        ComputerAdapter listAdapter = new ComputerAdapter(this, comps);
//        myitemlist.setAdapter(listAdapter);

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

}
