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
    String name;

    protected class Data {
        protected Product product;
        protected String categoryName;

        public Data(Product product, String categoryName) {
            this.product = product;
            this.categoryName = categoryName;
        }
    }

    public ProductRetriever(View view, WarehouseDb db, String name) {
        this.db = db;
        this.view = view;
        this.name = name;
    }

    @Override
    protected Data doInBackground(Void... voids) {
        Product product = db.productDao().getProductByName(name);
        Category category = db.categoryDao().getCategoryById(product.getCategory_Id());
        Data data = new Data(product, category.getName());
        return data;
    }

    @Override
    protected void onPostExecute(Data result) {
        TextView name = view.findViewById(R.id.productName);
        TextView id = view.findViewById(R.id.productId);
        TextView amount = view.findViewById(R.id.productAmount);
        TextView category = view.findViewById(R.id.productCategory);

        name.setText("Name: " + result.product.getName());
        id.setText("ID: " + result.product.getProductId());
        amount.setText("Amount: " + result.product.getAmount());
        category.setText("Category: " + result.categoryName);
    }
}