package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Product;

public class AddProductHandler extends AsyncTask<Void, Void, String> {
    final String EMPTY_MSG = "Name cannot be empty";
    final String INVALID_MSG = "Name can contain only letters";
    final String EXISTS_MSG = "Product already exists";

    WarehouseDb db;
    View view;
    Context context;

    public AddProductHandler(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        EditText text = (EditText)view.findViewById(R.id.productName);
        Spinner mySpinner = (Spinner)view.findViewById(R.id.productCategorySpinner);
        String name = text.getText().toString();

        if (name.equals(""))
            return EMPTY_MSG;

        if (!name.matches("[a-zA-Z ]+"))
            return INVALID_MSG;

        List<Product> products = db.productDao().getAll();
        List<String> productsNames = new ArrayList<>();
        for (Product p : products)
            productsNames.add(p.getName().toLowerCase());
        if (productsNames.contains(name.toLowerCase()))
            return EXISTS_MSG;

        String categoryName = mySpinner.getSelectedItem().toString().substring(1);
        categoryName = categoryName.substring(0,categoryName.length() - 1);
        Category category = db.categoryDao().getCategoryByName(categoryName);
        db.productDao().insertProduct(new Product(name, 0, category.getCategoryId()));

        return name;
    }

    @Override
    protected void onPostExecute(String result) {
        TextView errorText = view.findViewById(R.id.productError);
        EditText text = (EditText)view.findViewById(R.id.productName);
        CardView cardView = view.findViewById(R.id.addProductsCard);

        if (result.equals(EMPTY_MSG) || result.equals(INVALID_MSG) || result.equals(EXISTS_MSG) )
            errorText.setText(result);
        else {
            errorText.setText("");
            text.setText("");
            cardView.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(context, "Product successfully added", Toast.LENGTH_SHORT);
            toast.show();
        }
        System.out.println("Result: " + result);
    }
}
