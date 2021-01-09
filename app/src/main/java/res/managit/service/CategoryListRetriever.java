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

public class CategoryListRetriever extends AsyncTask<Void, Void, List<Category>> {
    WarehouseDb db;
    View view;
    Context context;
    LayoutInflater inflater;

    public CategoryListRetriever(Context context, View view, WarehouseDb db, LayoutInflater inflater) {
        this.db = db;
        this.view = view;
        this.context = context;
        this.inflater = inflater;
    }

    @Override
    protected List<Category> doInBackground(Void... voids) {
        return db.categoryDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Category> result) {
        ListView products = view.findViewById(R.id.list);
        List<Data> data = createCategoryLabels(result);
        ArrayAdapter<Data> arrayAdapter = new ArrayAdapter<Data>(context, R.layout.manage_listview_formatter, R.id.elementId, data) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                if(convertView == null)
                    convertView = inflater.inflate(R.layout.manage_listview_formatter, null, false);

                Data d = data.get(position);
                TextView id = convertView.findViewById(R.id.elementId);
                TextView name = convertView.findViewById(R.id.elementName);
                TextView info = convertView.findViewById(R.id.elementInfo);

                id.setText(Long.toString(d.getId()));
                name.setText(d.getName());
                info.setVisibility(View.INVISIBLE);

                return convertView;
            }
        };
        products.setAdapter(arrayAdapter);
    }

    class Data {
        long id;
        String name;

        public Data(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    private List<Data> createCategoryLabels(List<Category> result) {
        List<Data> data = new ArrayList<>();
        for (Category c : result) {
            data.add(new Data(c.getCategoryId(), c.getName()));
        }
        return data;
    }

}
