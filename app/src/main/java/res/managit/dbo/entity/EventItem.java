package res.managit.dbo.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class which is used to Room database library.
 * It represents a table in database with the same name.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventItem {
    @PrimaryKey(autoGenerate = true)
    public long eventItemId;
    public int amount;
    public long product_Id;
    public long event_Id;

    /**
     * Creating a class object which represents one entry in EventItem table
     *
     * @param amount     amount of a given and assigned item in the event
     * @param product_Id Foreign key connecting eventItem with a Product table identifier
     * @param event_Id   Foreign key connecting eventItem with a Event table identifier
     */
    public EventItem(int amount, long product_Id, long event_Id) {
        this.amount = amount;
        this.product_Id = product_Id;
        this.event_Id = event_Id;
    }


    public long getEventItemId() {
        return eventItemId;
    }

    public int getAmount() {
        return amount;
    }

    public long getProduct_Id() {
        return product_Id;
    }

    public long getEvent_Id() {
        return event_Id;
    }
}
