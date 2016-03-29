package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.ComputerAdapter;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class HomePage extends ActionBarActivity {

    private ArrayList<Computer> comps = null;
    private CurrentUser cu = CurrentUser.getInstance();
    private String username = cu.toString();
    private ListView itemslist;
    private EditText search;

    /**
     * Set up on click listeners for itemlist click and go click
     * @param savedInstanceState
     */
    @Override
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

