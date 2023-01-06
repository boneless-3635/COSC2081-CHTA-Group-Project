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
    private static ArrayList<Product> productArrayList = new ArrayList<>();

    public Product(String id, String NAME, int price, String category, int numberSold) {
        this.id = id;
        this.NAME = NAME;
        this.price = price;
        this.category = category;
        this.numberSold = numberSold;
    }

    public static void initializeProduct() throws IOException {

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
            StringBuilder errorMessage = new StringBuilder("Errors: \n");
            Scanner userInput = new Scanner(System.in);
            System.out.println("Product name: ");
            String productName = userInput.nextLine();
            System.out.println("Price: ");
            int productPrice = userInput.nextInt();
            userInput.nextLine();
            System.out.println("Category: ");
            String productCategory = userInput.nextLine();

//            input validating
            if (validateInput(productName, "^[a-zA-Z0-9 ]{3,}$")) {
                errorMessage.append("Invalid product name (only letters and digits, at least 3 characters) \n");
                errorFree = false;
            }

//            To check if product already existed, if it exists, error occurs
            for (Product productLoop : productArrayList) {
                if (productName.equals(productLoop.getNAME())) {
                    errorMessage.append("Product already exists \n");
                    errorFree = false;
                }
            }

            if (validateInput(String.valueOf(productPrice), "^[0-9]{4,}$")) {
                errorMessage.append("Invalid price (must be at least 1000 VND) \n");
                errorFree = false;
            }

//            Loop through the available categories to check if it matches
            for (String functionLoop : Category.getCategoryArrayList()) {
                if (functionLoop.equals(productCategory)) {
                    categoryMatched = true;
                    break;
                }
            }

//            If there is no match then add the error message and declare it not error free
            if (!categoryMatched) {
                errorMessage.append("Category does not exist \n");
                errorFree = false;
            }

            if (!errorFree) {
                System.out.println(errorMessage);
            } else {
//                    generate product id
                String productID = UUID.randomUUID().toString();
//                    write info to file
                PrintWriter pw = new PrintWriter(new FileWriter("product.txt", true));
                pw.println(productID + "," + productName + "," + productPrice + "," + productCategory + "," + 0);
                pw.close();
                System.out.println("Successfully added product");
                productArrayList.add(new Product(productID, productName, productPrice, productCategory, 0));
                break;
            }
        }
    }

    public static void removeProduct() throws IOException {
        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Please enter the name of the product you want to delete: ");
            String productDelete = userInput.nextLine();

//            Delete the product from the loop
//            Using collection for concise code
            productArrayList.removeIf(productLoop -> productDelete.equals(productLoop.getNAME()));

//            Delete product from text file
            String targetFile = "product.txt";
            String tempFile = "temp.txt";

            File oldFile = new File(targetFile);
            File newFile = new File(tempFile);

//            We can't remove a row from a csv file with java. This means we have to create a new temp file,
//            we then go through the original file. All the rows are copied over to the temp file and the
//            "deleted" row is not copied over.
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tempFile, true)));
                Scanner fileScanner = new Scanner(Paths.get(targetFile));

                while (fileScanner.hasNext()) {
                    String line = fileScanner.nextLine();
                    List<String> productValues = Arrays.asList(line.split(","));
                    if (!productDelete.equals(productValues.get(1))) {
                        pw.println(line);
                    } else {
                        System.out.println("Delete success\n");
                    }
                }

                pw.close();

                fileScanner.close();

                oldFile.delete();
                File dump = new File(targetFile);
                newFile.renameTo(dump);

                break;
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }

    public static boolean validateInput(String userInput, String pattern) {
        Pattern validPattern = Pattern.compile(pattern);
        return !validPattern.matcher(userInput).find();
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