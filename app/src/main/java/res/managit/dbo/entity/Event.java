package res.managit.dbo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Event {
    @PrimaryKey(autoGenerate = true)
    public long eventId;
    public LocalDateTime date;
    public String action;
    public int amount;
    public List<Long> worker_Id;
    public List<Long> supplier_Id;
    public List<Long> customer_Id;
    public List<Long> product_Id;

    public Event(LocalDateTime date, String action, int amount, List<Long> worker_Id, List<Long> supplier_Id, List<Long> customer_Id, List<Long> product_Id) {
        this.date = date;
        this.action = action;
        this.amount = amount;
        this.worker_Id = worker_Id;
        this.supplier_Id = supplier_Id;
        this.customer_Id = customer_Id;
        this.product_Id = product_Id;
    }

}
