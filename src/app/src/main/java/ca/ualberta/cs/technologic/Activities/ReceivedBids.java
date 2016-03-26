package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.Bid;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.ComputerAdapter;
import ca.ualberta.cs.technologic.CurrentBids;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.R;

public class ReceivedBids extends ActionBarActivity {
    private ArrayList<Bid> bids;
    private ArrayList<Computer> comps;
    final private CurrentUser cu = CurrentUser.getInstance();
    private CurrentBids cb = CurrentBids.getInstance();
    private ListView myitemlist;
    ComputerAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_item_bids);
        myitemlist = (ListView) findViewById(R.id.myitemsbidlist);

        //when an computer is selected then user will be brought to a
        //different activity to see all bids for tha computer
        myitemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Computer entry = (Computer) parent.getAdapter().getItem(position);
                Intent goToBids = new Intent(ReceivedBids.this, AcceptBid.class);
                goToBids.putExtra("id", entry.getId().toString());
                startActivity(goToBids);
                listAdapter.notifyDataSetChanged();

            }
        });

        listAdapter = new ComputerAdapter(this, cb.getCurrentBids());
        myitemlist.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        //ComputerAdapter listAdapter;
        super.onStart();
        listAdapter.notifyDataSetChanged();
        //gets all computer that user owns that have been bid on
        //getMyItems();

//        listAdapter = new ComputerAdapter(this, comps);
//        myitemlist.setAdapter(listAdapter);

        if (cb.getCurrentBids().size() == 0){
            Toast msg = Toast.makeText(getApplicationContext(), "No bids on your computers", Toast.LENGTH_SHORT);
            msg.show();
        }
    }

    /**
     * gets all computers that the user owns that have
     * been bid on by other users
     */
    private void getMyItems(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                comps = ElasticSearchBidding.getMyItemBids(cu.getCurrentUser());
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                finish();
                break;
            case R.id.myitems:
                startActivity(new Intent(this, MyComputers.class));
                finish();
                break;
            case R.id.accountsettings:
                startActivity(new Intent(this, EditUser.class));
                finish();
                break;
            case R.id.logout:
                startActivity(new Intent(this, Login.class));
                finish();
                break;
            case R.id.mybids:
                startActivity(new Intent(this, MyBids.class));
                finish();
                break;
            case R.id.myborrows:
                startActivity(new Intent(this, MyBorrows.class));
                finish();
                break;
            case R.id.lentout:
                startActivity(new Intent(this, LentOut.class));
                finish();
                break;
            case R.id.myitembids:
                startActivity(new Intent(this, ReceivedBids.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
