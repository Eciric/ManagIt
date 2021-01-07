package res.managit.dbo.helpers;

import java.time.LocalDateTime;
import java.util.List;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;
import res.managit.dbo.relations.TypeAction;

public class ExecuteEvents extends Thread {

    public ExecuteEvents() {
        canBeExecute = true;
    }

    private boolean canBeExecute;

    public void stopProcess() {
        canBeExecute = false;
    }

    @Override
    public void run() {
        WarehouseDb db = PublicDatabaseAcces.currentDatabase;
        List<Event> listEvents;
        List<EventItem> listEventItem;
        List<Product> listProduct;

        while (canBeExecute) {
            listEvents = db.eventDao().getAll();
            System.out.println("run " + this.getName());
            for (Event event : listEvents) {
                if (event.getDate().compareTo(LocalDateTime.now()) <= 0 && !event.isExecuted) {
                    listEventItem = db.eventItemDao().getAll();
                    for (EventItem eventItem : listEventItem) {
                        if (eventItem.event_Id == event.eventId) {
                            listProduct = db.productDao().getAll();
                            for (Product product : listProduct) {
                                if (product.productId == eventItem.product_Id) {
                                    if (event.action.equals(TypeAction.Loading.label)) {
                                        product.amount -= eventItem.amount;
                                    } else {
                                        product.amount += eventItem.amount;
                                    }
                                    event.isExecuted = true;
                                    db.eventDao().updateEvent(event);
                                    db.productDao().updateProduct(product);
                                }
                            }
                        }
                    }
                }
            }
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
