package res.managit.add.event.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import res.managit.R;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Worker;

public class CustomerAdapter extends ArrayAdapter<Customer> {

    private static ArrayList<Pair<Customer, Integer>> customerListChecked;
    private static int start = 0;

    ArrayList<Customer> customers;

    public CustomerAdapter(ArrayList<Customer> data, Context context) {
        super(context, R.layout.one_row_customer_with_checkbox, data);
        customerListChecked = new ArrayList<>(data.size());
        start = 0;
        this.customers = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(start == 0){
            for(int i = 0;i<customers.size();i++){
                customerListChecked.add(new Pair<>(customers.get(i),0));
            }
            start = 1;
        }

        Customer customer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_row_customer_with_checkbox, parent, false);
        }
        CheckBox setCustomers = (CheckBox) convertView.findViewById(R.id.setCustomers);
        setCustomers.setTag(position);
        for(int i = 0;i<customerListChecked.size();i++){
            Integer number = customerListChecked.get(i).second;
            if(i == (int) setCustomers.getTag()){
                if (number == 0) {
                    setCustomers.setChecked(false);
                } else {
                    setCustomers.setChecked(true);
                }
            }
        }
        setCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<customerListChecked.size();i++){
                    final int position2 = (int) ((CheckBox) v).getTag();
                    if(i == position2){
                        Customer customer1 = customerListChecked.get(i).first;
                        if(((CheckBox) v).isChecked()){
                            customerListChecked.set(i,new Pair<>(customer1,1));
                        }
                        else {
                            customerListChecked.set(i,new Pair<>(customer1,0));
                        }
                    }
                }

//                System.out.println(customerListChecked);
            }
        });

        setCustomers.setText(customer.name);
        return convertView;
    }

    public static ArrayList<Pair<Customer, Integer>> getCustomerListChecked() {
        return customerListChecked;
    }

    public static void setCustomerListChecked(ArrayList<Pair<Customer, Integer>> customerListChecked) {
        CustomerAdapter.customerListChecked = customerListChecked;
    }
}
