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
import res.managit.dbo.entity.Worker;

public class WorkerListRetriever extends AsyncTask<Void, Void, List<Worker>> {
    WarehouseDb db;
    View view;
    Context context;

    public WorkerListRetriever(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    @Override
    protected List<Worker> doInBackground(Void... voids) {
        return db.workerDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Worker> result) {
        ListView workers = view.findViewById(R.id.productsList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.listview_text_formatter, R.id.textView2, createProductLabels(result));
        workers.setAdapter(arrayAdapter);
    }

    private List<String> createProductLabels(List<Worker> result) {
        List<String> workers = new ArrayList<>();
        for (Worker w : result) {
            workers.add("[" + w.getWorkerId() + "] " + w.getName() + " " + w.getLastName());
        }
        return workers;
    }

}
