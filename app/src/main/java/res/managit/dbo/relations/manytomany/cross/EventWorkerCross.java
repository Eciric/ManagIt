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
@Entity(primaryKeys = {"eventId", "workerId"})
public class EventWorkerCross {
    @ColumnInfo(index = true)
    public long eventId;
    @ColumnInfo(index = true)
    public long workerId;

    public EventWorkerCross(long eventId, long workerId) {
        this.eventId = eventId;
        this.workerId = workerId;
    }
}
