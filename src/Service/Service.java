package Service;

import Product.ProductCategory;
import Database.ProductRepositoryImpl;
import Product.Product;

import java.math.BigDecimal;
import java.util.List;

public class Service {
    private ProductRepositoryImpl DB = new ProductRepositoryImpl();

    private boolean addProductToDB(Product product) {
        if (DB.insert(product)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addProduct(String productName, BigDecimal productPrice, String productCategory, BigDecimal productDiscount, String productDescription) {
        if (productDiscount.compareTo(BigDecimal.valueOf(0)) < 0 || productDiscount.compareTo(BigDecimal.valueOf(1)) > 0) {
            throw new IndexOutOfBoundsException("Discount must be from 0 to 1, or empty.");
        } else {
            Product product = new Product(productName, productPrice, ProductCategory.valueOf(productCategory));

            if (productDiscount != null) {
                product.setProductDiscount(productDiscount);
            }

            if (!productDescription.isEmpty()) {
                product.setProductDescription(productDescription);
            }

            return addProductToDB(product);
        }
    }

    public List<Product> getAllProducts() {
        return DB.readAllProducts();
    }

    public List<Product> getConditionProducts(Long productId, String productName, BigDecimal productPrice, ProductCategory productCategory) {
        return DB.conditionRead(productId, productName, productPrice, productCategory, null);
    }

    public Product getProductID(Long productId) {
        return DB.readSingleProduct(productId, null, null, null, 1);
    }

    public List<Product> getProductByCategory(ProductCategory productCategory) {
        return DB.conditionRead(null, null, null, productCategory, null);
    }

    public boolean updateProductsByCategory(ProductCategory productCategory, BigDecimal newProductDiscount) {
        if (newProductDiscount != null && (newProductDiscount.compareTo(BigDecimal.valueOf(0)) < 0 || newProductDiscount.compareTo(BigDecimal.valueOf(1)) > 0)) {
            throw new IndexOutOfBoundsException("Discount must be from 0 to 1, or empty.");
        } else {
            return DB.update(null, null, null, productCategory, null, newProductDiscount, null, null);
        }
    }

    public boolean updateSingleProductByID(Long productID, BigDecimal newPrice, BigDecimal newProductDiscount) {
        if (newProductDiscount != null && (newProductDiscount.compareTo(BigDecimal.valueOf(0)) < 0 || newProductDiscount.compareTo(BigDecimal.valueOf(1)) > 0)) {
            throw new IndexOutOfBoundsException("Discount must be from 0 to 1, or empty.");

        } else {
            return DB.update(productID, null, null, null, newPrice, newProductDiscount, null, 1);
        }
    }

    public boolean deleteProductByID(Long productId) {
        return DB.delete(productId, null, null, null, 1);
    }

    public boolean cleanDatabase() {
        return DB.dropAll();
    }
}

