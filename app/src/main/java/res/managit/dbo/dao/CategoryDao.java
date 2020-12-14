package res.managit.dbo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import res.managit.dbo.entity.Category;


@Dao
public interface CategoryDao {
    //zwraca wszystkie kategorie
    @Query("SELECT * FROM category")
    List<Category> getAll();

    //zwraca kategorie o podanej nazwie
    @Query("SELECT * FROM category where category_name LIKE :name")
    public Category getCategoryByName(String name);

    //wstawia nowa kategorie
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category... categories);

    //usuwa kategorie
    @Delete
    void deleteCategory(Category... categories);

    //modyfikuje kategorie
    @Update
    void updateCategory(Category category);
}
