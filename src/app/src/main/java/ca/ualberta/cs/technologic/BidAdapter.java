package ca.ualberta.cs.technologic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.Activities.ViewUser;


public class BidAdapter extends ArrayAdapter<Bid> {
    private boolean myBids;

    public BidAdapter(Context context, ArrayList<Bid> bids, boolean myBids) {
        super(context, 0, bids);
        this.myBids = myBids;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Computer[] c = new Computer[1];
        // Get the data item for this position
        final Bid bid = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bidlistview, parent, false);
        }
        // Lookup view for data population
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView owner = (TextView) convertView.findViewById(R.id.owner);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        // Populate the data into the template view using the data object
        Thread thread = new Thread(new Runnable() {
            public void run() {
                c[0] = ElasticSearchComputer.getComputersById(bid.getComputerID());
            }

        });
        thread.start();


        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        description.setText(c[0].getDescription());
        price.setText("bid: $" + String.format("%.2f", bid.getPrice()));

        if (myBids) {
            owner.setText("owner: " + bid.getOwner());
        } else {
            owner.setText("bidder: " + bid.getUsername());
        }
        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}
