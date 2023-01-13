/*
RMIT University Vietnam
Course: COSC2081 Programming 1
Semester: 2022C
Assessment: Assignment 3
Authors: Nguyen Quoc An, Pham Minh Hoang, Tran Gia Minh Thong, Yoo Christina
ID: s3938278, s3930051, s3924667, s3938331
Acknowledgement:

*/

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Category {
    private String category;
    private static final ArrayList<String> categoryArrayList = new ArrayList<>();

    public Category(String category) {
        this.category = category;
    }

    public static void initializeCategory() throws IOException {
        categoryArrayList.clear();
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

            if (Utility.validateInput(categoryInput, "^[a-zA-Z ]{3,}$")) {
                System.out.println("Invalid category name. Text only and it has to be at least 3 characters");
            } else {
                new Category(categoryInput);
                PrintWriter pw = new PrintWriter(new FileWriter("category.txt", true));
                pw.println(categoryInput);
                pw.close();
                System.out.println("Successfully added category\n");
                break;
            }
        }
        initializeCategory();
    }

    public static void removeCategory() throws IOException {
        System.out.println("Please enter the category name you want to delete:");
        Scanner userInput = new Scanner(System.in);
        String categoryDelete = userInput.nextLine();

        boolean categoryFound = false;

        //        Look for the category
        while (true) {
            for (String categoryLoop : categoryArrayList) {
                if (categoryDelete.equalsIgnoreCase(categoryLoop)) {
                    categoryFound = true;
                    break;
                }
            }

            if (!categoryFound) {
                System.out.println("Product not found\nPlease try again:");
                categoryDelete = userInput.nextLine();
            } else {
                break;
            }
        }
        Utility.deleteRowTextFile(categoryDelete, 0, "category.txt");

        Utility.updateTextFile(categoryDelete, "None", 3, 3, "product.txt");
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
