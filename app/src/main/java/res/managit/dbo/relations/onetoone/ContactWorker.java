package res.managit.dbo.relations.onetoone;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Worker;

/**
 * Class which is used to Room database library
 * This class is needed in Room library to create one-to-one relationship between Worker and Contact table
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class ContactWorker {
    @Embedded
    public Contact contact;
    @Relation(
            parentColumn = "contactId",
            entityColumn = "contact_Id"
    )
    public Worker worker;
}
