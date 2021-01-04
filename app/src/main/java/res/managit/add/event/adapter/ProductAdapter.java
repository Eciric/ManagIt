package res.managit.add.event.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import res.managit.R;
import res.managit.dbo.entity.Product;

public class ProductAdapter extends ArrayAdapter<Product> {

    private static ArrayList<Pair<Product, Integer>> productQuantityList;

    public ProductAdapter(ArrayList<Product> data, Context context) {
        super(context, R.layout.one_row_customer_with_checkbox, data);
        productQuantityList = new ArrayList<>();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_row_product_with_input_number, parent, false);
        }
        EditText quantity = (EditText) convertView.findViewById(R.id.number);
        TextView name = convertView.findViewById(R.id.nameProduct);
        name.setText(product.name);
        quantity.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                for (int i = 0; i < productQuantityList.size(); i++) {
                    if (productQuantityList.get(i).first.equals(product)) {
                        productQuantityList.remove(i);
                        break;
                    }
                }
                productQuantityList.add(new Pair<>(product, Integer.parseInt(s.toString())));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() != 0)
//                    field2.setText("");
            }
        });
        return convertView;


    }

    public static ArrayList<Pair<Product, Integer>> getProductQuantityList() {
        return productQuantityList;
    }

    public static void setProductQuantityList(ArrayList<Pair<Product, Integer>> productListChecked) {
        ProductAdapter.productQuantityList = productListChecked;
    }
}
