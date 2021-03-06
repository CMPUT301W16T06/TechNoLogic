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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.ComputerAdapter;
import ca.ualberta.cs.technologic.CurrentComputers;
import ca.ualberta.cs.technologic.CurrentOffline;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.OfflineMode;
import ca.ualberta.cs.technologic.R;

/**
 * Homepage where the user can search for computers and view all availbe and bidded on computers
 * Borrowed computers and computers the user owns will not show up here
 */

public class HomePage extends ActionBarActivity {

    //singletons
    private CurrentUser cu = CurrentUser.getInstance();
    private CurrentOffline co = CurrentOffline.getInstance();
    private CurrentComputers cc = CurrentComputers.getInstance();

    //variables
    private ArrayList<Computer> comps = null;
    private String username = cu.toString();
    private Integer notificationCount = 0;

    //UI elements
    private ListView itemslist;
    private EditText search;


    protected void onCreate(Bundle savedInstanceState) {
        Button go;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        itemslist = (ListView) findViewById(R.id.homelist);
        go = (Button) findViewById(R.id.go);
        search = (EditText) findViewById(R.id.search);

        itemslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Computer entry = (Computer) parent.getAdapter().getItem(position);
                Intent goToInfo = new Intent(HomePage.this, ViewComputer.class);
                goToInfo.putExtra("id", entry.getId().toString());
                startActivity(goToInfo);
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //scriptInput.getText();
                getComputersSearch();
            }
        });

    }

    /**
     * Loads computers into listview
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        //check the connectivity, if connected to internet then check if there
        //are computers to add save to the database
        checkCompsToSave();

        //check if there are any new bids and notify
        getNotificaitons();

        //update computer singleton
        //get changes done by external users, ie. bidding
        getComputerSingleton();

        getComputers();
        ComputerAdapter listAdapter = new ComputerAdapter(this, comps);
        itemslist.setAdapter(listAdapter);
    }

    /**
     * Gets all computers from the DB
     * @see ElasticSearchComputer
    */
    private void getComputers(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                comps = ElasticSearchComputer.getAllComputers();
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
     * Gets computers from search from DB
     */
    private void getComputersSearch(){
        //Toast toast1 = Toast.makeText(getApplicationContext(), search.getText().toString(), Toast.LENGTH_SHORT);
        //toast1.show();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                comps = ElasticSearchComputer.getComputersSearch(search.getText().toString());
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Toast toast = Toast.makeText(getApplicationContext(), comps.toString(), Toast.LENGTH_SHORT);
        //toast.show();
        ComputerAdapter listAdapter = new ComputerAdapter(this, comps);
        itemslist.setAdapter(listAdapter);

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
                    "You have "+notificationCount.toString()+" new bid(s)!", Toast.LENGTH_SHORT);
            notify.show();
            notificationCount = 0;
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
                Intent intent0 = new Intent(this, Maps.class);
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

