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

@Dao
public interface ProductDao {

    //zwraca wszystkie produkty
    @Query("SELECT * FROM Product")
    public List<Product> getAll();

    @Query("SELECT * FROM Product where productId like :id")
    public Product getProductById(Long id);

    //zwraca wszystkie produkty o podanej nazwie
    @Query("SELECT * FROM Product WHERE product_name LIKE :name")
    public Product getProductByName(String name);

    //zwraca wszystkie kategorie i produkty jakie do nich nalezą
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select * FROM Category")
    public List<CategoryProduct> getCategoryAndProduct();

    //zwraca kategorie i produkty do niej nalezace(jednym z nich jest ten z name)
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Category INNER JOIN Product Where category_Id = categoryId and product_name LIKE :name ")
    public CategoryProduct getCategoryAndProductByName(String name);


    //wstawianie produktu
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProduct(Product... products);

    //usuwanie produktu
    @Delete
    void deleteProduct(Product... products);

    @Query("DELETE FROM product")
    void deleteAll();

    //modyfikowanie produktu
    @Update
    void updateProduct(Product product);
}
