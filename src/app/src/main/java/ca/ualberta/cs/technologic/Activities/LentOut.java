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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.ualberta.cs.technologic.Borrow;
import ca.ualberta.cs.technologic.BorrowAdapter;
import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchBorrowing;
import ca.ualberta.cs.technologic.R;

public class LentOut extends ActionBarActivity {
    private ArrayList<Borrow> lentOut;
    private ArrayList<Computer> comps;
    private CurrentUser cu = CurrentUser.getInstance();
    //ComputerAdapter listAdapter;
    ListView lentlist;
    BorrowAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lent_out);
        lentlist = (ListView) findViewById(R.id.lentlist);

//        lentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent goToInfo = new Intent(LentOut.this, ItemInfo.class);
//                startActivity(goToInfo);
//            }
//        });

        //This is just testing... DELETE LATER
//       String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
//                "Jupiter", "Saturn", "Uranus", "Neptune"};
//        String[] planets = new String[] {"Coming Soon!"};
//        ArrayList<String> planetList = new ArrayList<String>();
//        planetList.addAll(Arrays.asList(planets));
//        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.listviewtext, planetList);
//        lentlist.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //gets all computers that user has lent out to other users
        getLentOut();

        //ArrayAdapter<Borrow> listAdapter = new ArrayAdapter<Borrow>(this, R.layout.listviewtext, lentOut);
        listAdapter = new BorrowAdapter(this, lentOut, false);
        lentlist.setAdapter(listAdapter);

        if (lentOut.size() == 0){
            Toast msg = Toast.makeText(getApplicationContext(), "You have not lent out any computers", Toast.LENGTH_SHORT);
            msg.show();
        }
    }

    /**
     * gets all computers that the user owns that have
     * been lent out to other users
     */
    public void getLentOut(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                lentOut = ElasticSearchBorrowing.getLentOut(cu.getCurrentUser());
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
                startActivity(new Intent(this, MyItems.class));
                break;
            case R.id.accountsettings:
                startActivity(new Intent(this, EditUser.class));
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
