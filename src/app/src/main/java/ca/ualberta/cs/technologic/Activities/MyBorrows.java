package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;

import ca.ualberta.cs.technologic.Borrow;
import ca.ualberta.cs.technologic.BorrowAdapter;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentOffline;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBidding;
import ca.ualberta.cs.technologic.ElasticSearchBorrowing;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.OfflineMode;
import ca.ualberta.cs.technologic.R;

/**
 * Contains a list of computers that you are currently borrowing
 * You can also return a computer from this screen
 */

public class MyBorrows extends ActionBarActivity {
    private ArrayList<Borrow> borrows;
    private ArrayList<Computer> comps;
    private CurrentUser cu = CurrentUser.getInstance();
    private CurrentOffline co = CurrentOffline.getInstance();
    private ListView borrowlist;
    private BorrowAdapter listAdatper;
    boolean selected;
    private Borrow selectedBorrow;
    private Integer notificationCount = 0;

    private double[] location = new double[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrows);
        borrowlist = (ListView) findViewById(R.id.borrowlist);
        Button returnBtn = (Button) findViewById(R.id.returnComp);
        Button locationBtn = (Button) findViewById(R.id.location);


        //when accept button is pressed, bid is converted to borrowed
        //all other bids must be rejected and status of computer changes
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if bid is selected
                if (selected) {
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            ElasticSearchBorrowing.returnComputer(selectedBorrow);
                        }
                    });
                    thread.start();

                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    Toast bidAccepted = Toast.makeText(getApplicationContext(), "Bid has been accepted!", Toast.LENGTH_SHORT);
//                    bidAccepted.show();
                    borrows.remove(selectedBorrow);
                    listAdatper.notifyDataSetChanged();
                    //onBackPressed();
                }
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected) {
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            location = ElasticSearchBorrowing.getLocation(selectedBorrow.getBorrowID());
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent10 = new Intent(v.getContext(), Maps.class);
                intent10.putExtra("Location", location);
                startActivity(intent10);
            }
        });


        borrowlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getId() == R.id.owner) {
                    Intent intent = new Intent(view.getContext(), ViewUser.class);
                    String sellerName = ((TextView) findViewById(R.id.owner)).getText().toString();
                    intent.putExtra("username", sellerName);
                    startActivity(intent);
                }
                selected = true;
                selectedBorrow = (Borrow) parent.getAdapter().getItem(position);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //check connectivity and if there are computers to save
        checkCompsToSave();

        //check if there are new bids and notify
        getNotificaitons();

        //gets all computers that user is borrowing
        getMyBorrows();

        //ArrayAdapter<Borrow> listAdapter = new ArrayAdapter<Borrow>(this, R.layout.listviewtext, borrows);
        listAdatper = new BorrowAdapter(this, borrows, true);
        borrowlist.setAdapter(listAdatper);

        if (borrows.size() == 0){
            Toast msg = Toast.makeText(getApplicationContext(), "You are borrowing no computers", Toast.LENGTH_SHORT);
            msg.show();
        }
    }

    /**
     * gets all computers that the user is currenlty borrowing
     * from other user
     */
    private void getMyBorrows(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                borrows = ElasticSearchBorrowing.getMyBorrows(cu.getCurrentUser());
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