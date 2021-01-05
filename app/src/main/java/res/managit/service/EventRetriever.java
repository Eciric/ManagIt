package res.managit.service;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;

public class EventRetriever extends AsyncTask<Void, Void, EventRetriever.Data> {
    WarehouseDb db;
    View view;
    Event event;

    protected class Data {
        protected Event event;
        protected List<EventItem> items;
        protected List<Worker> worker;
        protected List<Supply> supply;
        protected List<Product> product;
        protected List<Customer> customer;

        public Data(Event event, List<Worker> worker, List<Supply> supply, List<Product> product, List<Customer> customer) {
            this.event = event;
            this.worker = worker;
            this.supply = supply;
            this.product = product;
            this.customer = customer;
        }

        public Data(Event event, List<EventItem> items, List<Worker> worker, List<Supply> supply, List<Product> product, List<Customer> customer) {
            this.event = event;
            this.items = items;
            this.worker = worker;
            this.supply = supply;
            this.product = product;
            this.customer = customer;
        }
    }

    public EventRetriever(View view, WarehouseDb db, Event event) {
        this.db = db;
        this.view = view;
        this.event = event;
    }


    @Override
    protected Data doInBackground(Void... voids) {
        List<Worker> worker = new ArrayList<>();
        List<Supply> supply = new ArrayList<>();
        List<Product> product = new ArrayList<>();
        List<Customer> customer = new ArrayList<>();
        List<EventItem> item = new ArrayList<>();
        for (Long id : event.worker_Id)
            worker.add(db.workerDao().getWorkerById(id));
        for (Long id : event.supplier_Id)
            supply.add(db.supplyDao().getSupplyById(id));
        for (Long id : event.product_Id)
            product.add(db.productDao().getProductById(id));
        for (Long id : event.customer_Id)
            customer.add(db.customerDao().getCustomerById(id));
        for(Long id : event.eventItem_Id)
            item.add(db.eventItemDao().getEventItemById(id));


        Data data = new Data(event, item, worker, supply, product, customer);
        return data;
    }

    @Override
    protected void onPostExecute(Data result) {
        TextView action = view.findViewById(R.id.action);
        TextView date = view.findViewById(R.id.date);
        TextView workers = view.findViewById(R.id.workers);
        TextView suppliers = view.findViewById(R.id.suppliers);
        TextView customers = view.findViewById(R.id.customers);
        TextView products = view.findViewById(R.id.products);

        String textDate = event.date.getYear() + " " + event.getDate().getMonth() + " " + event.getDate().getDayOfMonth() + " " + event.getDate().getHour() + ":" + event.getDate().getMinute();
        if (event.getDate().getMinute() == 0)
            textDate += "0";

        List<String> workersNames = new ArrayList<>();
        List<String> suppliesNames = new ArrayList<>();
        List<String> customersNames = new ArrayList<>();
        List<String> productsNames = new ArrayList<>();
        int i = 0;
        for (Worker w : result.worker){
            workersNames.add(w.getName());
        }

        for (Supply s : result.supply)
            suppliesNames.add(s.getName());
        for (Product p : result.product){
            productsNames.add(p.getName() + " (" + result.items.get(i).amount + ")");
            i++;
        }

        for (Customer c : result.customer)
            customersNames.add(c.getName());


        action.setText("Action: " + result.event.getAction());
        date.setText("Date: " + textDate);
        workers.setText("Workers: " + workersNames.toString().replace("[","").replace("]",""));
        suppliers.setText("Suppliers: " + suppliesNames.toString().replace("[","").replace("]",""));
        customers.setText("Customers: " + customersNames.toString().replace("[","").replace("]",""));
        products.setText("Products: " + productsNames.toString().replace("[","").replace("]",""));
    }
}
