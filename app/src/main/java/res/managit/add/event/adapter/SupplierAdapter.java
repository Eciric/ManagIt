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
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;

/**
 * Class is responsible for created adapter for suppliers. Adapter consist of list suppliers
 */
public class SupplierAdapter extends ArrayAdapter<Supply> {

    private static ArrayList<Pair<Supply, Integer>> suppliesListChecked;
    private static int start = 0;

    ArrayList<Supply> supplies;

    public SupplierAdapter(ArrayList<Supply> data, Context context) {
        super(context, R.layout.one_row_customer_with_checkbox, data);
        suppliesListChecked = new ArrayList<>(data.size());
        start = 0;
        this.supplies = data;
    }

    public static ArrayList<Pair<Supply, Integer>> getSuppliesListChecked() {
        return suppliesListChecked;
    }

    public static void setSuppliesListChecked(ArrayList<Pair<Supply, Integer>> suppliesListChecked) {
        SupplierAdapter.suppliesListChecked = suppliesListChecked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(start == 0){
            for(int i = 0;i<supplies.size();i++){
                suppliesListChecked.add(new Pair<>(supplies.get(i),0));
            }
            start = 1;
        }

        Supply supply = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_row_customer_with_checkbox, parent, false);
        }
        CheckBox setSupply = (CheckBox) convertView.findViewById(R.id.setCustomers);
        setSupply.setTag(position);

        for(int i = 0;i<suppliesListChecked.size();i++){
            Integer number = suppliesListChecked.get(i).second;
            if(i == (int) setSupply.getTag()){
                if (number == 0) {
                    setSupply.setChecked(false);
                } else {
                    setSupply.setChecked(true);
                }
            }
        }

        setSupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<suppliesListChecked.size();i++){
                    final int position2 = (int) ((CheckBox) v).getTag();
                    if(i == position2){
                        Supply supply1 = suppliesListChecked.get(i).first;
                        if(((CheckBox) v).isChecked()){
                            suppliesListChecked.set(i,new Pair<>(supply1,1));
                        }
                        else {
                            suppliesListChecked.set(i,new Pair<>(supply1,0));
                        }
                    }
                }

//                System.out.println(suppliesListChecked);
            }
        });
        setSupply.setText(supply.name);
        return convertView;
    }
}