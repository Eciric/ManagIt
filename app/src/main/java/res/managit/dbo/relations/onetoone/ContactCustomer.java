package res.managit.dbo.relations.onetoone;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Product;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ContactCustomer {
    @Embedded
    public Contact contact;
    @Relation(
            parentColumn = "contactId",
            entityColumn = "contact_Id"
    )
    public Customer customer;
}