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
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.Borrow;
import ca.ualberta.cs.technologic.BorrowAdapter;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBorrowing;
import ca.ualberta.cs.technologic.R;

public class MyBorrows extends ActionBarActivity {
    private ArrayList<Borrow> borrows;
    private ArrayList<Computer> comps;
    private CurrentUser cu = CurrentUser.getInstance();
    private ListView borrowlist;
    private BorrowAdapter listAdatper;
    boolean selected;
    private Borrow selectedBorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrows);
        borrowlist = (ListView) findViewById(R.id.borrowlist);
        Button returnBtn = (Button) findViewById(R.id.returnComp);


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

        borrowlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = true;
                selectedBorrow = (Borrow) parent.getAdapter().getItem(position);
            }
        });

        //This is just testing... DELETE LATER
//        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
//                "Jupiter", "Saturn", "Uranus", "Neptune"};
//        String[] planets = new String[] {"Coming Soon!"};
//        ArrayList<String> planetList = new ArrayList<String>();
//        planetList.addAll(Arrays.asList(planets));
//        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.listviewtext, planetList);
//        borrowlist.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
