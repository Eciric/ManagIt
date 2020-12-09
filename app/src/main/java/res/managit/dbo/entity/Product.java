package res.managit.dbo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(indices = @Index(value = {"product_name", "amount", "category_Id"} , unique = true))
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Product {
    @PrimaryKey(autoGenerate = true)
    public long productId;
    @ColumnInfo(name = "product_name")
    public String name;
    public int amount;
    public long category_Id;

    public Product(String name, int amount, long category_Id) {
        this.name = name;
        this.amount = amount;
        this.category_Id = category_Id;
    }

}
