package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Customer;

/**
 * Class used to retrieve customers list
 */
public class CustomersListRetriever extends AsyncTask<Void, Void, List<Customer>> {
    WarehouseDb db;
    View view;
    Context context;
    LayoutInflater inflater;

    /**
     * Class constructor
     *
     * @param context  fragment's context
     * @param view     fragment's view
     * @param db       database on which operations will be done
     * @param inflater fragment's inflater
     */
    public CustomersListRetriever(Context context, View view, WarehouseDb db, LayoutInflater inflater) {
        this.db = db;
        this.view = view;
        this.context = context;
        this.inflater = inflater;
    }

    /**
     * Function used to retrieve customers list
     *
     * @return customers list
     */
    @Override
    protected List<Customer> doInBackground(Void... voids) {
        return db.customerDao().getAll();
    }

    /**
     * Populate customers' ListView with passed result
     *
     * @param result customers list from doInBackground method
     */
    @Override
    protected void onPostExecute(List<Customer> result) {
        ListView customers = view.findViewById(R.id.list);
        ArrayAdapter<Customer> arrayAdapter = new ArrayAdapter<Customer>(context, R.layout.listview_text_formatter, result) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                if (convertView == null)
                    convertView = inflater.inflate(R.layout.manage_listview_formatter, null, false);

                Customer c = result.get(position);
                TextView name = convertView.findViewById(R.id.elementName);
                TextView info = convertView.findViewById(R.id.elementInfo);

                name.setText(c.getName());
                info.setVisibility(View.INVISIBLE);

                return convertView;
            }
        };
        customers.setAdapter(arrayAdapter);
    }

}
