package res.managit.dbo.relations.manytomany;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.Worker;
import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventWorker {
    @Embedded
    public Event event;
    @Relation(
            parentColumn = "eventId",
            entityColumn = "workerId",
            associateBy = @Junction(EventWorkerCross.class)
    )
    public List<Worker> workers;
}
