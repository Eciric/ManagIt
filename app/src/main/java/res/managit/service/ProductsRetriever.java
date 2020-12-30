package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import res.managit.R;
import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Product;

public class ProductsRetriever extends AsyncTask<Void, Void, List<Product>> {
    WarehouseDb db;
    View view;
    Context context;

    public ProductsRetriever(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    @Override
    protected List<Product> doInBackground(Void... voids) {
        return db.productDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Product> result) {
        ListView products = view.findViewById(R.id.productsList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.listview_text_formatter, R.id.textView2, createProductLabels(result));
        products.setAdapter(arrayAdapter);
    }

    private List<String> createProductLabels(List<Product> result) {
        List<String> products = new ArrayList<>();
        for (Product p : result) {
            products.add("[" + p.getProductId() + "] " + p.getName() + " (" + p.getAmount() + ")");
        }
        return products;
    }

}
