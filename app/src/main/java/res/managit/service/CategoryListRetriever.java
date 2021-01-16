package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;

/**
 * Class used to retrieve categories list
 */
public class CategoryListRetriever extends AsyncTask<Void, Void, List<Category>> {
    WarehouseDb db;
    View view;
    Context context;
    LayoutInflater inflater;

    /**
     * Class constructor
     * @param context fragment's context
     * @param view fragment's view
     * @param db database on which operations will be done
     * @param inflater fragment's inflater
     */
    public CategoryListRetriever(Context context, View view, WarehouseDb db, LayoutInflater inflater) {
        this.db = db;
        this.view = view;
        this.context = context;
        this.inflater = inflater;
    }

    /**
     * Function used to retrieve categories list
     * @return categories list
     */
    @Override
    protected List<Category> doInBackground(Void... voids) {
        return db.categoryDao().getAll();
    }

    /**
     * Populate categories' ListView with passed result
     * @param result categories list from doInBackground method
     */
    @Override
    protected void onPostExecute(List<Category> result) {
        ListView products = view.findViewById(R.id.list);
        ArrayAdapter<Category> arrayAdapter = new ArrayAdapter<Category>(context, R.layout.manage_listview_formatter, result) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                if(convertView == null)
                    convertView = inflater.inflate(R.layout.manage_listview_formatter, null, false);

                Category c = result.get(position);
                TextView name = convertView.findViewById(R.id.elementName);
                TextView info = convertView.findViewById(R.id.elementInfo);

                name.setText(c.getName());
                info.setVisibility(View.INVISIBLE);

                return convertView;
            }
        };
        products.setAdapter(arrayAdapter);
    }
}
