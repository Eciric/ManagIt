package res.managit.dbo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import res.managit.dbo.entity.Category;

/**
 * Class which is a Data Access Object for Category table in Room database
 */
@Dao
public interface CategoryDao {
    /**
     * Function which represents specific SQL query
     *
     * @return list of all entries in Category table
     */
    @Query("SELECT * FROM category")
    List<Category> getAll();

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched category
     * @return Category object with specific name
     */
    @Query("SELECT * FROM category where category_name LIKE :name")
    Category getCategoryByName(String name);

    /**
     * Function which represents specific SQL query
     *
     * @param id identity number of searched category
     * @return Category object with specific id
     */
    @Query("SELECT * FROM category where categoryId LIKE :id")
    Category getCategoryById(long id);

    /**
     * Function which insert new entry to Category table
     *
     * @param categories category objects to be inserted into Category table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category... categories);

    /**
     * Function which delete specific entry in Category table
     *
     * @param categories category object to be deleted from Category table
     */
    @Delete
    void deleteCategory(Category... categories);

    /**
     * Function which delete all entries in Category table
     */
    @Query("DELETE FROM category")
    void deleteAll();

    /**
     * Function which sets generating Id back to 1
     */
    @Query("DELETE FROM sqlite_sequence WHERE name = 'Category'")
    void deleteNumber();

    /**
     * Function which update specific entry in Category table
     *
     * @param category category object to be updated in Category table
     */
    @Update
    void updateCategory(Category category);
}
