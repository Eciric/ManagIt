package res.managit.dbo.relations.onetoone;

import androidx.room.Embedded;
import androidx.room.Relation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventItemProduct {
    @Embedded
    public EventItem eventItem;
    @Relation(
            parentColumn = "productId",
            entityColumn = "product_Id"
    )
    public Product product;
}
