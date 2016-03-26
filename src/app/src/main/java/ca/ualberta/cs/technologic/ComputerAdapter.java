package ca.ualberta.cs.technologic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ComputerAdapter extends ArrayAdapter<Computer>{
    public ComputerAdapter(Context context, ArrayList<Computer> computers) {
        super(context, 0, computers);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Computer computer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.computerlistview, parent, false);
        }
        // Lookup view for data population
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        TextView year = (TextView) convertView.findViewById(R.id.year);
        TextView model = (TextView) convertView.findViewById(R.id.model);
        // Populate the data into the template view using the data object
        description.setText(computer.getDescription());
        model.setText(computer.getModel());
        year.setText(computer.getYear().toString());
        status.setText(computer.getStatus());

        //change the color of the availability
        if (computer.getStatus().equals("available")){
            status.setTextColor(Color.parseColor("#008000"));
        }
        else if (computer.getStatus().equals("bidded")){
            status.setTextColor(Color.parseColor("#ff8c00"));
        }
        else{
            status.setTextColor(Color.parseColor("#0077ea"));
        }
        // Return the completed view to render on screen
        return convertView;
    }

}
