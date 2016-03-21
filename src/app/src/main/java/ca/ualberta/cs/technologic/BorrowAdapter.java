package ca.ualberta.cs.technologic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jessica on 2016-03-18.
 */
public class BorrowAdapter extends ArrayAdapter<Borrow> {
    private boolean myBorrows;

    public BorrowAdapter(Context context, ArrayList<Borrow> borrows, boolean myBorrows) {
        super(context, 0, borrows);
        this.myBorrows = myBorrows;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Computer[] c = new Computer[1];
        // Get the data item for this position
        final Borrow borrow = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.borrowlistview, parent, false);
        }
        // Lookup view for data population
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView owner = (TextView) convertView.findViewById(R.id.owner);
        // Populate the data into the template view using the data object
        Thread thread = new Thread(new Runnable() {
            public void run() {
                c[0] = ElasticSearchComputer.getComputersById(borrow.getComputerID());
            }

        });
        thread.start();


        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        description.setText(c[0].getDescription());

        if (myBorrows) {
            owner.setText("owner: " + borrow.getOwner());
        } else {
            owner.setText("borrower: " + borrow.getUsername());
        }
//        //change the color of the availability
//        if (computer.getStatus().equals("available")){
//            status.setTextColor(Color.parseColor("#3b5323"));
//        }
//        else if (computer.getStatus().equals("bidded")){
//            status.setTextColor(Color.parseColor("#e6e600"));
//        }
//        else{
//            status.setTextColor(Color.parseColor("#b20000"));
//        }
        // Return the completed view to render on screen
        return convertView;
    }

}
