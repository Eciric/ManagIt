package res.managit.dbo.relations.onetoone;

import androidx.room.Embedded;
import androidx.room.Relation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;

/**
 * Class which is used to Room database library
 * This class is needed in Room library to create one-to-one relationship between EventItem and Product table
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventItemProduct {
    @Embedded
    public EventItem eventItem;
    @Relation(
            parentColumn = "product_Id",
            entityColumn = "productId"
    )
    public Product product;
}
