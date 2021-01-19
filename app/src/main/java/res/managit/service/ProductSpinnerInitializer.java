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

/**
 * Class used to initialize spinner from product's add card
 */
public class ProductSpinnerInitializer extends AsyncTask<Void, Void, List<Category>> {
    WarehouseDb db;
    View view;
    Context context;


    /**
     * Class constructor
     *
     * @param context fragment's context
     * @param view    fragment's view
     * @param db      database on which operations will be done
     */
    public ProductSpinnerInitializer(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    /**
     * Function used to retrieve categories from database
     *
     * @return categories list
     */
    @Override
    protected List<Category> doInBackground(Void... voids) {
        return db.categoryDao().getAll();
    }

    /**
     * Function used to populate spinner with data from result list
     */
    @Override
    protected void onPostExecute(List<Category> result) {
        Spinner spinner = view.findViewById(R.id.productCategorySpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, createCategoryNameList(result));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    /**
     * Function which converts list of categories to list of categories names
     *
     * @param result list of categories
     * @return list of categories names
     */
    private List<String> createCategoryNameList(List<Category> result) {
        List<String> categories = new ArrayList<>();
        for (Category c : result) {
            categories.add(c.getName());
        }
        return categories;
    }

}
