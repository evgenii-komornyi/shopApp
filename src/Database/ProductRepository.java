package Database;

import Product.ProductCategory;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository<T> {
    boolean insert(T product);

    List<T> readAllProducts();

    List<T> conditionRead(@Nullable Long productID, @Nullable String productName, @Nullable BigDecimal productPrice, @Nullable ProductCategory productCategory, @Nullable Integer limit);

    T readSingleProduct(@Nullable Long productID, @Nullable String productName, @Nullable BigDecimal productPrice, @Nullable ProductCategory productCategory, @Nullable Integer limit);

    boolean update(@Nullable Long productID, @Nullable String productName, @Nullable BigDecimal productPrice, @Nullable ProductCategory productCategory, @Nullable BigDecimal newProductPrice, @Nullable BigDecimal newProductDiscount, @Nullable String newProductDescription, @Nullable Integer limit);

    boolean delete(@Nullable Long productID, @Nullable String productName, @Nullable BigDecimal productPrice, @Nullable ProductCategory productCategory, @Nullable Integer limit);
}
