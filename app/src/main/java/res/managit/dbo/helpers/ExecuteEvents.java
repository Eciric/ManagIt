package res.managit.dbo.helpers;

import java.time.LocalDateTime;
import java.util.List;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;

public class ExecuteEvents extends Thread{

    @Override
    public void run() {
        WarehouseDb db = PublicDatabaseAcces.currentDatabase;
        List<Event> listEvents;
        List<EventItem> listEventItem;
        List<Product> listProduct;

        while (true){
            System.out.println("!!!!!!!!!!!!!!!!!!!");
            listEvents = db.eventDao().getAll();

            for ( Event event : listEvents ){
                System.out.println("enter to list event");
                if ( event.getDate().compareTo(LocalDateTime.now()) <= 0 && !event.isExecuted){
                    listEventItem = db.eventItemDao().getAll();
                    for ( EventItem eventItem: listEventItem ){
                        System.out.println("enter to list eventItem");
                        if ( eventItem.event_Id == event.eventId ){
                            listProduct = db.productDao().getAll();
                            System.out.println("size listProduct "+ listProduct.size());
                            for ( Product product : listProduct ){
                                System.out.println("enter to list eventListProduct");
                                if ( product.productId == eventItem.product_Id ){
                                    if ( event.action.equals("loading") ){
                                        product.amount -= eventItem.amount;
                                        event.isExecuted = true;
                                        System.out.println(product.name+" -"+eventItem.amount);
                                    }else{
                                        product.amount += eventItem.amount;
                                        event.isExecuted = true;
                                        System.out.println(product.name+" +"+eventItem.amount);
                                    }
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
