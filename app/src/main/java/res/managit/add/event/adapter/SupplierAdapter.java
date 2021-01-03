package res.managit.add.event.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

import res.managit.R;
import res.managit.dbo.entity.Supply;

public class SupplierAdapter extends ArrayAdapter<Supply> {

    private static ArrayList<Supply> suppliesListChecked;

    public SupplierAdapter(ArrayList<Supply> data, Context context) {
        super(context, R.layout.one_row_customer_with_checkbox, data);
        suppliesListChecked = new ArrayList<>();
    }

    public static ArrayList<Supply> getSuppliesListChecked() {
        return suppliesListChecked;
    }

    public static void setSuppliesListChecked(ArrayList<Supply> suppliesListChecked) {
        SupplierAdapter.suppliesListChecked = suppliesListChecked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Supply supply = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_row_customer_with_checkbox, parent, false);
        }
        CheckBox setSupply = (CheckBox) convertView.findViewById(R.id.setCustomers);
        setSupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    suppliesListChecked.add(supply);
                } else{
                    suppliesListChecked.remove(supply);
                }
                System.out.println(suppliesListChecked);
            }
        });
        setSupply.setText(supply.name);
        return convertView;
    }
}