import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //declare an unknown user object first, then assign it to customer or admin later
        User user;
        //login phase, enter 'yes' or 'no' to login or register. Otherwise, ask again for valid input
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
        } else {
            //customer's events here
            Customer customer = (Customer) user;
            System.out.println(customer); //print for testing
        }

    }
}
