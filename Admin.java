import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.remainderUnsigned;

public class Admin extends User {
    public Admin(String userName, String password) {
        super(userName, password);
    }

    //havent check if user enter unexisting order id, do it later
    public void changeOrderStatus(ArrayList <Order> orders) throws IOException {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter order id you want to change status to PAID");
        String orderId = userInput.nextLine();
        Utility.updateTextFile(orderId, "PAID", 0, 6, "order.txt");
    }

    public int calculateTotalRevenue(){
        int totalRevenue = 0;
        for (Order order : Order.getOrders()){
            totalRevenue += order.getTotalPrice();
        }
        return totalRevenue;
    }

    public int calculateTotalRevenue(String date){
        int totalRevenue = 0;
        for (Order order : Order.getOrders()){
            if (order.getDate().equals(date)){
                totalRevenue += order.getTotalPrice();
            }
        }
        return totalRevenue;
    }

    public int calculateTotalRevenueWithDateValidate(){
        int totalRevenue = 0;
        Scanner userInput = new Scanner(System.in);
        boolean validDateInput = false;
        while (!validDateInput){
            System.out.println("Enter date to view revenue (dd-mm-yyy): ");
            String dateInput = userInput.nextLine();
            if (validateInput(dateInput, "^(0[1-9]|[12][0-9]|3[01])\\-(0[1-9]|1[012])\\-\\d{4}$")){
                totalRevenue = calculateTotalRevenue(dateInput);
                validDateInput = true;
            } else {
                System.out.println("Invalid date input, try again!");
            }
        }
        return totalRevenue;
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
