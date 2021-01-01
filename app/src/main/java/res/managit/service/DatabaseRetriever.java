package res.managit.service;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import res.managit.R;
import res.managit.dbo.WarehouseDb;

public class DatabaseRetriever extends AsyncTask<Void, Void, List<Integer>> {
    WarehouseDb db;
    View view;

    public DatabaseRetriever(View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
    }

    @Override
    protected List<Integer> doInBackground(Void... voids) {
        return Arrays.asList(db.productDao().getAll().size(),
                            db.workerDao().getAll().size(),
                            db.supplyDao().getAll().size(),
                            db.customerDao().getAll().size());
    }

    @Override
    protected void onPostExecute(List<Integer> result) {
        Button products = view.findViewById(R.id.products);
        products.setText(products.getText() + " (" + result.get(0).toString() + ")");

        Button workers = view.findViewById(R.id.workers);
        workers.setText(workers.getText() + " (" + result.get(1).toString() + ")");

        Button suppliers = view.findViewById(R.id.suppliers);
        suppliers.setText(suppliers.getText() + " (" + result.get(2).toString() + ")");

        Button customers = view.findViewById(R.id.customers);
        customers.setText(customers.getText() + " (" + result.get(3).toString() + ")");
    }
}
