package res.managit.dbo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class which is used to Room database library.
 * It represents a table in database with the same name.
 */

@Entity(indices = @Index(value = {"customer_name", "contact_Id"}, unique = true))
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public long customerId;
    @ColumnInfo(name = "customer_name")
    public String name;
    public long contact_Id;

    /**
     * Creating a class object which represents one entry in Customer table
     *
     * @param name       name of customer
     * @param contact_Id Foreign key connecting customer with a dedicated to him Contact table identifier
     */
    public Customer(String name, long contact_Id) {
        this.name = name;
        this.contact_Id = contact_Id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public long getContact_Id() {
        return contact_Id;
    }
}
