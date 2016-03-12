package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import ca.ualberta.cs.technologic.Bid;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.R;

public class MyItemBids extends ActionBarActivity {
    private ArrayList<Bid> bids;
    private CurrentUser cu = CurrentUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_item_bids);
        ListView myitemlist = (ListView) findViewById(R.id.myitemsbidlist);

        myitemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent goToBids = new Intent(MyItemBids.this, AcceptBid.class);
//                startActivity(goToBids);
            }
        });

//        //This is just testing... DELETE LATER
//        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
//                "Jupiter", "Saturn", "Uranus", "Neptune"};
//        ArrayList<String> planetList = new ArrayList<String>();
//        planetList.addAll(Arrays.asList(planets));
//        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.listviewtext, planetList);
//        myitemlist.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                bids = ElasticSearchBidding.getMyItemBids(cu.getCurrentUser());
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListView myitemlist = (ListView) findViewById(R.id.myitemsbidlist);
        ArrayAdapter<Bid> listAdapter = new ArrayAdapter<Bid>(this, R.layout.listviewtext, bids);
        myitemlist.setAdapter(listAdapter);

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
