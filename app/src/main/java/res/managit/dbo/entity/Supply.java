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
@Entity(indices = @Index(value = {"supply_name", "contact_Id"}, unique = true))
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Supply {
    @PrimaryKey(autoGenerate = true)
    public long supplyId;
    @ColumnInfo(name = "supply_name")
    public String name;
    public long contact_Id;

    /**
     * Creating a class object which represents one entry in Supply table
     *
     * @param name       name of supplier
     * @param contact_Id Foreign key connecting supplier with a dedicated to him Contact table identifier
     */
    public Supply(String name, long contact_Id) {
        this.name = name;
        this.contact_Id = contact_Id;
    }

    public long getSupplyId() {
        return supplyId;
    }

    public String getName() {
        return name;
    }

    public long getContact_Id() {
        return contact_Id;
    }
}
