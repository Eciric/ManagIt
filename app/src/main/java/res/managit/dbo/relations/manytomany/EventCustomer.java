package res.managit.dbo.relations.manytomany;


import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;

/**
 * Class which is used to Room database library
 * This class is needed in Room library to create many-to-many relationship between Event and Customer table
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class EventCustomer {
    @Embedded public Event event;
    @Relation(
            parentColumn = "eventId",
            entityColumn = "customerId",
            associateBy = @Junction(EventCustomerCross.class)
    )
    public List<Customer> customers;

}
