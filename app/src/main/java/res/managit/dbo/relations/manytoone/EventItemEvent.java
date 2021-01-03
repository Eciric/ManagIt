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
