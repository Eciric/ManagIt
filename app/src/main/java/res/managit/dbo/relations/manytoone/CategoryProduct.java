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
