package res.managit.add.event.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import res.managit.R;
import res.managit.dbo.entity.Customer;

public class CustomerAdapter extends ArrayAdapter<Customer> {

    private static ArrayList<Customer> customerListChecked;

    public CustomerAdapter(ArrayList<Customer> data, Context context) {
        super(context, R.layout.one_row_customer_with_checkbox, data);
        customerListChecked = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Customer customer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_row_customer_with_checkbox, parent, false);
        }
        CheckBox setCustomers = (CheckBox) convertView.findViewById(R.id.setCustomers);
        setCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    customerListChecked.add(customer);
                } else{
                    customerListChecked.remove(customer);
                }
                System.out.println(customerListChecked);
            }
        });

        setCustomers.setText(customer.name);
        return convertView;
    }

    public static ArrayList<Customer> getCustomerListChecked() {
        return customerListChecked;
    }

    public static void setCustomerListChecked(ArrayList<Customer> customerListChecked) {
        CustomerAdapter.customerListChecked = customerListChecked;
    }
}
