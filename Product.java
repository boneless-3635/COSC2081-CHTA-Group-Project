/*
RMIT University Vietnam
Course: COSC2081 Programming 1
Semester: 2022C
Assessment: Assignment 3
Authors: Nguyen Quoc An, Pham Minh Hoang, Tran Gia Minh Thong, Yoo Christina
ID: s3938278, s3930051, s3924667, s3938331
Acknowledgement:

*/

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class Product {
    private final String id;
    private final String NAME;
    private int price;
    private final String category;

    public Product(String id, String NAME, int price, String category) {
        this.id = id;
        this.NAME = NAME;
        this.price = price;
        this.category = category;
    }

    public static void addProduct() throws IOException {
        while (true) {
            boolean errorFree = true;
            System.out.println("Create new product: ");
            String errorMessage = "Errors: \n";
            Scanner userInput = new Scanner(System.in);
            System.out.println("Product name: ");
            String productName = userInput.nextLine();
            System.out.println("Price: ");
            int price = userInput.nextInt();
            userInput.nextLine();
            System.out.println("Category: ");
            String category = userInput.nextLine();

//            input validating
            if (validateInput(productName, "^[a-zA-Z0-9 ]{3,}$")) {
                errorMessage += "Invalid user name (only letters and digits, at least 3 characters) \n";
                errorFree = false;
            }

            if (validateInput(String.valueOf(price), "^[0-9]{4,}$")) {
                errorMessage += "Invalid price (must be at least 1000 VND) \n";
                errorFree = false;
            }

            if (validateInput(category, "^.+@.+$")) {
                errorMessage += "Invalid email (must contains @ in email) \n";
                errorFree = false;
            }

            if (!errorFree) {
                System.out.println(errorMessage);
            } else {
//                    generate product id
                String productID = UUID.randomUUID().toString();
//                    write info to file
                PrintWriter pw = new PrintWriter(new FileWriter("product.txt", true));
                pw.println(productID + ";" + productName + ";" + price + ";" + category);
                pw.close();
                System.out.println("Successfully added product");
                break;
            }
        }
    }

    public static void removeProduct() throws IOException {

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
}