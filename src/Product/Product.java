package Product;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private static Long countID = Long.valueOf(0);

    private String productName;
    private Long productID;
    private BigDecimal productPrice;
    private ProductCategory productCategory;

    private BigDecimal productDiscount = new BigDecimal("0.0");
    private String productDescription = "";

    public Product(String productName, BigDecimal productPrice, ProductCategory productCategory) {
        this.productID = getCountID();
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
    }

    private Long getCountID() {
        return countID++;
    }

    public BigDecimal calculateActualPrice() {
        BigDecimal actualPrice = productPrice.subtract(productPrice.multiply(productDiscount));
        return actualPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productID=" + productID +
                ", productPrice=" + productPrice +
                ", productCategory=" + productCategory +
                ", productDiscount=" + productDiscount +
                ", actualPrice=" + calculateActualPrice() +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productName, product.productName) &&
                Objects.equals(productID, product.productID) &&
                Objects.equals(productPrice, product.productPrice) &&
                productCategory == product.productCategory &&
                Objects.equals(productDiscount, product.productDiscount) &&
                Objects.equals(productDescription, product.productDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productID, productPrice, productCategory, productDiscount, productDescription);
    }

    public String getProductName() {
        return productName;
    }

    public Long getProductID() {
        return productID;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public BigDecimal getProductDiscount() {
        return productDiscount;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductDiscount(BigDecimal productDiscount) {
        this.productDiscount = productDiscount;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
