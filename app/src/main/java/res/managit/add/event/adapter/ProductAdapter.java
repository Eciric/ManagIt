package res.managit.add.event.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.util.SparseBooleanArray;
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
    private static int start = 0;

    ArrayList<Product> products;

    public ProductAdapter(ArrayList<Product> data, Context context) {
        super(context, R.layout.one_row_customer_with_checkbox, data);
        this.products = data;
        start = 0;
        productQuantityList = new ArrayList<>(data.size());

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(start == 0){
            for(int i = 0;i<products.size();i++){
                productQuantityList.add(new Pair<>(products.get(i),0));
            }
            start = 1;
        }

        Product product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_row_product_with_input_number, parent, false);
        }
        EditText quantity = (EditText) convertView.findViewById(R.id.number);
        TextView name = convertView.findViewById(R.id.nameProduct);
        name.setText(product.name);
        quantity.setTag(position);
        for(int i = 0;i<productQuantityList.size();i++){
            Integer number = productQuantityList.get(i).second;
            if(i == (int)quantity.getTag()){
                quantity.setText(number.toString());
            }
        }

        quantity.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                final  int position2 = (int) quantity.getTag();
                final EditText caption = quantity;
                if(caption.getText().toString().length()>0){

                }
//                System.out.println("Rozmiar: " + productQuantityList.size());
//                System.out.println("Produkt: " + product.toString());
//                System.out.println("Quan: " + quantity.getText().toString());
//                System.out.println("QuanTag: " + quantity.getTag());
//                System.out.println("QuanId: " + quantity.getId());
//                System.out.println("S: " + s.toString());



                for(int i = 0;i<productQuantityList.size();i++){
                    if(i == position2 && s.length() != 0){
                        Product product1 = productQuantityList.get(i).first;
                        productQuantityList.set(i,new Pair<>(product1,Integer.parseInt(s.toString())));
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
