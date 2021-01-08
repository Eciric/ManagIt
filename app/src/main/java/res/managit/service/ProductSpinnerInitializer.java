package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import res.managit.R;

import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;

public class ProductSpinnerInitializer extends AsyncTask<Void, Void, List<Category>> {
    WarehouseDb db;
    View view;
    Context context;

    public ProductSpinnerInitializer(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    @Override
    protected List<Category> doInBackground(Void... voids) {
        return db.categoryDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Category> result) {
        Spinner spinner = view.findViewById(R.id.productCategorySpinner);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, createCategoryNameList(result));

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private List<String> createCategoryNameList(List<Category> result) {
        List<String> categories = new ArrayList<>();
        for (Category c : result) {
            categories.add("[" + c.getName() +")");
        }
        return categories;
    }

}
