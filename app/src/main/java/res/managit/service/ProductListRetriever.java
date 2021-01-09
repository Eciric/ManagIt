package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import res.managit.R;
import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Product;

public class ProductListRetriever extends AsyncTask<Void, Void, List<Product>> {
    WarehouseDb db;
    View view;
    Context context;
    LayoutInflater inflater;

    public ProductListRetriever(Context context, View view, WarehouseDb db, LayoutInflater inflater) {
        this.db = db;
        this.view = view;
        this.context = context;
        this.inflater = inflater;
    }

    @Override
    protected List<Product> doInBackground(Void... voids) {
        return db.productDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Product> result) {
        ListView products = view.findViewById(R.id.list);
        ArrayAdapter<Product> arrayAdapter = new ArrayAdapter<Product>(context, R.layout.listview_text_formatter, R.id.textView2, result)  {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                if(convertView == null)
                    convertView = inflater.inflate(R.layout.manage_listview_formatter, null, false);

                Product p = result.get(position);
                TextView name = convertView.findViewById(R.id.elementName);
                TextView info = convertView.findViewById(R.id.elementInfo);

                name.setText(p.getName());
                info.setText(Integer.toString(p.getAmount()));

                return convertView;
            }
        };
        products.setAdapter(arrayAdapter);
    }
}
