package Database;

import Product.Product;
import Product.ProductCategory;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository<Product> {
    private static List <Product> database = new ArrayList<>();

    @Override
    public boolean insert(Product product) {
        if (!database.contains(product)) {
            database.add(product);
            System.out.println("Your product has successfuly saved.");
            return true;
        } else {
            System.out.println("Your product exist already.");
            return false;
        }
    }

    public List<Product> readAllProducts() {
        return database;
    }

    @Override
    public List<Product> conditionRead(@Nullable Long productID, @Nullable String productName, @Nullable BigDecimal productPrice, @Nullable ProductCategory productCategory, @Nullable Integer limit) {
        List<Product> listOfFoundProducts = new ArrayList<>();
        Long sProductID;
        String sProductName;
        BigDecimal sProductPrice;
        String sProductCategory;

        for (Product product : database) {
            sProductID = ((productID != null) ? productID : product.getProductID());
            sProductName = ((productName != null) ? productName : product.getProductName());
            sProductPrice = ((productPrice != null) ? productPrice : product.getProductPrice());
            sProductCategory = ((productCategory != null) ? String.valueOf(productCategory) : String.valueOf(product.getProductCategory()));
            if (sProductID.compareTo(product.getProductID()) == 0 && sProductName == product.getProductName() && sProductPrice.compareTo(product.getProductPrice()) == 0 && sProductCategory.equals(String.valueOf(product.getProductCategory()))) {
                listOfFoundProducts.add(product);
                if (limit != null && listOfFoundProducts.size() >= limit) {
                    break;
                }
            }
        }

        return listOfFoundProducts;
    }

    @Override
    public Product readSingleProduct(@Nullable Long productID, @Nullable String productName, @Nullable BigDecimal productPrice, @Nullable ProductCategory productCategory, @Nullable Integer limit) {
        List<Product> list = conditionRead(productID, productName, productPrice, productCategory, limit);
        Product product = null;
        if (list != null) {
            product = list.get(0);
        }
        return product;
    }
    
    @Override
    public boolean update(@Nullable Long productID, @Nullable String productName, @Nullable BigDecimal productPrice, @Nullable ProductCategory productCategory, @Nullable BigDecimal newProductPrice, @Nullable BigDecimal newProductDiscount, @Nullable String newProductDescription, @Nullable Integer limit) {
        List<Product> list = conditionRead(productID, productName, productPrice, productCategory, limit);

        for (Product product : list) {
            if (newProductPrice != null) {
                product.setProductPrice(newProductPrice);
            }

            if (newProductDiscount != null) {
                product.setProductDiscount(newProductDiscount);
            }

            if (newProductDescription != null) {
                product.setProductDescription(newProductDescription);
            }
        }
        return true;
    }

    @Override
    public boolean delete(@Nullable Long productID, @Nullable String productName, @Nullable BigDecimal productPrice, @Nullable ProductCategory productCategory, @Nullable Integer limit) {
        List<Product> list = conditionRead(productID, productName, productPrice, productCategory, limit);

        for (Product product : list) {
            database.remove(product);
        }

        if (list.size() == 0) {
            return false;
        }

        return true;
    }

    public boolean dropAll() {
        if (!database.isEmpty()) {
            database.clear();
            return true;
        } else {
            return false;
        }
    }
}
