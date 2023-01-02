import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class Product {
    private String id;
    private final String NAME;
    private int price;
    private String category;

    public Product(String id, String NAME, int price, String category) {
        this.id = id;
        this.NAME = NAME;
        this.price = price;
        this.category = category;
    }

    public static void writeProduct() throws IOException {
        while (true) {
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
            if (!validateInput(productName, "^[a-zA-Z0-9 ]{3,}$")) {
                errorMessage += "Invalid user name (only letters and digits, at least 3 characters) \n";

                if (!validateInput(String.valueOf(price), "^[0-9]{4,}$")) {
                    errorMessage += "Invalid price (must be at least 1000 VND) \n";
                }

                if (!validateInput(category, "^.+@.+$")) {
                    errorMessage += "Invalid email (must contains @ in email) \n";
                }


                if (errorMessage != "Errors: \n") {
                    System.out.println(errorMessage);
                } else {
//                    generate product id
                    String productID = UUID.randomUUID().toString();
//                    write info to file
                    PrintWriter pw = new PrintWriter(new FileWriter("product.txt", true));
                    pw.println(productID + ";" + productName + ";" + price + ";" + category);
                    pw.close();
                    System.out.println("Register successfully!");
                    break;
                }
            }
        }
    }

    public static boolean validateInput(String userInput, String pattern) {
        boolean valid;
        Pattern validPattern = Pattern.compile(pattern);
        if (validPattern.matcher(userInput).find()) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

}