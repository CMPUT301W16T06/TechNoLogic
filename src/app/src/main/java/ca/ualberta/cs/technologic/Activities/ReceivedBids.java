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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import ca.ualberta.cs.technologic.Bid;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.ComputerAdapter;
import ca.ualberta.cs.technologic.CurrentBids;
import ca.ualberta.cs.technologic.CurrentOffline;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.OfflineMode;
import ca.ualberta.cs.technologic.R;

/**
 * View the details of a computer and its status
 * You can also click on the owner name and you can view the owner
 */

public class ReceivedBids extends ActionBarActivity {
    private ArrayList<Bid> bids;
    private ArrayList<Computer> comps;
    final private CurrentUser cu = CurrentUser.getInstance();
    private CurrentOffline co = CurrentOffline.getInstance();
    private CurrentBids cb = CurrentBids.getInstance();
    private ListView myitemlist;
    ComputerAdapter listAdapter;
    private Integer notificationCount = 0;


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

        //check the connectivity and if there are computers to save
        checkCompsToSave();

        //check if there are new bids and notify
        getNotificaitons();

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

    /**
     * checks the connectivity to the internet
     * if connected to the internet then checks if there are computers to add
     * to the database
     */
    private void checkCompsToSave(){
        boolean connection = OfflineMode.getEnabled(this);
        if (connection) {
            //final ArrayList<Computer> fileComps = loadComputersFile();
            final ArrayList<Computer> fileComps = co.getCurrentOffline();
            if (fileComps.size() > 0) {
                try {
                    final Thread thread = new Thread(new Runnable() {
                        public void run() {
                            for (final Computer cFile : fileComps) {
                                ElasticSearchComputer.addComputer(cFile);
                            }
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
                fileComps.clear();

                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            FileOutputStream fos = openFileOutput("computers.sav", 0);
                            co.saveCurrentOffline(fos);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * check if there are any new bids for the current user
     * display notification if there are new bids
     */
    private void getNotificaitons(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                notificationCount = ElasticSearchBidding.getNotifications(cu.getCurrentUser());
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (notificationCount > 0){
            Toast notify = Toast.makeText(getApplicationContext(),
                    "You have " + notificationCount.toString() + " new bid(s)!", Toast.LENGTH_SHORT);
            notify.show();
            notificationCount = 0;
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