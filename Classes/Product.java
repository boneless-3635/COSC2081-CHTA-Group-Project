package Classes;/*
RMIT University Vietnam
Course: COSC2081 Programming 1
Semester: 2022C
Assessment: Assignment 3
Authors: Nguyen Quoc An, Pham Minh Hoang, Tran Gia Minh Thong, Yoo Christina
ID: s3938278, s3930051, s3924667, s3938331
Acknowledgement:
https://stackoverflow.com/questions/64678515/how-to-delete-a-specific-row-from-a-csv-file-using-search-string
https://www.youtube.com/watch?v=ij07fW5q4oo
https://www.tutorialspoint.com/how-to-overwrite-a-line-in-a-txt-file-using-java
https://www.w3schools.com/java/java_regex.asp
https://stackoverflow.com/questions/33443651/print-java-arrays-in-columns
https://stackoverflow.com/questions/1883345/whats-up-with-javas-n-in-printf
https://www.geeksforgeeks.org/pattern-compilestring-method-in-java-with-examples/
https://stackoverflow.com/questions/53201648/java-format-string-d-and-d
https://stackoverflow.com/questions/11665884/how-can-i-parse-a-string-with-a-comma-thousand-separator-to-a-number
https://www.javatpoint.com/java-string-replaceall
https://www.geeksforgeeks.org/bubble-sort/

*/

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Product {
    private String id;
    private String name;
    private int price;
    private String category;
    private int numberSold;
    private static ArrayList<Product> productArrayList = new ArrayList<>();
    private static ArrayList<Product> productEditedArrayList = new ArrayList<>();


    public Product(String id, String name, int price, String category, int numberSold) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.numberSold = numberSold;
    }

    public static void initializeProduct() throws IOException {
        productArrayList.clear();
        Scanner fileScanner = new Scanner(Paths.get("Database/product.txt"));
        while (fileScanner.hasNext()) {
//            read per line and split line into array
            List<String> productValues = Arrays.asList(fileScanner.nextLine().split(";;;"));

//                store products in arraylist
            productArrayList.add(new Product(productValues.get(0), productValues.get(1),
                    parseInt(productValues.get(2)), productValues.get(3), parseInt(productValues.get(4))));
        }

        productEditedArrayList = productArrayList;
    }

    public static void addProduct() throws IOException {
        while (true) {
            boolean errorFree = true;
            boolean categoryMatched = false;
            System.out.println("\nCreate a new product:\n");

            Scanner userInput = new Scanner(System.in);

            System.out.println("Product name: ");
            String productName = userInput.nextLine();

            System.out.println("Price: ");
            String productPrice = userInput.nextLine();

            System.out.println("Category: ");
            String productCategory = userInput.nextLine();

            if (!Utility.validateInput(productName, "^[a-zA-Z0-9 ]{3,}$")) {
                System.out.println("Invalid user name (only letters and digits, at least 3 characters)");
                errorFree = false;
            }

            //            To check if product already existed because name is unique
            for (Product productLoop : productArrayList) {
                if (productName.equalsIgnoreCase(productLoop.getName())) {
                    System.out.println("Product already exists");
                    errorFree = false;
                    break;
                }
            }

            if (Utility.validateInput(productPrice, "[0-9 ]+")) {
                if (getFilteredInt(productPrice) < 1000) {
                    System.out.println("Invalid price (must be a number at least 1000 VND)");
                    errorFree = false;
                }
            } else {
                System.out.println("Invalid price (must be a number at least 1000 VND)");
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
                System.out.println("Category not found");
                errorFree = false;
            }

            if (errorFree) {
//                    generate product id
                String productID = UUID.randomUUID().toString();
//                    write info to file
                PrintWriter pw = new PrintWriter(new FileWriter("Database/product.txt", true));
                pw.println(productID + ";;;" + productName + ";;;" + getFilteredInt(productPrice) +
                        ";;;" + productCategory + ";;;" + 0);
                pw.close();
                System.out.println("Successfully added product\n");
                break;
            }
        }
        initializeProduct();
    }

    public static void removeProduct() throws IOException {

        System.out.println("Please enter the name of the product you want to delete: ");
        Scanner userInput = new Scanner(System.in);
        String productName = userInput.nextLine();
        if (lookupProductName(productName)) {
            Utility.deleteRowTextFile(productName,1, "Database/product.txt");
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

                if (Utility.validateInput(productNewPrice, "[0-9 ]+")) {
                    if (getFilteredInt(productNewPrice) > 1000) {
                        Utility.updateTextFile(productName, String.valueOf(getFilteredInt(productNewPrice)), 1, 2, "Database/product.txt");
                        System.out.println("Successfully updated the price\n");
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

    public static void displayProducts() {
//        The parameter is to display the chosen ArrayList which are the default(all products), filtered and sorted
        System.out.printf("%n%-20s%-15s%20s%20s%n", "Product name", "Category", "Price (VND)", "Number Sold");
        System.out.println("---------------------------------------------------------------------------");
        if (!productEditedArrayList.isEmpty()) {
            for (Product productLoop : productEditedArrayList) {
                System.out.printf("%-20s%-15s%,20d%,20d%n", productLoop.getName(), productLoop.getCategory(),
                        productLoop.getPrice(),productLoop.getNumberSold());
            }
        } else {
            System.out.println("No products to display\n");
        }
    }

    public static void filterProductCategory() {
        ArrayList<Product> tempProductArrayList  = new ArrayList<>();

        while (true) {
            boolean foundCategory = false;

            System.out.println("Please enter the category you want to filter: (category/x)");
            Scanner userInput = new Scanner(System.in);
            String inputCategory = userInput.nextLine();
            if (inputCategory.equalsIgnoreCase("x")) {
                break;
            } else {
                for (String categoryLoop : Category.getCategoryArrayList()) {
                    if (inputCategory.equalsIgnoreCase(categoryLoop)) {
                        foundCategory = true;
                        for (Product productLoop : productEditedArrayList) {
                            if (inputCategory.equalsIgnoreCase(productLoop.getCategory())) {
                                tempProductArrayList.add(productLoop);
                            }
                        }
                        productEditedArrayList = tempProductArrayList;
                    }
                }
            }

            if (!foundCategory) {
                System.out.println("No category found, please try again");
            } else {
                break;
            }
        }
    }

    public static void filterProductPrice() {
        ArrayList<Product> tempProductArrayList  = new ArrayList<>();

        while (true) {
            boolean UpperLimit = true;

            Scanner userInput = new Scanner(System.in);
            System.out.println("Please enter the lower price filter (price/x)");
            String lowerPriceLimit = userInput.nextLine();

            System.out.println("Please enter the upper price filter (price/x)");
            String upperPriceLimit = userInput.nextLine();

//            Let the user choose if there aren't any upper limits or lower limits
            if (lowerPriceLimit.equalsIgnoreCase("x")) {
                lowerPriceLimit = "0";
            }

            if (upperPriceLimit.equalsIgnoreCase("x")) {
                UpperLimit = false;
            }

//            Utility.validate the inputs first
            if (Utility.validateInput(lowerPriceLimit, "[0-9 ]+")){
                if (UpperLimit) {
//                    This case only happens when there IS an upper limit
//                    Lower limit has to be lower than upper limit
                    if (Utility.validateInput(upperPriceLimit, "[0-9 ]+")) {
                        if (getFilteredInt(lowerPriceLimit) < getFilteredInt(upperPriceLimit)) {
                            for (Product productLoop : productEditedArrayList) {
                                if (getFilteredInt(lowerPriceLimit) < productLoop.getPrice() &&
                                        getFilteredInt(upperPriceLimit) > productLoop.getPrice()) {
                                    tempProductArrayList.add(productLoop);
                                }
                            }
//                            Write the temporary ArrayList back into the filtered ArrayList
                            productEditedArrayList = tempProductArrayList;
                            break;
                        } else {
                            System.out.println("Invalid price, lower price limit must be smaller than upper price limit");
                        }
                    } else {
                        System.out.println("Invalid upper price (must be a number)");
                    }
//                    This case only happens when there ISN'T an upper limit
                } else {
                    for (Product productLoop : productEditedArrayList) {
                        if (getFilteredInt(lowerPriceLimit) < productLoop.getPrice()) {
                            tempProductArrayList.add(productLoop);
                        }
                    }
                    productEditedArrayList = tempProductArrayList;
                    break;
                }
            } else {
                System.out.println("Invalid lower price (must be a number)");
            }
        }
    }

    public static void sortProductPrice() {
        while (true) {
            System.out.println("Do you want to sort by ascending or descending price? (asc/des/x)");
            Scanner userInput = new Scanner(System.in);
            String userChoice = userInput.nextLine();

            if (userChoice.equalsIgnoreCase("asc")) {
//                True for ascending, false for descending
                bubbleSortPrice(true);
                break;
            } else if (userChoice.equalsIgnoreCase("des")) {
                bubbleSortPrice(false);
                break;
            } else if (userChoice.equalsIgnoreCase("x")) {
                break;
            } else {
                System.out.println("Invalid choice, please try again");
            }
        }
    }

    public static void bubbleSortPrice(boolean ascending) {


        for (int i = 0; i < productEditedArrayList.size() - 1; i++) {
            for (int j = 0; j < productEditedArrayList.size() - i - 1; j++) {
//                sort ascending
                if (ascending) {
                    if (productEditedArrayList.get(j).getPrice() > productEditedArrayList.get(j + 1).getPrice()) {
                        Product temp = productEditedArrayList.get(j);
                        productEditedArrayList.set(j, productEditedArrayList.get(j + 1));
                        productEditedArrayList.set(j + 1, temp);
                    }
//                    sort descending
                } else {
                    if (productEditedArrayList.get(j).getPrice() < productEditedArrayList.get(j + 1).getPrice()) {
                        Product temp = productEditedArrayList.get(j);
                        productEditedArrayList.set(j, productEditedArrayList.get(j + 1));
                        productEditedArrayList.set(j + 1, temp);
                    }
                }
            }
        }
    }

    public static int getFilteredInt(String intString) {
//        Remove the whitespace and comma from the price
        return Integer.parseInt(intString.replaceAll(",", "").replaceAll("\\s", ""));
    }

    public static void resetProduct() {
//        To remove all filters
        productEditedArrayList = productArrayList;
    }

    public static boolean lookupProductName(String productName) {
//            Look for the product name
        for (Product productLoop : productArrayList) {
            if (productName.equalsIgnoreCase(productLoop.getName())) {
                return true;
            }
        }
        return false;
    }

    //create method for Hoang
    public static void updateNumSold(Map<String, Integer>  shoppingCart) throws IOException {
        for (String item : shoppingCart.keySet()){
            for (Product product : productArrayList){
                if (item.equalsIgnoreCase(product.getName())){
                    int numSold = product.getNumberSold() + shoppingCart.get(item);
                    Utility.updateTextFile(product.getName(), String.valueOf(numSold), 1, 4, "Database/product.txt");
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getNumberSold() {
        return numberSold;
    }

    public static ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

}