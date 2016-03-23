package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.ComputerAdapter;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class HomePage extends ActionBarActivity {

    private ArrayList<Computer> comps = null;
    private CurrentUser cu = CurrentUser.getInstance();
    String username = cu.toString();
    ListView itemslist;
    Button go;
    EditText search;

    /**
     * Set up on click listeners for itemlist click and go click
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        itemslist = (ListView) findViewById(R.id.homelist);
        go = (Button) findViewById(R.id.go);
        search = (EditText) findViewById(R.id.search);

        itemslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Computer entry = (Computer) parent.getAdapter().getItem(position);
                Intent goToInfo = new Intent(HomePage.this, ItemView.class);
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

        getComputers();
        ComputerAdapter listAdapter = new ComputerAdapter(this, comps);
        itemslist.setAdapter(listAdapter);
    }

    /**
     * Gets all computers from the DB
     * @see ElasticSearchComputer
    */
    public void getComputers(){
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
    public void getComputersSearch(){
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
                //I used this for testing to make sure the username could be accessed from anywhere.
                //Toast toast = Toast.makeText(getApplicationContext(), cu.getCurrentUser(), Toast.LENGTH_SHORT);
                //toast.show();
                startActivity(new Intent(this, HomePage.class));
                break;
            case R.id.myitems:
                startActivity(new Intent(this, MyItems.class));
                break;
            case R.id.accountsettings:
                startActivity((new Intent(this, EditUser.class)));
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

