package res.managit.dbo.relations.manytoone;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Product;

/**
 * Class which is used to Room database library
 * This class is needed in Room library to create many-to-one relationship between Category and Product table
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class CategoryProduct {
    @Embedded public Category category;
    @Relation(
            parentColumn = "categoryId",
            entityColumn = "category_Id"
    )
    public List<Product> products;
}
