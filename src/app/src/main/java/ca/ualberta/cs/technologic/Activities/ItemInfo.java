package ca.ualberta.cs.technologic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class ItemInfo extends ActionBarActivity {
    private String id;
    private Computer comp;
    private CurrentUser cu = CurrentUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        Button deleteBtn = (Button) findViewById(R.id.btnDelete);
        Button updateBtn = (Button) findViewById(R.id.btnUpdate);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        TextView lblID = (TextView)findViewById(R.id.lblId);
        lblID.setText("ID: " + id);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                comp = ElasticSearchComputer.getComputersById(UUID.fromString(id));
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setComputerValues(comp);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteComputer();
                //Intent goToItems = new Intent(ItemInfo.this, MyItems.class);
                //startActivity(goToItems);
                onBackPressed();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateComputer();
//                Intent goToItems = new Intent(ItemInfo.this, MyItems.class);
//                startActivity(goToItems);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void deleteComputer() {
        try {

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ElasticSearchComputer.deleteComputer(id);
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
    }

    private void updateComputer() {
        String make = ((EditText)findViewById(R.id.infoMake)).getText().toString();
        String model = ((EditText)findViewById(R.id.infoModel)).getText().toString();
        Integer year = Integer.parseInt(((EditText) findViewById(R.id.infoYear)).getText().toString());
        String processor= ((EditText)findViewById(R.id.infoProcessor)).getText().toString();
        Integer ram = Integer.parseInt(((EditText) findViewById(R.id.infoMemory)).getText().toString());
        Integer hardDrive = Integer.parseInt(((EditText) findViewById(R.id.infoHarddrive)).getText().toString());
        String os = ((EditText)findViewById(R.id.infoOs)).getText().toString();
        Float price = Float.parseFloat(((EditText) findViewById(R.id.infoBaserate)).getText().toString());
        String description = ((EditText)findViewById(R.id.infoDescription)).getText().toString();
        String status = comp.getStatus();
        String username = cu.getCurrentUser();


        final Computer computer;
        try {
            computer = new Computer(comp.getId(), username, make, model, year, processor, ram,
                    hardDrive, os, price, description, status);

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ElasticSearchComputer.updateComputer(computer);
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

    }

    public void setComputerValues(Computer c){
        ((TextView)findViewById(R.id.infoStatus)).setText(c.getStatus());
        ((EditText)findViewById(R.id.infoMake)).setText(c.getMake());
        ((EditText)findViewById(R.id.infoModel)).setText(c.getModel());
        ((EditText)findViewById(R.id.infoYear)).setText(c.getYear().toString());
        ((EditText)findViewById(R.id.infoProcessor)).setText(c.getProcessor());
        ((EditText)findViewById(R.id.infoMemory)).setText(c.getRam().toString());
        ((EditText)findViewById(R.id.infoHarddrive)).setText(c.getHardDrive().toString());
        ((EditText)findViewById(R.id.infoOs)).setText(c.getOs());
        ((EditText)findViewById(R.id.infoBaserate)).setText(c.getPrice().toString());
        ((EditText)findViewById(R.id.infoDescription)).setText(c.getDescription());
        ((EditText)findViewById(R.id.infoMake)).setText(c.getMake());


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
