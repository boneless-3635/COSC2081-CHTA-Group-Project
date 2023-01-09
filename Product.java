/*
RMIT University Vietnam
Course: COSC2081 Programming 1
Semester: 2022C
Assessment: Assignment 3
Authors: Nguyen Quoc An, Pham Minh Hoang, Tran Gia Minh Thong, Yoo Christina
ID: s3938278, s3930051, s3924667, s3938331
Acknowledgement:
https://stackoverflow.com/questions/64678515/how-to-delete-a-specific-row-from-a-csv-file-using-search-string
https://www.geeksforgeeks.org/stream-filter-java-examples/
https://www.youtube.com/watch?v=ij07fW5q4oo
https://www.geeksforgeeks.org/try-with-resources-feature-in-java/
https://www.tutorialspoint.com/how-to-overwrite-a-line-in-a-txt-file-using-java

*/

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Product {
    private final String id;
    private final String NAME;
    private int price;
    private String category;
    private int numberSold;
    private static final ArrayList<Product> productArrayList = new ArrayList<>();

    public Product(String id, String NAME, int price, String category, int numberSold) {
        this.id = id;
        this.NAME = NAME;
        this.price = price;
        this.category = category;
        this.numberSold = numberSold;
    }

    public static void initializeProduct() throws IOException {
        productArrayList.clear();
        Scanner fileScanner = new Scanner(Paths.get("product.txt"));
        while (fileScanner.hasNext()) {
//            read per line and split line into array
            List<String> productValues = Arrays.asList(fileScanner.nextLine().split(","));

//                store products in arraylist
            productArrayList.add(new Product(productValues.get(0), productValues.get(1),
                    parseInt(productValues.get(2)), productValues.get(3), parseInt(productValues.get(4))));
        }
    }

    public static void addProduct() throws IOException {
        while (true) {
            boolean errorFree = true;
            boolean categoryMatched = false;
            System.out.println("Create new product: ");
            StringBuilder errorMessage = new StringBuilder("Error: \n");
            Scanner userInput = new Scanner(System.in);

            System.out.println("Product name: ");
            String productName = userInput.nextLine();

            System.out.println("Price: ");
            String productPrice = userInput.nextLine();

            System.out.println("Category: ");
            String productCategory = userInput.nextLine();

            if (!validateInput(productName, "^[a-zA-Z0-9 ]{3,}$")) {
                errorMessage.append("Invalid user name (only letters and digits, at least 3 characters)");
                errorFree = false;
            }

            //            To check if product already existed because name is unique
            for (Product productLoop : productArrayList) {
                if (productName.equalsIgnoreCase(productLoop.getNAME())) {
                    errorMessage.append("Product already exists");
                    errorFree = false;
                    break;
                }
            }

            if (validateInput(productPrice, "^[0-9 ]$+")) {
                if (Integer.parseInt(productPrice) < 1000) {
                    errorMessage.append("Invalid price (must be a number at least 1000 VND)");
                    errorFree = false;
                }
            } else {
                errorMessage.append("Invalid price (must be a number at least 1000 VND)");
                errorFree = false;
            }

//            Categories can only be chosen from ones that already exists
            for (String functionLoop : Category.getCategoryArrayList()) {
                if (functionLoop.equalsIgnoreCase(productCategory)) {
                    categoryMatched = true;
                    break;
                }
            }

            if (!categoryMatched) {
                errorMessage.append("Category does not exist");
                errorFree = false;
            }

            if (errorFree) {
//                    generate product id
                String productID = UUID.randomUUID().toString();
//                    write info to file
                PrintWriter pw = new PrintWriter(new FileWriter("product.txt", true));
                pw.println(productID + "," + productName + "," + productPrice + "," + productCategory + "," + 0);
                pw.close();
                System.out.println("Successfully added product");
                break;
            } else {
                System.out.println(errorMessage+"\n");
            }
        }
        initializeProduct();
    }

    public static void removeProduct() throws IOException {

        System.out.println("Please enter the name of the product you want to delete: ");
        Scanner userInput = new Scanner(System.in);
        String productName = userInput.nextLine();
        if (lookupProductName(productName)) {
            Utility.deleteRowTextFile(productName,1, "product.txt");
        } else {
            System.out.println("No product found, please try again");
            removeProduct();
        }
        initializeProduct();
    }

    public static void updatePrice() throws IOException {
        System.out.println("Please enter the name of the product you want to update the price: ");
        Scanner userInput = new Scanner(System.in);
        String productName = userInput.nextLine();
        if (lookupProductName(productName)) {
            while (true) {
                System.out.println("Please enter the new price of product " + productName + ":");
                String productNewPrice = userInput.nextLine();

                if (validateInput(productNewPrice, "^[0-9]$+")) {
                    if (Integer.parseInt(productNewPrice) > 1000) {
                        Utility.updateTextFile(productName, productNewPrice, 1, 2, "product.txt");
                        break;
                    }
                } else {
                    System.out.println("Error: Invalid price (must be a number at least 1000 VND)");
                }
            }
        } else {
            System.out.println("No product found, please try again");
            updatePrice();
        }
        initializeProduct();
    }

    public static ArrayList<Product> getMostPopularProducts() {
        ArrayList<Product> mostPopularProducts = new ArrayList<>();
        int mostSold = productArrayList.get(0).getNumberSold();
        for (Product productLoop : productArrayList) {
            if (mostSold < productLoop.getNumberSold()) {
                mostSold = productLoop.getNumberSold();
            }
        }

        for (Product productLoop : productArrayList) {
            if (mostSold == productLoop.getNumberSold()) {
                mostPopularProducts.add(productLoop);
            }
        }

        return mostPopularProducts;
    }

    public static ArrayList<Product> getLeastPopularProducts() {
        ArrayList<Product> leastPopularProducts = new ArrayList<>();
        int leastSold = productArrayList.get(0).getNumberSold();
        for (Product productLoop : productArrayList) {
            if (leastSold > productLoop.getNumberSold()) {
                leastSold = productLoop.getNumberSold();
            }
        }

        for (Product productLoop : productArrayList) {
            if (leastSold == productLoop.getNumberSold()) {
                leastPopularProducts.add(productLoop);
            }
        }

        return leastPopularProducts;
    }

    public static void displayProducts() throws IOException {

    }

    public static boolean lookupProductName(String productName) {
        boolean productFound = false;
//            Look for the product name
        for (Product productLoop : productArrayList) {
            if (productName.equalsIgnoreCase(productLoop.getNAME())) {
                productFound = true;
                break;
            }
        }

        if (!productFound) {
            System.out.println("Product not found");
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateInput(String userInput, String pattern) {
        Pattern validPattern = Pattern.compile(pattern);
        return validPattern.matcher(userInput).find();
    }

    public String getId() {
        return id;
    }

    public String getNAME() {
        return NAME;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumberSold() {
        return numberSold;
    }

    public void setNumberSold(int numberSold) {
        this.numberSold = numberSold;
    }

    public static ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }
}