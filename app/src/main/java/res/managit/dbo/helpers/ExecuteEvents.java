package res.managit.dbo.helpers;

import java.time.LocalDateTime;
import java.util.List;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;
import res.managit.dbo.relations.TypeAction;
import res.managit.dbo.relations.onetoone.EventItemProduct;

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

        EventItemProduct eventItemProduct;
        List<EventItem> listEventItem;
        List<Event> listEvents;

        while (canBeExecute) {
            listEvents = db.eventDao().getEventByDateAndExecution(false);
            System.out.println("run " + this.getName());
            for (Event event : listEvents) {
                    listEventItem = db.eventItemDao().getEventItemByEventId(event.eventId);
                    for (EventItem eventItem : listEventItem) {
                            eventItemProduct = db.eventItemDao().getEventItemByEventIdAndProductId(event.eventId, eventItem.product_Id);
                            if (event.action.equals(TypeAction.Loading.label)) {
                                eventItemProduct.product.amount -= eventItem.amount;
                            } else {
                                eventItemProduct.product.amount += eventItem.amount;
                            }
                            event.isExecuted = true;
                            db.eventDao().updateEvent(event);
                            db.productDao().updateProduct(eventItemProduct.product);
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
