package res.managit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import res.managit.add.event.adapter.CustomerAdapter;
import res.managit.add.event.adapter.ProductAdapter;
import res.managit.add.event.adapter.WorkerAdapter;
import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Product;

public class AddEventSecondStepUnloadActivity extends AppCompatActivity {


    private ListView listCustomers;
    private ArrayList<Customer> customerArrayList;
    private static CustomerAdapter customerAdapter;

    private ListView listProducts;
    private ArrayList<Product> productsArrayList;
    private static ProductAdapter productsAdapter;

    private Button buttonAddEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_second_unload);

        WarehouseDb db = PublicDatabaseAcces.currentDatabase;

        //initialize for customers
        listCustomers = (ListView) findViewById(R.id.customersList);
        customerArrayList = new ArrayList<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Customer> customerList = db.customerDao().getAll();
            customerArrayList.addAll(customerList);
        });
        customerAdapter = new CustomerAdapter(customerArrayList, getApplicationContext());
        listCustomers.setAdapter(customerAdapter);

        //initialize for products
        listProducts = (ListView) findViewById(R.id.productList);
        productsArrayList = new ArrayList<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Product> productList = db.productDao().getAll();
            productsArrayList.addAll(productList);
        });
        productsAdapter = new ProductAdapter(productsArrayList, getApplicationContext());
        listProducts.setAdapter(productsAdapter);

        buttonAddEvent = findViewById(R.id.buttonSaveEvent);
        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo dodaj sprawdzenie przed tworzeniem evenutu czy listCustomers nie jest pusta plus produktow
                System.out.println("#############################");
                System.out.println("Details of event");
                System.out.println("List of customers");
                CustomerAdapter.getCustomerListChecked().forEach(System.out::println);
                System.out.println("List of products with quantity");
                ProductAdapter.getProductQuantityList().forEach(item -> System.out.println(item.first + " " + item.second));
                System.out.println("Type: Unload");
                System.out.println("List of workers");
                WorkerAdapter.getWorkerListChecked().forEach(System.out::println);
                System.out.println(AddEventFirstStepActivity.getDateTime().toString());
                System.out.println("#############################");
                Intent intent = new Intent(AddEventSecondStepUnloadActivity.this, planerFragment.class);
                startActivity(intent);
            }
        });
    }
}