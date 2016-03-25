package ca.ualberta.cs.technologic.Activities;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.ualberta.cs.technologic.Computer;
import ca.ualberta.cs.technologic.CurrentUser;
import ca.ualberta.cs.technologic.ElasticSearchComputer;
import ca.ualberta.cs.technologic.R;

public class AddItems extends ActionBarActivity {
    final private CurrentUser cu = CurrentUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        Button submitBtn = (Button) findViewById(R.id.submit);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComputer();
                onBackPressed();
                //Intent goToItems1 = new Intent(AddItems.this, HomePage.class);
                //startActivity(goToItems1);
            }
        });
    }

    /**
     * save the new computer
     * needs to retrieve all values from the UI
     */
    private void saveComputer(){
        String make = ((EditText)findViewById(R.id.make)).getText().toString();
        String model = ((EditText)findViewById(R.id.model)).getText().toString();
        Integer year = Integer.parseInt(((EditText) findViewById(R.id.year)).getText().toString());
        String processor= ((EditText)findViewById(R.id.processor)).getText().toString();
        Integer ram = Integer.parseInt(((EditText) findViewById(R.id.memory)).getText().toString());
        Integer hardDrive = Integer.parseInt(((EditText) findViewById(R.id.harddrive)).getText().toString());
        String os = ((EditText)findViewById(R.id.os)).getText().toString();
        Float price = Float.parseFloat(((EditText) findViewById(R.id.baserate)).getText().toString());
        String description = ((EditText)findViewById(R.id.description)).getText().toString();
        String username = cu.getCurrentUser();


        final Computer computer;
        try {
            computer = new Computer(username,make, model, year, processor, ram,
                    hardDrive, os, price, description);

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ElasticSearchComputer.addComputer(computer);
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

}
