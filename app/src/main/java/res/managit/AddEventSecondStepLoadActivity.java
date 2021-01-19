package res.managit;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import res.managit.add.event.adapter.CustomerAdapter;
import res.managit.add.event.adapter.ProductAdapter;
import res.managit.add.event.adapter.SupplierAdapter;
import res.managit.add.event.adapter.WorkerAdapter;
import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;
import res.managit.dbo.relations.TypeAction;

/**
 * Second step to add load type new event
 */
public class AddEventSecondStepLoadActivity extends AppCompatActivity {

    private ListView listCustomers;
    private ArrayList<Customer> customersArrayList;
    private static CustomerAdapter customerAdapter;

    private ListView listProducts;
    private ArrayList<Product> productsArrayList;
    private static ProductAdapter productsAdapter;

    private Button buttonAddEvent;

    /**
     * Created activity. This activity is responsible for get data from user to add event to database
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_second_load_step);

        WarehouseDb db = PublicDatabaseAcces.currentDatabase;

        ///initialize for customers
        listCustomers = (ListView) findViewById(R.id.customersList);
        customersArrayList = new ArrayList<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Customer> customerList = db.customerDao().getAll();
            customersArrayList.addAll(customerList);
        });
        customerAdapter = new CustomerAdapter(customersArrayList, getApplicationContext());
        listCustomers.setAdapter(customerAdapter);


        ////initialize for products
        listProducts = (ListView) findViewById(R.id.productList);
        listProducts.setItemsCanFocus(true);
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

                ArrayList<Long> workers = new ArrayList<>();
                ArrayList<Long> supplies = new ArrayList<>();
                ArrayList<Long> customers = new ArrayList<>();
                ArrayList<Long> products = new ArrayList<>();


                for (Pair<Worker, Integer> s : WorkerAdapter.getWorkerListChecked()) {
                    if (s.second == 1) {
                        workers.add(s.first.getWorkerId());
                    }
                }


                for(Pair<Customer, Integer> c : CustomerAdapter.getCustomerListChecked()){
                    if(c.second == 1){
                        customers.add(c.first.getCustomerId());
                    }
                }

                for (Pair<Product, Integer> p : ProductAdapter.getProductQuantityList()) {
                    if (p.second != 0) {
                        products.add(p.first.getProductId());
                    }
                }

                if (products.size() != 0 && customers.size() != 0) {
                    Thread threadToUpdateDataBase = new Thread(() -> {
                        Event event = new Event(AddEventFirstStepActivity.getDateTime(), TypeAction.Loading.label, workers, supplies, customers, false);
                        db.eventDao().insertEvent(event);
                        long id = db.eventDao().getMaxEventId();

                        for (Pair<Product, Integer> p : ProductAdapter.getProductQuantityList()) {
                            if (p.second != 0) {
                                EventItem eventItem = new EventItem(p.second, p.first.getProductId(), id);
                                db.eventItemDao().insertEventItem(eventItem);

                            }
                        }
                    });
                    threadToUpdateDataBase.start();
                    try {
                        threadToUpdateDataBase.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                AddEventSecondStepLoadActivity.this.finish();
            }
        });
    }
}