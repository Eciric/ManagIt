package res.managit.dbo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    public Category(String name) {
        this.name = name;
    }

}