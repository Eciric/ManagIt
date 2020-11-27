package res.managit.dbo.relations.manytomany.cross;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor

@ToString
@Entity(primaryKeys = {"eventId", "customerId"})
public class EventCustomerCross {
    @ColumnInfo(index = true)
    public long eventId;
    @ColumnInfo(index = true)
    public long customerId;

    public EventCustomerCross(long eventId, long customerId) {
        this.eventId = eventId;
        this.customerId = customerId;
    }
}
