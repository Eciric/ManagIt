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
import res.managit.dbo.entity.Supply;
import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class SupplyEvent {
    @Embedded
    public Supply supply;
    @Relation(
            parentColumn = "supplyId",
            entityColumn = "eventId",
            associateBy = @Junction(EventSupplyCross.class)
    )
    public List<Event> events;
}
