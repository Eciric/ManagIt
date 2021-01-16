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
import res.managit.dbo.entity.Worker;

/**
 * Class used to retrieve workers list
 */
public class WorkerListRetriever extends AsyncTask<Void, Void, List<Worker>> {
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
    public WorkerListRetriever(Context context, View view, WarehouseDb db, LayoutInflater inflater) {
        this.db = db;
        this.view = view;
        this.context = context;
        this.inflater = inflater;
    }

    /**
     * Function used to retrieve workers list
     * @return workers list
     */
    @Override
    protected List<Worker> doInBackground(Void... voids) {
        return db.workerDao().getAll();
    }

    /**
     * Populate workers' ListView with passed result
     * @param result workers list from doInBackground method
     */
    @Override
    protected void onPostExecute(List<Worker> result) {
        ListView workers = view.findViewById(R.id.list);
        ArrayAdapter<Worker> arrayAdapter = new ArrayAdapter<Worker>(context, R.layout.listview_text_formatter, result)  {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                if(convertView == null)
                    convertView = inflater.inflate(R.layout.manage_listview_formatter, null, false);

                Worker w = result.get(position);
                TextView name = convertView.findViewById(R.id.elementName);
                TextView info = convertView.findViewById(R.id.elementInfo);

                name.setText(w.getName());
                info.setText(w.getRole());

                return convertView;
            }
        };
        workers.setAdapter(arrayAdapter);
    }
}
