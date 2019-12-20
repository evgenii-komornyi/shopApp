package Database;

import Product.Product;
import Product.ProductCategory;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryImplTest {
    private Product cowMilk;
    private Product beefMeat;
    private Product cheese;

    @Before
    void initProducts() {
        cowMilk = new Product("Milk", new BigDecimal("1.8"), ProductCategory.MILK);
        cowMilk.setProductDiscount(new BigDecimal("10.4"));
        cowMilk.setProductDescription("Milk from cow, 1L.");

        beefMeat = new Product("Beef", new BigDecimal("5.6"), ProductCategory.MEAT);
        beefMeat.setProductDiscount(new BigDecimal("0.2"));
        beefMeat.setProductDescription("Beef, 1KG.");

        cheese = new Product("Russian Cheese", new BigDecimal("8.5"), ProductCategory.MILK);
        cheese.setProductDiscount(new BigDecimal("0.3"));
        cheese.setProductDescription("Chees from Russia, 1KG.");
    }

    @Test
    void testForSuccessfulInsert() {
        initProducts();

        ProductRepositoryImpl DB = new ProductRepositoryImpl();

        boolean actualResult = DB.insert(cowMilk);

        assertTrue(actualResult);

        DB.dropAll();
    }

    @Test
    void testForUnsuccessfulInsert() {
        initProducts();

        ProductRepositoryImpl DB = new ProductRepositoryImpl();

        DB.insert(cowMilk);

        boolean actualResult = DB.insert(cowMilk);

        assertFalse(actualResult);

        DB.dropAll();
    }

    @Test
    void readAllProducts() {
        initProducts();

        ProductRepositoryImpl DB = new ProductRepositoryImpl();

        DB.insert(cowMilk);
        DB.insert(beefMeat);
        DB.insert(cheese);

        int expectedListSize = 3;
        List<Product> actualListSize = DB.readAllProducts();
        System.out.println("DB size: " + actualListSize.size());

        assertEquals(expectedListSize, actualListSize.size());

        DB.dropAll();
    }

    @Test
    void conditionRead() {
        initProducts();

        ProductRepositoryImpl DB = new ProductRepositoryImpl();

        DB.insert(cowMilk);
        DB.insert(beefMeat);
        DB.insert(cheese);

        List<Product> actualList = DB.conditionRead(null, null, null, ProductCategory.MILK, null);

        for (Product product : actualList) {
            System.out.println(product.getProductName());
            System.out.println("ID: " + product.getProductID());
            if (product.getProductName().contains("Milk") && product.getProductName().contains("Cheese")) {
                System.out.println("Test for condition search has passed.");
            } else {
                System.out.println("Test for condition search has failed.");
            }
        }
    }

    @Test
    void readSingleProduct() {
        initProducts();

        ProductRepositoryImpl DB = new ProductRepositoryImpl();

        DB.insert(cowMilk);
        DB.insert(beefMeat);
        DB.insert(cheese);

        Product expectedProduct = cowMilk;

        Long id = cowMilk.getProductID();

        Product actualProduct = DB.readSingleProduct(id, null, null, null, 1);

        if(expectedProduct.equals(actualProduct)) {
            System.out.println("Test for search product by ID has passed.");
        } else {
            System.out.println("Test for search product by ID has failed.");
        }

        DB.dropAll();
    }

    @Test
    void updateByID() {
        initProducts();

        ProductRepositoryImpl DB = new ProductRepositoryImpl();

        DB.insert(cowMilk);

        BigDecimal expectedNewPrice = new BigDecimal("2.0");
        BigDecimal expectedNewDiscount = new BigDecimal("0.5");
        String expectedNewDescription = "Milk, 1L for test";

        Long id = cowMilk.getProductID();

        DB.update(id, null, null, null, new BigDecimal("2.0"), new BigDecimal("0.5"), "Milk, 1L for test", 1);

        BigDecimal actualNewPrice = cowMilk.getProductPrice();
        BigDecimal actualNewDiscount = cowMilk.getProductDiscount();
        String actualNewDescription = cowMilk.getProductDescription();

        if ((expectedNewPrice.compareTo(actualNewPrice) == 0) && (expectedNewDiscount.compareTo(actualNewDiscount) == 0) && expectedNewDescription.equals(actualNewDescription)) {
            System.out.println("Test for update proudct by ID has passed.");
        } else {
            System.out.println("Test for update proudct by ID has failed.");
            System.out.println("Expected new price: " + expectedNewPrice + ", Actual new price: " + actualNewPrice);
            System.out.println("Expected new discount: " + expectedNewDiscount + ", Actual new discount: " + actualNewDiscount);
            System.out.println("Expected new description: " + expectedNewDescription + ", Actual new description: " + actualNewDescription);
        }

        DB.dropAll();
    }

    @Test
    void updateByCategory() {
        initProducts();

        ProductRepositoryImpl DB = new ProductRepositoryImpl();

        DB.insert(cowMilk);
        DB.insert(cheese);

        ProductCategory category = cowMilk.getProductCategory();

        BigDecimal expectedNewDiscount = new BigDecimal("0.6");

        DB.update(null, null, null, category, null, expectedNewDiscount, null, null);

        BigDecimal actualNewDiscount = cowMilk.getProductDiscount();

        if ((expectedNewDiscount.compareTo(actualNewDiscount) == 0)) {
            System.out.println("Test for update by category has passed.");
        } else {
            System.out.println("Test for update by category has failed.");
            System.out.println("Expected new discount: " + expectedNewDiscount + ", actual new discount: " + actualNewDiscount);
        }
        DB.dropAll();
    }

    @Test
    void delete() {
        initProducts();

        ProductRepositoryImpl DB = new ProductRepositoryImpl();

        DB.insert(cowMilk);
        DB.insert(beefMeat);
        DB.insert(cheese);

        Long id = cowMilk.getProductID();

        boolean isDelete = DB.delete(id, null, null, null, 1);

        assertTrue(isDelete);

        int expectedSize = 2;
        List<Product> sizeAfterDelete = DB.readAllProducts();

        if (expectedSize == sizeAfterDelete.size()) {
            System.out.println("Test for remove product from database has passed.");
        } else {
            System.out.println("Test for remove product from database has failed.");
        }

        DB.dropAll();
    }
}