package res.managit.dbo.relations.manytomany.cross;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class which is used to Room database library
 * This class is needed in Room library to create many-to-many relationship between Event and Customer table
 * It creates junction which Room library needs to connect relationship between entities
 */
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

    /**
     * Creating a class object which represents one entry in EventCustomerCross table
     *
     * @param eventId    primary key represents specific event identifier
     * @param customerId primary key represents specific customer identifier
     */
    public EventCustomerCross(long eventId, long customerId) {
        this.eventId = eventId;
        this.customerId = customerId;
    }
}
