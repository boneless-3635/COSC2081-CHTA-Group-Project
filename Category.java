

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Category {
    private String category;
    private static ArrayList<String> categoryArrayList;

    public Category(String category) {
        this.category = category;
    }

    public static void initializeCategory() throws IOException {
        categoryArrayList = new ArrayList<String>();
        Scanner fileScanner = new Scanner(Paths.get("category.txt"));
        while (fileScanner.hasNext()) {
            categoryArrayList.add(fileScanner.nextLine());
        }
        fileScanner.close();
    }

    public static void addCategory() throws IOException {
        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Please enter the name of the category you want to add: ");
            String categoryInput = userInput.nextLine();

            if (validateInput(categoryInput, "^[a-zA-Z ]{3,}$")) {
                System.out.println("Invalid category name. Text only and it has to be at least 3 characters");
            } else {
                new Category(categoryInput);
                PrintWriter pw = new PrintWriter(new FileWriter("category.txt", true));
                pw.println(categoryInput);
                pw.close();
                System.out.println("Successfully added category");
                break;
            }
        }
    }

    public static boolean validateInput(String userInput, String pattern) {
        Pattern validPattern = Pattern.compile(pattern);
        return !validPattern.matcher(userInput).find();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static ArrayList<String> getCategoryArrayList() {
        return categoryArrayList;
    }
}
