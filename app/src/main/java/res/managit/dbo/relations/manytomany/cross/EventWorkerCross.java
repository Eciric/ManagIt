package res.managit.dbo.relations.manytomany.cross;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class which is used to Room database library
 * This class is needed in Room library to create many-to-many relationship between Event and Worker table
 * It creates junction which Room library needs to connect relationship between entities
 */
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

    /**
     * Creating a class object which represents one entry in EventWorkerCross table
     *
     * @param eventId  primary key represents specific event identifier
     * @param workerId primary key represents specific worker identifier
     */
    public EventWorkerCross(long eventId, long workerId) {
        this.eventId = eventId;
        this.workerId = workerId;
    }
}
