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
public class EventSupply {
    @Embedded
    public Event event;
    @Relation(
            parentColumn = "eventId",
            entityColumn = "supplyId",
            associateBy = @Junction(EventSupplyCross.class)
    )
    public List<Supply> supplies;
}
