package res.managit.add.event.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

import res.managit.R;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Worker;

public class WorkerAdapter extends ArrayAdapter<Worker> {

    private static ArrayList<Pair<Worker, Integer>> workerListChecked;
    private static int start = 0;

    ArrayList<Worker> workers;

    public WorkerAdapter(ArrayList<Worker> data, Context context) {
        super(context, R.layout.one_row_customer_with_checkbox, data);
        workerListChecked = new ArrayList<>(data.size());
        start = 0;
        this.workers = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(start == 0){
            for(int i = 0;i<workers.size();i++){
                workerListChecked.add(new Pair<>(workers.get(i),0));
            }
            start = 1;
        }

        Worker worker = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_row_worker_with_checkox, parent, false);
        }
        CheckBox setWorker = (CheckBox) convertView.findViewById(R.id.setWorker);
        setWorker.setTag(position);
        for(int i = 0;i<workerListChecked.size();i++){
           Integer number = workerListChecked.get(i).second;
            if(i == (int) setWorker.getTag()){
                if (number == 0) {
                    setWorker.setChecked(false);
                } else {
                    setWorker.setChecked(true);
                }
            }
        }

        setWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<workerListChecked.size();i++){
                    final int position2 = (int) ((CheckBox) v).getTag();
                    if(i == position2){
                        Worker worker1 = workerListChecked.get(i).first;
                        if(((CheckBox) v).isChecked()){
                            workerListChecked.set(i,new Pair<>(worker1,1));
                        }
                        else {
                            workerListChecked.set(i,new Pair<>(worker1,0));
                        }
                    }
                }

                System.out.println(workerListChecked);
            }
        });

        setWorker.setText(worker.name);
        return convertView;
    }

    public static ArrayList<Pair<Worker,Integer>> getWorkerListChecked() {
        return workerListChecked;
    }

    public static void setWorkerListChecked(ArrayList<Pair<Worker,Integer>> workerListChecked) {
        WorkerAdapter.workerListChecked = workerListChecked;
    }
}
