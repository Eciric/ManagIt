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

/**
 * Class handling adding new product
 */
public class AddProductHandler extends AsyncTask<Void, Void, String> {
    final String EMPTY_MSG = "Name cannot be empty";
    final String INVALID_MSG = "Name can contain only letters";
    final String EXISTS_MSG = "Product already exists";
    final String SUCCESS_MSG = "Product successfully added";

    WarehouseDb db;
    View view;
    Context context;

    /**
     * Class constructor
     *
     * @param context fragment's context
     * @param view    fragment's view
     * @param db      database on which operations will be done
     */
    public AddProductHandler(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    /**
     * Function to validate user input data.
     * After successful validation new product is inserted.
     *
     * @return validation message
     */
    @Override
    protected String doInBackground(Void... voids) {
        EditText text = view.findViewById(R.id.productName);
        Spinner mySpinner = view.findViewById(R.id.productCategorySpinner);
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

        String categoryName = mySpinner.getSelectedItem().toString();
        Category category = db.categoryDao().getCategoryByName(categoryName);
        db.productDao().insertProduct(new Product(name, 0, category.getCategoryId()));

        return SUCCESS_MSG;
    }

    /**
     * Function which updates ui based on passed result
     *
     * @param result validation message from doInBackground method
     */
    @Override
    protected void onPostExecute(String result) {
        TextView errorText = view.findViewById(R.id.productError);

        if (!result.equals(SUCCESS_MSG))
            errorText.setText(result);
        else {
            EditText text = view.findViewById(R.id.productName);
            CardView cardView = view.findViewById(R.id.addProductsCard);

            errorText.setText("");
            text.setText("");

            cardView.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(context, SUCCESS_MSG, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
