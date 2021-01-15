package res.managit.dbo.relations.manytoone;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.EventItem;

/**
 * Class which is used to Room database library
 * This class is needed in Room library to create many-to-one relationship between Event and EventItem table
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EventItemEvent {
    @Embedded public Event event;
    @Relation(
            parentColumn = "eventId",
            entityColumn = "event_Id"
    )
    public List<EventItem> eventItems;
}
