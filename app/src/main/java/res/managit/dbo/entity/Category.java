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
@Entity(indices = @Index(value = "category_name", unique = true))
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Category {
    @PrimaryKey(autoGenerate = true)
    public long categoryId;
    @ColumnInfo(name = "category_name")
    public String name;

    /**
     * Creating a class object which represents one entry in Category table
     *
     * @param name category name - It have to be unique in database
     */
    public Category(String name) {
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}