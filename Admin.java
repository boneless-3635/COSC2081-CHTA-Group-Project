import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Admin extends User {
    public Admin(String userName, String password) {
        super(userName, password);
    }

    public void changeOrderStatus(ArrayList <Order> orders) throws IOException {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter order id you want to change status to PAID");
        String orderId = userInput.nextLine();
        boolean validId = false;
        boolean notPaid = false;
        for (Order order : orders){
            if (orderId.equals(order.getORDER_ID()) && order.getStatus().equals("delivered")){
                order.setStatus("PAID");
                Scanner fileScanner = new Scanner(Paths.get("order.txt"));
                ArrayList<String> tempOrderArray = new ArrayList<String>();
                while (fileScanner.hasNext()) {
                    String line = fileScanner.nextLine();
                    System.out.println(line);
                }
                validId = true;
                notPaid = true;
                break;
            }
        }
        if (validId && notPaid){
            System.out.println("Changed order having id: " + orderId + " to PAID successfully!");
        }
    }

    @Override
    public boolean isAdmin (){
        return true;
    }

    @Override
    public String toString(){
        return "you are admin";
    }
}
