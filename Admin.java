import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Admin extends User{
    public Admin(String userName, String password) {
        super(userName, password);
    }

    @Override
    public boolean isAdmin (){
        return true;
    }

    public void getAllOrderByCustomerId(){
        String customerID;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Order> orders;
        int count = 0;

        System.out.println("Please enter customer ID to view all order:");
        customerID = scanner.nextLine();
        orders = Order.getOrders();
        for (Order order: orders){
            if (order.getCUSTOMER_ID().equals(customerID)){
                order.viewOrder();
                System.out.println("----------");
                count++;
            }
        }
        if (count==0){
            System.out.println("Customer ID not found.");
        }
    }

    public void checkMostPaidCustomer() throws FileNotFoundException {
        HashMap<String, Integer> customerPaidlist;
        ArrayList<Order> orders;
        int value, largest;
        String key, cusID = null;

        customerPaidlist = new HashMap<String, Integer>();
        orders = Order.getOrders();
        for (Order order: orders){
            key = order.getCUSTOMER_ID();
            value = order.getTotalPrice();

            if (customerPaidlist.containsKey(key)) {
                customerPaidlist.put(key, customerPaidlist.get(key) + value);
            } else {
                // add the key-value pair to the map
                customerPaidlist.put(key, value);
            }
        }

        largest = 0;
        for (Map.Entry<String, Integer> entry :
                customerPaidlist.entrySet()) {
            if (entry.getValue()>largest){
                largest = entry.getValue();
                cusID = entry.getKey();
            }
        }
        System.out.println("Most paid customer information:");
        Customer.viewUserInfoById(cusID);
    }

    public void viewAllOrderMadeInDay(){
        Scanner scanner;
        String day;
        int count =0;
        ArrayList<Order> orders;

        scanner = new Scanner(System.in);
        orders = Order.getOrders();
        System.out.println("Input the day you want to check for all order:");
        day = scanner.nextLine();

        for (Order order: orders){
            if (day.equals(order.getDate())){
                count++;
                order.viewOrder();
                System.out.println("----------");
            }
        }

        if (count==0){
            System.out.println("No order made on this day.");
        }
    }

    
    @Override
    public String toString(){
        return "you are admin";
    }
}
