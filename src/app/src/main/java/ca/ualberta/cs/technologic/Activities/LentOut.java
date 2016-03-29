package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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
    private ListView lentlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lent_out);
        lentlist = (ListView) findViewById(R.id.lentlist);

    }

    @Override
    protected void onStart() {
        BorrowAdapter listAdapter;
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
    private void getLentOut(){
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