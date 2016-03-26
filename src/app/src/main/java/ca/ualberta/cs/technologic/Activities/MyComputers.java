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

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.ComputerAdapter;
import ca.ualberta.cs.technologic.CurrentComputers;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class MyComputers extends ActionBarActivity {

    private ArrayList<Computer> comps = new ArrayList<Computer>();
    private ArrayList<Computer> compsTemp = new ArrayList<Computer>();
    private CurrentUser cu = CurrentUser.getInstance();
    private CurrentComputers currentComps = CurrentComputers.getInstance();
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

        listAdapter.notifyDataSetChanged();
        //get all computer belonging to this user
        //getComputers();

        if (currentComps.getCurrentComputers().size() == 0){
            Toast myitems = Toast.makeText(getApplicationContext(), "You have no Computers", Toast.LENGTH_SHORT);
            myitems.show();
        }

    }

//    @Override
//    protected void onResume(){
//        super.onResume();
//        getComputers();
//    }

    /**
     * gets all computers belonging to the current logged in user
     */
    private void getComputers(){
        compsTemp.clear();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                compsTemp = ElasticSearchComputer.getComputers(cu.getCurrentUser());
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        comps.clear();
        comps.addAll(compsTemp);
        listAdapter.notifyDataSetChanged();
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
                finish();
                break;
            case R.id.myitems:
                startActivity(new Intent(this, MyComputers.class));
                finish();
                break;
            case R.id.accountsettings:
                startActivity(new Intent(this, EditUser.class));
                finish();
                break;
            case R.id.logout:
                startActivity(new Intent(this, Login.class));
                finish();
                break;
            case R.id.mybids:
                startActivity(new Intent(this, MyBids.class));
                finish();
                break;
            case R.id.myborrows:
                startActivity(new Intent(this, MyBorrows.class));
                finish();
                break;
            case R.id.lentout:
                startActivity(new Intent(this, LentOut.class));
                finish();
                break;
            case R.id.myitembids:
                startActivity(new Intent(this, ReceivedBids.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
