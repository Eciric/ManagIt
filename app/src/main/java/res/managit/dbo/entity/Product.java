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
@Entity(indices = @Index(value = {"product_name", "amount", "category_Id"}, unique = true))
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

    /**
     * Creating a class object which represents one entry in Product table
     *
     * @param name        name of product
     * @param amount      amount of product in stock
     * @param category_Id Foreign key connecting product with a Category table identifier
     */
    public Product(String name, int amount, long category_Id) {
        this.name = name;
        this.amount = amount;
        this.category_Id = category_Id;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public long getCategory_Id() {
        return category_Id;
    }
}
