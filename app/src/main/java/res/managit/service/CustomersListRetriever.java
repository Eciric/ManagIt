package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import res.managit.R;
import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;

public class CustomersListRetriever extends AsyncTask<Void, Void, List<Customer>> {
    WarehouseDb db;
    View view;
    Context context;

    public CustomersListRetriever(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    @Override
    protected List<Customer> doInBackground(Void... voids) {
        return db.customerDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Customer> result) {
        ListView customers = view.findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.listview_text_formatter, R.id.textView2, createCustomerLabels(result));
        customers.setAdapter(arrayAdapter);
    }

    private List<String> createCustomerLabels(List<Customer> result) {
        List<String> customers = new ArrayList<>();
        for (Customer c : result) {
            customers.add("[" + c.getCustomerId() + "] " + c.getName());
        }
        return customers;
    }

}
