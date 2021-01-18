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
 * This class is needed in Room library to create many-to-many relationship between Event and Supply table
 * It creates junction which Room library needs to connect relationship between entities
 */
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

    /**
     * Creating a class object which represents one entry in EventSupplyCross table
     *
     * @param eventId  primary key represents specific event identifier
     * @param supplyId primary key represents specific supplier identifier
     */
    public EventSupplyCross(long eventId, long supplyId) {
        this.eventId = eventId;
        this.supplyId = supplyId;
    }
}
