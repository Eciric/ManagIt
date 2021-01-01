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
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;

public class SuppliersListRetriever extends AsyncTask<Void, Void, List<Supply>> {
    WarehouseDb db;
    View view;
    Context context;

    public SuppliersListRetriever(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    @Override
    protected List<Supply> doInBackground(Void... voids) {
        return db.supplyDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Supply> result) {
        ListView workers = view.findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.listview_text_formatter, R.id.textView2, createSupplierLabels(result));
        workers.setAdapter(arrayAdapter);
    }

    private List<String> createSupplierLabels(List<Supply> result) {
        List<String> suppliers = new ArrayList<>();
        for (Supply s : result) {
            suppliers.add("[" + s.getSupplyId() + "] " + s.getName());
        }
        return suppliers;
    }

}
