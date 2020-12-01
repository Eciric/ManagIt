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
import res.managit.dbo.entity.Product;
import res.managit.dbo.relations.manytomany.cross.EventProductCross;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventProduct {
    @Embedded
    public Event event;
    @Relation(
            parentColumn = "eventId",
            entityColumn = "productId",
            associateBy = @Junction(EventProductCross.class)
    )
    public List<Product> products;
}
