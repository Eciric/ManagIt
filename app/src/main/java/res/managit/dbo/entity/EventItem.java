package res.managit.dbo.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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
