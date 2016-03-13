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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.ComputerAdapter;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class MyItems extends ActionBarActivity {

    private ArrayList<Computer> comps = null;
    private CurrentUser cu = CurrentUser.getInstance();
    ListView myitemslist;
    ComputerAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_items);
        Button addNewItem = (Button) findViewById(R.id.addnewitem);
        myitemslist = (ListView) findViewById(R.id.myitemslist);

        //when add button clicked bring up activity to add a new computer
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddItems = new Intent(MyItems.this, AddItems.class);
                startActivity(goToAddItems);
                onBackPressed();
            }
        });

        //when an entry from the list of items selected, bring up that computer information
        myitemslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Computer entry = (Computer) parent.getAdapter().getItem(position);
                Intent goToInfo = new Intent(MyItems.this, ItemInfo.class);
                goToInfo.putExtra("id", entry.getId().toString());
                startActivity(goToInfo);
                onBackPressed();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //get all computer belonging to this user
        getComputers();
        listAdapter = new ComputerAdapter(this, comps);
        myitemslist.setAdapter(listAdapter);

        if (comps.size() == 0){
            Toast myitems = Toast.makeText(getApplicationContext(), "You have no Computers", Toast.LENGTH_SHORT);
            myitems.show();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        getComputers();
        ComputerAdapter listAdapter = new ComputerAdapter(this, comps);
        myitemslist.setAdapter(listAdapter);
    }

    /**
     *     gets all computers belonging to the current logged in user
     */
    public void getComputers(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                comps = ElasticSearchComputer.getComputers(cu.getCurrentUser());
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
