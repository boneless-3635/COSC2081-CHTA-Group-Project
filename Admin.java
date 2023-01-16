import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.remainderUnsigned;

public class Admin extends User {
    public Admin(String userName, String password) {
        super(userName, password);
    }

    public void changeOrderStatus(ArrayList <Order> orders) throws IOException {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter order id you want to change status to PAID");
        String orderIdInput = userInput.nextLine();
        //check if user enter existing order id or not first, if yes then execute updateTextFile
        boolean isExist = false;
        for (Order order : Order.getOrders()){
            if (orderIdInput.equalsIgnoreCase(order.getORDER_ID())){
                isExist = true;
                break;
            }
        }
        if (!isExist){
            System.out.println("Order ID does not exist, quit the procedure!");
        } else {
            Utility.updateTextFile(orderIdInput.toLowerCase(), "PAID", 0, 6, "order.txt");
            System.out.println("Order status is changed successfully to PAID!");
        }
    }

    public int calculateTotalRevenue(){
        int totalRevenue = 0;
        for (Order order : Order.getOrders()){
            totalRevenue += order.getTotalPrice();
        }
        return totalRevenue;
    }

    public void viewAllOrders() {
        ArrayList<Order> orders = Order.getOrders();
        System.out.println("<View all orders>");
        for (Order order: orders){
            order.viewOrder();
            System.out.println("----------");
        }
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
            if (Utility.validateInput(dateInput, "^(0[1-9]|[12][0-9]|3[01])\\-(0[1-9]|1[012])\\-\\d{4}$")){
                totalRevenue = calculateTotalRevenue(dateInput);
                validDateInput = true;
            } else {
                System.out.println("Invalid date input, try again!");
            }
        }
        return totalRevenue;
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
        ArrayList<Customer> customers;
        int largest =0;

        customers = Customer.getCustomers();

        for (Customer customer: customers){
            if (customer.getTotalPay()> largest){
                largest =customer.getTotalPay();
            }
        }
        System.out.println("Most paid customer information:");
        this.viewUserNameAndTotalPayByTotalPay(largest);
    }

    public void checkLeastPaidCustomer() throws FileNotFoundException {
        ArrayList<Customer> customers;
        int smallest =2147483647;

        customers = Customer.getCustomers();

        for (Customer customer: customers){
            if (customer.getTotalPay() < smallest){
                smallest =customer.getTotalPay();
            }
        }
        System.out.println("Least paid customer information:");
        this.viewUserNameAndTotalPayByTotalPay(smallest);
    }

    public void viewAllOrderMadeInDay(){
        Scanner scanner;
        String day = "";
        int count =0;
        ArrayList<Order> orders;
        Boolean isValid;

        scanner = new Scanner(System.in);
        orders = Order.getOrders();
        isValid = false;
        System.out.println("Input the day you want to check for all order:");

        while (!isValid){
            day = scanner.nextLine();
            if (Utility.validateInput(day, "^(0[1-9]|[12][0-9]|3[01])\\-(0[1-9]|1[012])\\-\\d{4}$")){
                isValid = true;
            }
            else{
                System.out.println("Wrong day format!!! Input the day you want to check for all order:");
            }
        }

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

    public static void viewUserInfoById(String userID) throws FileNotFoundException {
        for (Customer customer: Customer.getCustomers()){
            if (userID.equals(customer.getId())){
                System.out.println(customer.toString());
            }
        }
    }

    public static void viewUserInfoByTotalPay(int totalPay) throws FileNotFoundException {
        for (Customer customer: Customer.getCustomers()){
            if (totalPay == (customer.getTotalPay())){
                System.out.println(customer.toString());
            }
        }
    }

    public static void viewUserNameAndTotalPayByTotalPay(int totalPay) throws FileNotFoundException {
        for (Customer customer: Customer.getCustomers()){
            if (totalPay == (customer.getTotalPay())){
                System.out.println("Name: "+ customer.getUserName());
                System.out.println("Total Pay: "+ customer.getTotalPay());
                System.out.println("----------");
            }
        }
    }

    public void removeCustomer() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter customer ID to remove");
        String customerIDInput = input.nextLine();
        boolean isExist = false;
        for (Customer customer : Customer.getCustomers()){
            if (customer.getId().equals(customerIDInput.toLowerCase())){
                isExist = true;
            }
        }
        if (!isExist){
            System.out.println("Customer ID does not exist!");
        } else {
            Utility.deleteRowTextFile(customerIDInput.toLowerCase(), 0, "customer.txt");
            System.out.println("remove customer successfully!");
            Customer.initializeCustomers();
        }
    }

    @Override
    public String toString(){
        return "Admin account\n";
    }
    @Override
    public boolean isAdmin (){
        return true;
    }
}
