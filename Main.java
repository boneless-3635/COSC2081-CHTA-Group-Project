import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //declare an unknown user object first, then assign it to customer or admin later
        User user;
        //login phase, enter 'yes' or 'no' to log in or register. Otherwise, ask again for valid input
        while (true) {
            java.lang.System.out.println("Do you have account yet ? (yes/no)");
            Scanner userInput = new Scanner(java.lang.System.in);
            String hadAccount = userInput.nextLine();
            if (hadAccount.equals("yes")){
                user = User.login();
                if (user != null){    //if user == null, that means login failed and ask user to login again
                    break;
                }
            } else if (hadAccount.equals("no")){
                Customer.register();
            }
        }

        //check if that user is customer or admin, then downcast to relevant role
        if (user.isAdmin()){
            //admin's events here
            Admin admin = (Admin) user;
            System.out.println(admin); //print for testing
//            Initialize content to access contents quickly from an object instead of reading from a file
            Category.initializeCategory();
            Product.initializeProduct();

            String[] options = {"addpro", "remove", "update", "get", "addcat", "delpro"};
//            asks admin to choose the action

//            loop to keep prompting the user until the input is valid
            while (true) {
                System.out.println("What would you like to do (type exit to close the program):");
                System.out.println("Add a product (addpro)");
                System.out.println("Remove a product (remove)");
                System.out.println("Update price (update)");
                System.out.println("Get information of all order by customer ID (get)");
                System.out.println("Add a product category(addcat)");
//            ADD MORE LATER!!

//            no option is selected at first
                boolean optionSelected = false;
                Scanner functionSelectorScanner = new Scanner(System.in);
                String functionSelection;

                functionSelection = functionSelectorScanner.next();

//                Loop through the available options to check if user input matches it
//                When it matches, direct to the helper method
                for (String function : options) {
                    if (function.equals(functionSelection)) {
                        optionSelected = true;
                        break;
                    }
                }

//                exits the program when the user wants to
                if (functionSelection.equals("exit")) {
                    break;
                } else if (!optionSelected) {
                    System.out.println("Invalid option");
                } else {
//                    check which option was selected to display the required information
                    switch (functionSelection) {
                        case "addpro":
                            Product.addProduct();
                            break;
                        case "addcat":
                            Category.addCategory();
                            break;
                        case "delpro":
                            Product.removeProduct();
                            break;
                        case "update":
                            Product.updatePrice();
                    }
                }
            }

        } else {
            //customer's events here
            Customer customer = (Customer) user;
            System.out.println(customer); //print for testing
        }

    }
}
