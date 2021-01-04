package res.managit.add.event.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

import res.managit.R;
import res.managit.dbo.entity.Worker;

public class WorkerAdapter extends ArrayAdapter<Worker> {

    private static ArrayList<Worker> workerListChecked;

    public WorkerAdapter(ArrayList<Worker> data, Context context) {
        super(context, R.layout.one_row_customer_with_checkbox, data);
        workerListChecked = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Worker worker = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_row_worker_with_checkox, parent, false);
        }
        CheckBox setWorker = (CheckBox) convertView.findViewById(R.id.setWorker);
        setWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    workerListChecked.add(worker);
                } else{
                    workerListChecked.remove(worker);
                }
                System.out.println(workerListChecked);
            }
        });

        setWorker.setText(worker.name);
        return convertView;
    }

    public static ArrayList<Worker> getWorkerListChecked() {
        return workerListChecked;
    }

    public static void setWorkerListChecked(ArrayList<Worker> workerListChecked) {
        WorkerAdapter.workerListChecked = workerListChecked;
    }
}
