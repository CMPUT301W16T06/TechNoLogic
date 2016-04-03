package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import ca.ualberta.cs.technologic.Bid;
import ca.ualberta.cs.technologic.BidAdapter;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentComputers;
import ca.ualberta.cs.technologic.CurrentOffline;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.OfflineMode;
import ca.ualberta.cs.technologic.R;

/**
 * Shows a list of items that you have bid on
 * you can click on an item and view its details
 */

public class MyBids extends ActionBarActivity {

    //singleton
    private CurrentUser cu = CurrentUser.getInstance();
    private CurrentOffline co = CurrentOffline.getInstance();
    private CurrentComputers cc = CurrentComputers.getInstance();

    //variables
    private ArrayList<Bid> bids;
    private Integer notificationCount = 0;

    //UI elements
    private ListView bidslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids);
        bidslist = (ListView) findViewById(R.id.bidslist);
    }

    @Override
    protected void onStart() {
        BidAdapter listAdapter;
        // TODO Auto-generated method stub
        super.onStart();

        //check connectivity and if there are computers to save
        checkCompsToSave();

        //check if there are new bids and notify
        getNotificaitons();

        //update computer singleton
        //get changes done by external users, ie. bidding
        getComputerSingleton();


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
        bidslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.getId() == R.id.owner) {
                    Intent intent = new Intent(view.getContext(), ViewUser.class);
                    String sellerName = ((TextView) findViewById(R.id.owner)).getText().toString();
                    intent.putExtra("username", sellerName);
                    startActivity(intent);
                }
            }
        });
        if (bids.size() == 0){
            Toast bidMsg = Toast.makeText(getApplicationContext(), "You have made no bids", Toast.LENGTH_SHORT);
            bidMsg.show();
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

    /**
     * gets the computers for the computer singleton
     */
    private void getComputerSingleton() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                cc.setCurrentComputers(ElasticSearchComputer.getComputers(cu.getCurrentUser()));
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