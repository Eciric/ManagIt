package res.managit.dbo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(indices = @Index(value = {"customer_name", "contact_Id"} , unique = true))
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

    public Customer(String name, long contact_Id) {
        this.name = name;
        this.contact_Id = contact_Id;
    }
}
