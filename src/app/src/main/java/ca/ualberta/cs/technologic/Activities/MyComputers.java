package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import ca.ualberta.cs.technologic.CurrentOffline;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.OfflineMode;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.ComputerAdapter;
import ca.ualberta.cs.technologic.CurrentComputers;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

/**
 * Shows a list of computers that you own and their status, availbie bidded or borrowed
 * The user can also add a new computer from this page
 */

public class MyComputers extends ActionBarActivity {
    //singleton
    private CurrentUser cu = CurrentUser.getInstance();
    private CurrentComputers currentComps = CurrentComputers.getInstance();
    private CurrentOffline co = CurrentOffline.getInstance();

    //variables
    private ArrayList<Computer> comps = new ArrayList<Computer>();
    private ArrayList<Computer> compsTemp = new ArrayList<Computer>();
    private Integer notificationCount = 0;

    //UI elements
    private ComputerAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView myitemslist;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_items);
        Button addNewItem = (Button) findViewById(R.id.addnewitem);
        myitemslist = (ListView) findViewById(R.id.myitemslist);

        //when add button clicked bring up activity to add a new computer
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddItems = new Intent(MyComputers.this, AddComputer.class);
                startActivity(goToAddItems);
            }
        });

        //when an entry from the list of items selected, bring up that computer information
        myitemslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Computer entry = (Computer) parent.getAdapter().getItem(position);
                Intent goToInfo = new Intent(MyComputers.this, EditComputerInfo.class);
                goToInfo.putExtra("id", entry.getId().toString());
                startActivity(goToInfo);
            }
        });

        listAdapter = new ComputerAdapter(this, currentComps.getCurrentComputers());
        myitemslist.setAdapter(listAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

        //check the connectivity and if there are computers to save
        checkCompsToSave();

        //check if there are new bids and notify
        getNotificaitons();

        //makes sure the list view is up to date
        listAdapter.notifyDataSetChanged();


        if (currentComps.getCurrentComputers().size() == 0){
            Toast myitems = Toast.makeText(getApplicationContext(), "You have no Computers", Toast.LENGTH_SHORT);
            myitems.show();
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