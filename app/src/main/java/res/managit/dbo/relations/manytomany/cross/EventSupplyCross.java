package res.managit.dbo.relations.manytomany.cross;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity(primaryKeys = {"eventId", "supplyId"})
public class EventSupplyCross {
    @ColumnInfo(index = true)
    public long eventId;
    @ColumnInfo(index = true)
    public long supplyId;

    public EventSupplyCross(long eventId, long supplyId) {
        this.eventId = eventId;
        this.supplyId = supplyId;
    }
}
