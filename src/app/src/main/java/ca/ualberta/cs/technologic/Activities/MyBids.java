package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.Bid;
import ca.ualberta.cs.technologic.BidAdapter;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.R;

public class MyBids extends ActionBarActivity {
    private ArrayList<Bid> bids;
    private CurrentUser cu = CurrentUser.getInstance();
    private ListView bidslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids);
        bidslist = (ListView) findViewById(R.id.bidslist);

//        bidslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Intent goToInfo = new Intent(MyBids.this, EditComputerInfo.class);
////                startActivity(goToInfo);
//            }
//        });
    }

    @Override
    protected void onStart() {
        BidAdapter listAdapter;
        // TODO Auto-generated method stub
        super.onStart();

        //get all bids that the current user has made on other computers
        Thread thread = new Thread(new Runnable() {
            public void run() {
                bids = ElasticSearchBidding.getMyBids(cu.getCurrentUser());
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        ArrayAdapter<Bid> listAdapter = new ArrayAdapter<Bid>(this, R.layout.listviewtext, bids);
//        bidslist.setAdapter(listAdapter);
        listAdapter = new BidAdapter(this, bids, true);
        bidslist.setAdapter(listAdapter);

        if (bids.size() == 0){
            Toast bidMsg = Toast.makeText(getApplicationContext(), "You have made no bids", Toast.LENGTH_SHORT);
            bidMsg.show();
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
