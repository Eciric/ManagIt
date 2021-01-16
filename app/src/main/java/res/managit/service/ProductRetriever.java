package res.managit.service;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Product;

public class ProductRetriever extends AsyncTask<Void, Void, ProductRetriever.Data> {
    WarehouseDb db;
    View view;
    Product product;

    /**
     * Inner class which is a storage of product data
     */
    protected class Data {
        protected Product p;
        protected String cName;

        public Data(Product product, String categoryName) {
            this.p = product;
            this.cName = categoryName;
        }
    }

    /**
     * Class constructor
     * @param view fragment's view
     * @param db database on which operations will be done
     * @param product product which will be displayed
     */
    public ProductRetriever(View view, WarehouseDb db, Product product) {
        this.db = db;
        this.view = view;
        this.product = product;
    }

    /**
     * Function to retrieve product data from database
     * @return product data
     */
    @Override
    protected Data doInBackground(Void... voids) {
        Category category = db.categoryDao().getCategoryById(product.getCategory_Id());
        Data data = new Data(product, category.getName());
        return data;
    }

    /**
     * Function used to fill ui with product data passed in result
     * @param result product data from doInBackground method
     */
    @Override
    protected void onPostExecute(Data result) {
        TextView name = view.findViewById(R.id.productName);
        TextView id = view.findViewById(R.id.productId);
        TextView amount = view.findViewById(R.id.productAmount);
        TextView category = view.findViewById(R.id.productCategory);

        name.setText(result.p.getName());
        id.setText(Long.toString(result.p.getProductId()));
        amount.setText(Integer.toString(result.p.getAmount()));
        category.setText(result.cName);
    }
}