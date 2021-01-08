package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;

public class CategoryListRetriever extends AsyncTask<Void, Void, List<Category>> {
    WarehouseDb db;
    View view;
    Context context;

    public CategoryListRetriever(Context context, View view, WarehouseDb db) {
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
        ListView products = view.findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.listview_text_formatter, R.id.textView2, createCategoryLabels(result));
        products.setAdapter(arrayAdapter);
    }

    private List<String> createCategoryLabels(List<Category> result) {
        List<String> categories = new ArrayList<>();
        for (Category c : result) {
            categories.add("[" + c.getCategoryId() + "] " + c.getName());
        }
        return categories;
    }

}
