package res.managit.dbo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import res.managit.dbo.entity.Product;
import res.managit.dbo.relations.manytoone.CategoryProduct;

/**
 * Class which is a Data Access Object for Product table in Room database
 */
@Dao
public interface ProductDao {

    /**
     * Function which represents specific SQL query
     *
     * @return list of all entries in Product table
     */
    @Query("SELECT * FROM Product")
    List<Product> getAll();

    /**
     * Function which represents specific SQL query
     *
     * @param id identity number of searched product
     * @return Product object with specific id
     */
    @Query("SELECT * FROM Product where productId like :id")
    Product getProductById(Long id);

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched product
     * @return Product object with specific name
     */
    @Query("SELECT * FROM Product WHERE product_name LIKE :name")
    Product getProductByName(String name);

    /**
     * Function which represents specific SQL query
     *
     * @return List of CategoryProduct objects - class connecting the category, with all products which are in it included
     */
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select * FROM Category")
    List<CategoryProduct> getCategoryAndProduct();


    /**
     * Function which represents specific SQL query
     *
     * @return list of product objects which amount is less than 10
     */
    @Query("SELECT * FROM Product WHERE amount < 10")
    List<Product> gatProductsAmountLessTen();

    /**
     * Function which insert new entry to Product table
     *
     * @param products product objects to be inserted into Product table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProduct(Product... products);

    /**
     * Function which delete specific entry in Product table
     *
     * @param products product object to be deleted from Product table
     */
    @Delete
    void deleteProduct(Product... products);

    /**
     * Function which delete all entries in Product table
     */
    @Query("DELETE FROM product")
    void deleteAll();

    /**
     * Function which sets generating Id back to 1
     */
    @Query("DELETE FROM sqlite_sequence WHERE name = 'Product'")
    void deleteNumber();

    /**
     * Function which update specific entry in Product table
     *
     * @param product product object to be updated in Product table
     */
    @Update
    void updateProduct(Product product);
}
