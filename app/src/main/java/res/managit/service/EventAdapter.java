package res.managit.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import res.managit.R;
import res.managit.dbo.entity.Event;

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(@NonNull Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_row_event, parent, false);
        }
        // Lookup view for data population
        TextView action = (TextView) convertView.findViewById(R.id.action);
        TextView amount = (TextView) convertView.findViewById(R.id.amount);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        //        TextView action = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object
        action.setText(event.action);
        String temp = "amount " + event.amount;
        amount.setText(temp);
        date.setText(event.date.toString());

        // Return the completed view to render on screen
        return convertView;
    }


}
