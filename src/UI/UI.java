package UI;

import Errors.ItemNotFoundException;
import Product.Product;
import Product.ProductCategory;
import Service.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    private static Service productService = new Service();
    private static List<ProductCategory> availableCategories = new ArrayList<>();

    private static void init() {
        availableCategories.add(ProductCategory.ALCOHOL);
        availableCategories.add(ProductCategory.BREAD);
        availableCategories.add(ProductCategory.FISH);
        availableCategories.add(ProductCategory.FRUITS);
        availableCategories.add(ProductCategory.MEAT);
        availableCategories.add(ProductCategory.MILK);
        availableCategories.add(ProductCategory.SOFT_DRINKS);
        availableCategories.add(ProductCategory.SWEETS);
        availableCategories.add(ProductCategory.VEGETABLES);
    }

    private static void greatings() {
        try {
            File greating = new File("src/UI/Greating");
            Scanner reader = new Scanner(greating);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    private static void readMainMenuCommands() {
        try {
            File commands = new File("src/UI/MainMenuCommands");
            Scanner reader = new Scanner(commands);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    private static void readCommandsForPrint() {
        try {
            File commands = new File("src/UI/GetMenuCommands");
            Scanner reader = new Scanner(commands);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    private static void readCommandsForUpdate() {
        try {
            File commands = new File("src/UI/UpdateMenuCommands");
            Scanner reader = new Scanner(commands);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    private static void addNewProduct() throws IllegalArgumentException, IOException, IndexOutOfBoundsException {
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);

        try {
            System.out.println("Product name: ");
            String productName = reader.readLine();

            if (productName.isEmpty()) {
                System.out.println("Product name can't be empty.");
                return;
            }

            System.out.println("Product price: ");
            String strProductPrice = reader.readLine();
            BigDecimal productPrice;
            if (strProductPrice.isEmpty()) {
                System.out.println("Product price can't be empty.");
                return;
            } else {
                productPrice = new BigDecimal(strProductPrice);
            }

            System.out.println("Available product categories: ");
            for (ProductCategory categories : availableCategories) {
                System.out.println(availableCategories.indexOf(categories) + " - " + categories.name());
            }

            System.out.println("Enter product category(or 0-8): ");
            String productCategory = reader.readLine().toUpperCase();

            switch (productCategory) {
                case "0":
                    productCategory = "ALCOHOL";
                    break;
                case "1":
                    productCategory = "BREAD";
                    break;
                case "2":
                    productCategory = "FISH";
                    break;
                case "3":
                    productCategory = "FRUITS";
                    break;
                case "4":
                    productCategory = "MEAT";
                    break;
                case "5":
                    productCategory = "MILK";
                    break;
                case "6":
                    productCategory = "SOFT_DRINKS";
                    break;
                case "7":
                    productCategory = "SWEETS";
                    break;
                case "8":
                    productCategory = "VEGETABLES";
                    break;
                default:
                    productCategory = productCategory;
            }

            if (productCategory.isEmpty()) {
                System.out.println("Product category can't be empty.");
                return;
            }

            System.out.println("Product discount: ");
            String productDiscount = reader.readLine();
            productDiscount = (productDiscount.isEmpty() ? "0.0" : productDiscount);

            System.out.println("Product description: ");
            String productDescription = reader.readLine();

            boolean successAdd = productService.addProduct(productName, productPrice, productCategory, new BigDecimal(productDiscount), productDescription);

            if (successAdd) {
                System.out.println("Product " + productName + " added");
            } else {
                System.out.println("Database not answer.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Price and discount can't be non numeric, or category doesn't exist.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printProduct(Product product) throws IndexOutOfBoundsException {
        try {
            String EURO = "\u20ac";
            DecimalFormat df = new DecimalFormat("#.##");
            System.out.println("ID: " + product.getProductID() +
                    ", Product: " + product.getProductName() +
                    ", Regular price: " + df.format(product.getProductPrice()) + EURO + ", " +
                    "Discount: " + df.format(product.getProductDiscount().multiply(BigDecimal.valueOf(100))) + "%, " +
                    "Actual price: " + df.format(product.calculateActualPrice()) + EURO + ", " +
                    "Category: " + product.getProductCategory() + ", " +
                    "Description: " + product.getProductDescription() + ".");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your product has not found.");
        }
    }

    private static void printProductsList(List<Product> list) {
        for (Product product : list) {
            printProduct(product);
        }
    }

    private static void findProductByCategory() {
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);

        System.out.println("Available product categories: ");
        for (ProductCategory categories : availableCategories) {
            System.out.println(availableCategories.indexOf(categories) + " - " + categories.name());
        }

        System.out.println("Enter product category(or 0-8): ");
        try {
            String productCategory = reader.readLine().toUpperCase();

            switch (productCategory) {
                case "0":
                    productCategory = "ALCOHOL";
                    break;
                case "1":
                    productCategory = "BREAD";
                    break;
                case "2":
                    productCategory = "FISH";
                    break;
                case "3":
                    productCategory = "FRUITS";
                    break;
                case "4":
                    productCategory = "MEAT";
                    break;
                case "5":
                    productCategory = "MILK";
                    break;
                case "6":
                    productCategory = "SOFT_DRINKS";
                    break;
                case "7":
                    productCategory = "SWEETS";
                    break;
                case "8":
                    productCategory = "VEGETABLES";
                    break;
                default:
                    productCategory = productCategory;
            }

            printProductsList(productService.getProductByCategory(ProductCategory.valueOf(productCategory)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Category not exist");
        }
    }

    private static void findProductByID() throws IndexOutOfBoundsException, NumberFormatException {
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);

        System.out.println("Enter product ID: ");
        try {
            Long productID = Long.valueOf(reader.readLine());

            printProduct(productService.getProductID(productID));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Your ID can't be empty or non numeric characters");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Your product doesn't exist");
        }
    }

    private static void updateProductByCategory() throws IOException, IllegalArgumentException, IndexOutOfBoundsException, NullPointerException {
        InputStreamReader intputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(intputStream);

        System.out.println("Available product categories: ");
        for (ProductCategory categories : availableCategories) {
            System.out.println(availableCategories.indexOf(categories) + " - " + categories.name());
        }

        try {
            System.out.println("Enter category (0-8): ");
            String productCategory = reader.readLine().toUpperCase();

            switch (productCategory) {
                case "0":
                    productCategory = "ALCOHOL";
                    break;
                case "1":
                    productCategory = "BREAD";
                    break;
                case "2":
                    productCategory = "FISH";
                    break;
                case "3":
                    productCategory = "FRUITS";
                    break;
                case "4":
                    productCategory = "MEAT";
                    break;
                case "5":
                    productCategory = "MILK";
                    break;
                case "6":
                    productCategory = "SOFT_DRINKS";
                    break;
                case "7":
                    productCategory = "SWEETS";
                    break;
                case "8":
                    productCategory = "VEGETABLES";
                    break;
                default:
                    productCategory = productCategory;
            }

            System.out.println("Enter new discount: ");
            String newDiscountProduct = reader.readLine();
            BigDecimal newDiscount;
            newDiscount = (newDiscountProduct.isEmpty() ? null : new BigDecimal(newDiscountProduct));

            searchProductByCategory(productCategory, newDiscount);
        } catch (NullPointerException e) {
            System.out.println("Discount can't be empty");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Discount must be from 0.0 to 1.0 and can't be empty");
        } catch (IllegalArgumentException e) {
            System.out.println("Discount can't be non numeric, or category not exist");
        }
    }

    private static boolean searchProductByCategory(String searchCategory, BigDecimal newDiscount)  {
        if (!searchCategory.isEmpty()) {
            return productService.updateProductsByCategory(ProductCategory.valueOf(searchCategory), newDiscount);
        } else {
            System.out.println("You must enter category:");
            return false;
        }
    }

    private static void updateProductByID() throws IOException, IllegalArgumentException {
        InputStreamReader intputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(intputStream);

        try {
            System.out.println("Enter ID: ");
            Long productID = Long.valueOf(reader.readLine());

            System.out.println("Enter new price: ");
            String newPrice = reader.readLine();
            BigDecimal price;
            price = (newPrice.isEmpty() ? null : new BigDecimal(newPrice));

            System.out.println("Enter new discount: ");
            String newDiscountProduct = reader.readLine();
            BigDecimal newDiscount;
            newDiscount = (newDiscountProduct.isEmpty() ? null : new BigDecimal(newDiscountProduct));

            searchByID(productID, price, newDiscount);
        } catch (NumberFormatException e) {
            System.out.println("ID can't be empty and non numeric, Price or Discount can't be non numeric");
        }
    }

    private static boolean searchByID(Long searchID, BigDecimal newPrice, BigDecimal newDiscount) {
        if (searchID != null) {
            return productService.updateSingleProductByID(searchID, newPrice, newDiscount);
        } else {
            System.out.println("ID can't be empty.");
            return false;
        }
    }

    private static void deleteProduct() throws NumberFormatException {
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);

        System.out.println("Enter product ID: ");
        try {
            Long productID = Long.valueOf(reader.readLine());
            if (productService.deleteProductByID(productID)) {
                System.out.println("Your product was successfuly deleted.");
            } else {
                System.out.println("Your product not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Your ID can't be empty or non numeric characters");
        }
    }

    private static void dropDatabase() {
        productService.cleanDatabase();
    }

    public static void main(String[] args) {
        init();
        BufferedReader keyPress = new BufferedReader(new InputStreamReader(System.in));
        boolean exitMainMenu = false;
        boolean exitSubMenu;
        greatings();

        while (exitMainMenu == false) {
            readMainMenuCommands();
            try {
                System.out.println("Type command: ");
                String commandForMainMenu = keyPress.readLine().toLowerCase();

                if (commandForMainMenu.equals("1") || commandForMainMenu.equals("add")) {
                    addNewProduct();
                } else if (commandForMainMenu.equals("2") || commandForMainMenu.equals("get")) {
                    exitSubMenu = false;

                    while (exitSubMenu == false) {
                        readCommandsForPrint();
                        System.out.println("Get product(s): ");
                        String commandForPrint = keyPress.readLine().toLowerCase();

                        if (commandForPrint.equals("1") || commandForPrint.equals("all")) {
                            List<Product> list = productService.getAllProducts();
                            printProductsList(list);
                        } else if (commandForPrint.equals("2") || commandForPrint.equals("byid")) {
                            findProductByID();
                        } else if (commandForPrint.equals("3") || commandForPrint.equals("bycat")) {
                            findProductByCategory();
                        } else if (commandForPrint.equals("0") || commandForPrint.equals("up")) {
                            exitSubMenu = true;
                        } else if (commandForPrint.equals("?") || commandForPrint.equals("help")) {
                            System.out.println("(0), or up - back to the main menu;");
                            System.out.println("(1), or all - get all information products in the database;");
                            System.out.println("(2), or byid - get information about product in the database by ID;");
                        } else {
                            System.out.println("This command isn't support. Please read help documentation. (For help type \"?\", or \"help\")");
                        }
                    }
                } else if (commandForMainMenu.equals("3") || commandForMainMenu.equals("update")) {
                    exitSubMenu = false;
                    while (exitSubMenu == false) {
                        readCommandsForUpdate();
                        System.out.println("Update product(s) by: ");
                        String commandForUpdate = keyPress.readLine().toLowerCase();

                        if (commandForUpdate.equals("0") || commandForUpdate.equals("up")) {
                            exitSubMenu = true;
                        } else if (commandForUpdate.equals("1") || commandForUpdate.equals("bycat")) {
                            updateProductByCategory();
                        } else if (commandForUpdate.equals("2") || commandForUpdate.equals("byid")) {
                            updateProductByID();
                        } else if (commandForUpdate.equals("?") || commandForUpdate.equals("help")) {
                            System.out.println("(0), or up - back to main menu;");
                            System.out.println("(1), or bycat - update information of products in the database by category;");
                            System.out.println("(2), or byid - update information of product in the database by ID;");
                        } else {
                            System.out.println("This command isn't support. Please read help documentation. (For help type \"?\", or \"help\")");
                        }
                    }
                } else if (commandForMainMenu.equals("4") || commandForMainMenu.equals("delete")) {
                    deleteProduct();
                } else if (commandForMainMenu.equals("5") || commandForMainMenu.equals("generate")) {
                    productService.addProduct("Cow Milk", new BigDecimal("2.0"), "MILK", new BigDecimal("0.05"), "Milk 1L.");
                    productService.addProduct("Pork", new BigDecimal("5.3"), "MEAT", new BigDecimal("0.2"), "Pork 1Kg.");
                    productService.addProduct("Beef", new BigDecimal("5.9"), "MEAT", new BigDecimal("0.3"), "Beef 1Kg.");
                    productService.addProduct("Beer", new BigDecimal("1.42"), "ALCOHOL", new BigDecimal("0.25"), "Beer 0.7L.");
                    productService.addProduct("Whiskey", new BigDecimal("17.1"), "ALCOHOL", new BigDecimal("0.1"), "Whiskey 1L.");
                    productService.addProduct("Tiramissu", new BigDecimal("4.14"), "SWEETS", new BigDecimal("0.2"), "Cake \"Tiramissu\" 300G.");
                    productService.addProduct("Potato", new BigDecimal("0.85"), "VEGETABLES", new BigDecimal("0.05"), "Potato 1Kg.");
                    productService.addProduct("Salmon", new BigDecimal("24.0"), "FISH", new BigDecimal("0.4"), "SAlmon 1Kg.");
                    productService.addProduct("Herring", new BigDecimal("15.5"), "FISH", new BigDecimal("0.02"), "Herring 1Kg.");
                    productService.addProduct("White bread", new BigDecimal("1.0"), "BREAD", new BigDecimal("0.0"), "White bread 1stk.");
                    productService.addProduct("Goat Milk", new BigDecimal("2.9"), "MILK", new BigDecimal("0.05"), "Milk 1L.");
                    productService.addProduct("Chocolate", new BigDecimal("0.95"), "SWEETS", new BigDecimal("0.09"), "Chocolate 1stk.");
                    productService.addProduct("Coca-cola", new BigDecimal("2.65"), "SOFT_DRINKS", new BigDecimal("0.2"), "Coca-cola 2L.");
                    productService.addProduct("Apples \"Alvu\"", new BigDecimal("1.00"), "FRUITS", new BigDecimal("0.0"), "Apples 1KG.");
                } else if (commandForMainMenu .equals("6") || commandForMainMenu.equals("drop")) {
                    System.out.println("Do you really want to drop all from the database?");
                    System.out.println("Type: yes || no");
                    String commandForDropDatabase = keyPress.readLine().toLowerCase();

                    if (commandForDropDatabase.equals("yes")) {
                        dropDatabase();
                        System.out.println("Database has been successfuly cleaned.");
                    } else if (commandForDropDatabase.equals("no")) {
                        continue;
                    }
                } else if (commandForMainMenu.equals("0") || commandForMainMenu.equals("exit")) {
                    exitMainMenu = true;
                } else if (commandForMainMenu.equals("?") || commandForMainMenu.equals("help")) {
                    System.out.println("(0), or exit - to exit from the program;");
                    System.out.println("(1), or add - to add new product;");
                    System.out.println("(2), or get - to get information from the databese;");
                    System.out.println("(3), or update - to update the database information;");
                    System.out.println("(4), or delete - to delete a product by ID;");
                    System.out.println("(5), or generate - to fill the database test of the products;");
                    System.out.println("(6), or drop - to drop all from the database;");
                } else {
                    System.out.println("This command isn't support. Please read help documentation. (For help type \"?\", or \"help\")");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}