package res.managit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
                ArrayList<Long> workers = new ArrayList<>();
                ArrayList<Long> supplies = new ArrayList<>();
                ArrayList<Long> customers = new ArrayList<>();
                ArrayList<Long> products = new ArrayList<>();
                ArrayList<Long> eventItemsId = new ArrayList<>();
                ArrayList<EventItem> eventItems = new ArrayList<>();


                for(Pair<Worker, Integer> s : WorkerAdapter.getWorkerListChecked()){
                    if(s.second == 1){
                        workers.add(s.first.getWorkerId());
                    }
                }
                for(Pair<Customer, Integer> s : CustomerAdapter.getCustomerListChecked()){
                    if(s.second == 1){
                        customers.add(s.first.getCustomerId());
                    }
                }

                for(Pair<Product, Integer> p : ProductAdapter.getProductQuantityList()){
                    if(p.second != 0){
                        products.add(p.first.getProductId());
                        EventItem eventItem = new EventItem(p.second,p.first.getProductId(),PublicDatabaseAcces.currentDatabaseEventNumber + 1);
                        eventItems.add(eventItem);
                    }
                }

                if(products.size() != 0 && customers.size() != 0){
                    Executors.newSingleThreadExecutor().execute(() -> {
                        for(int i = 0; i<eventItems.size();i++){
                            db.eventItemDao().insertEventItem(eventItems.get(i));
                            int size = db.eventItemDao().getAll().size();
                            eventItemsId.add(db.eventItemDao().getAll().get(size-1).getEventItemId());
                        }
                        Event event = new Event(AddEventFirstStepActivity.getDateTime(),"unloading",eventItemsId,workers,supplies,customers,products);
                        db.eventDao().insertEvent(event);
                        PublicDatabaseAcces.currentDatabaseEventNumber += 1;
                    });
                }
                AddEventSecondStepUnloadActivity.this.finish();
//                Intent intent = new Intent(AddEventSecondStepUnloadActivity.this, planerFragment.class);
//                startActivity(intent);
            }
        });
    }
}