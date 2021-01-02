package res.managit.service;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;

public class EventRetriever extends AsyncTask<Void, Void, EventRetriever.Data> {
    //NOTE(Krystian): Zostawiłem klase Data w razie gdyby trzeba bylo rozbudowac wyswietlanie o informacje o customerach czy productach a nie tylko liczby
    WarehouseDb db;
    View view;
    Event event;

    protected class Data {
        protected Event event;
//        protected Worker worker;
//        protected Supply supply;
//        protected Product product;
//        protected Customer customer;

        public Data(Event event) {
            this.event = event;
//            this.worker = worker;
//            this.supply = supply;
//            this.product = product;
//            this.customer = customer;
        }
    }

    public EventRetriever(View view, WarehouseDb db, Event event) {
        this.db = db;
        this.view = view;
        this.event = event;
    }


    @Override
    protected Data doInBackground(Void... voids) {
        //Event event = db.eventDao().getEventById(id);
        Data data = new Data(event);
        return data;
    }

    @Override
    protected void onPostExecute(Data result) {
        TextView action = view.findViewById(R.id.action);
        TextView date = view.findViewById(R.id.date);
        TextView amount = view.findViewById(R.id.amount);
        TextView workers = view.findViewById(R.id.workers);
        TextView suppliers = view.findViewById(R.id.suppliers);
        TextView customers = view.findViewById(R.id.customers);
        TextView products = view.findViewById(R.id.products);

        action.setText("Action: " + result.event.getAction());
        date.setText("Date: " + result.event.getDate());
        amount.setText("Amount: " + result.event.getAmount());
        workers.setText("Workers: " + result.event.getWorker_Id().toString());
        suppliers.setText("Suppliers: " + result.event.getSupplier_Id().toString());
        customers.setText("Customers: " + result.event.getCustomer_Id().toString());
        products.setText("Products: " + result.event.getProduct_Id().toString());
    }
}
