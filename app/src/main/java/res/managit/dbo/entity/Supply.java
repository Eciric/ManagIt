package res.managit.dbo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(indices = @Index(value = {"supply_name", "contact_Id"} , unique = true))
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
