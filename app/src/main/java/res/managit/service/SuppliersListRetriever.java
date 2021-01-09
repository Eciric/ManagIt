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
import java.util.Arrays;
import java.util.List;

import res.managit.R;
import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;

public class SuppliersListRetriever extends AsyncTask<Void, Void, List<Supply>> {
    WarehouseDb db;
    View view;
    Context context;
    LayoutInflater inflater;

    public SuppliersListRetriever(Context context, View view, WarehouseDb db, LayoutInflater inflater) {
        this.db = db;
        this.view = view;
        this.context = context;
        this.inflater = inflater;
    }

    @Override
    protected List<Supply> doInBackground(Void... voids) {
        return db.supplyDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Supply> result) {
        ListView suppliers = view.findViewById(R.id.list);
        ArrayAdapter<Supply> arrayAdapter = new ArrayAdapter<Supply>(context, R.layout.listview_text_formatter, result)  {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                if(convertView == null)
                    convertView = inflater.inflate(R.layout.manage_listview_formatter, null, false);

                Supply s = result.get(position);
                TextView name = convertView.findViewById(R.id.elementName);
                TextView info = convertView.findViewById(R.id.elementInfo);

                name.setText(s.getName());
                info.setVisibility(View.INVISIBLE);

                return convertView;
            }
        };
        suppliers.setAdapter(arrayAdapter);
    }
}
