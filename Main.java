/*
RMIT University Vietnam
Course: COSC2081 Programming 1
Semester: 2022C
Assessment: Assignment 3
Authors: Nguyen Quoc An, Pham Minh Hoang, Tran Gia Minh Thong, Yoo Christina
ID: s3938278, s3930051, s3924667, s3938331
Acknowledgement:
*/

import Classes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------------------------------------------------");
        System.out.println("COSC2081 GROUP ASSIGNMENT\nSTORE ORDER MANAGEMENT SYSTEM\nInstructor: Mr. Tom Huynh");
        System.out.println("Group: Group CHTA");
        System.out.println("s3938278, Nguyen Quoc An\ns3930051, Pham Minh Hoang\ns3924667, Tran Gia Minh Thong\ns3938331, Yoo Christina");
        System.out.println("-----------------------------------------------------\n");

        Scanner input = new Scanner(System.in);

        label1:
        while (true) {
            System.out.println("Store Order Management System of CHTA");
            System.out.println("< Options >");
            System.out.println("1. Register");
            System.out.println("2. Sign In");
            System.out.println("0. Exit");
            System.out.println("(Enter the according number to proceed)");
            switch (input.nextLine()) {
                case "1":
                    Customer.register();
                    break;
                case "2":
                    Category.initializeCategory();
                    Product.resetProduct();
                    Product.initializeProduct();
                    Order.initializeOrders();
                    Customer.initializeCustomers();

                    User user = User.login();

                    if (user == null) {
                        break;
                    } else if (user.isAdmin()) {
                        Admin admin = (Admin) user;
                        label:
                        while (true) {
                            System.out.println("\nAdmin page");
                            System.out.println("< Main page options >");
                            System.out.println("1. List all products");
                            System.out.println("2. View all orders");
                            System.out.println("3. List all members");
                            System.out.println("4. Remove member");//
                            System.out.println("5. Add new product");
                            System.out.println("6. Remove product");
                            System.out.println("7. Update price of product");
                            System.out.println("8. Get information of all orders by Customer ID");
                            System.out.println("9. Change the status of the order");
                            System.out.println("10. View Statistics");
                            System.out.println("11. View orders of a day");
                            System.out.println("12. Add category");
                            System.out.println("13. Remove category");
                            System.out.println("0. Logout");
                            System.out.println("(Enter the according number to proceed)");
                            switch (input.nextLine()) {
                                case "1":
                                    Product.displayProducts();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "2":
                                    admin.viewAllOrders();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "3":
                                    Customer.listMembers();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "4":
                                    admin.removeCustomer();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "5":
                                    Product.addProduct();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "6":
                                    Product.removeProduct();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "7":
                                    Product.updatePrice();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "8":
                                    admin.getAllOrderByCustomerId();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "9":
                                    admin.changeOrderStatus();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "10":
                                    while (true) {
                                        System.out.println("\n< View statistics page >");
                                        System.out.println("1. View total revenue");
                                        System.out.println("2. View revenue of a day");
                                        System.out.println("3. View most popular product");
                                        System.out.println("4. View least popular product");
                                        System.out.println("5. View customer with highest spending");
                                        System.out.println("6. View customer with lowest spending");
                                        System.out.println("7. View membership type numbers");
                                        System.out.println("0. Return to main menu");
                                        System.out.println("(Enter the according number to proceed)");
                                        switch (input.nextLine()) {
                                            case "1":
                                                System.out.println("\n<The total revenue of the store>");
                                                System.out.printf("%,d VND%n", admin.calculateTotalRevenue());
                                                System.out.println("(Enter '0' to go back)");
                                                if (input.nextLine().equals("0")) {
                                                    break;
                                                }
                                            case "2":
                                                System.out.println("\n<The revenue of a day>");
                                                System.out.printf("%,d VND%n",admin.calculateTotalRevenueWithDateValidate());
                                                System.out.println("(Enter '0' to go back)");
                                                if (input.nextLine().equals("0")) {
                                                    break;
                                                }
                                            case "3":
                                                System.out.println("\n<Most popular product>");
                                                ArrayList<Product> popularProducts = Product.getMostPopularProducts();
                                                System.out.printf("%n%-20s%-15s%20s%20s%n", "Product name", "Category", "Price (VND)", "Number Sold");
                                                System.out.println("---------------------------------------------------------------------------");
                                                for (Product product: popularProducts) {
                                                    System.out.printf("%-20s%-15s%,20d%,20d%n", product.getName(), product.getCategory(),
                                                            product.getPrice(),product.getNumberSold());
                                                }
                                                System.out.println("(Enter '0' to go back)");
                                                if (input.nextLine().equals("0")) {
                                                    break;
                                                }
                                            case "4":
                                                System.out.println("\n<Least popular product>");
                                                ArrayList<Product> leastPopularProducts = Product.getLeastPopularProducts();
                                                System.out.printf("%n%-20s%-15s%20s%20s%n", "Product name", "Category", "Price (VND)", "Number Sold");
                                                System.out.println("---------------------------------------------------------------------------");
                                                for (Product product: leastPopularProducts) {
                                                    System.out.printf("%-20s%-15s%,20d%,20d%n", product.getName(), product.getCategory(),
                                                            product.getPrice(),product.getNumberSold());
                                                }
                                                System.out.println("(Enter '0' to go back)");
                                                if (input.nextLine().equals("0")) {
                                                    break;
                                                }
                                            case "5":
                                                admin.checkMostPaidCustomer();
                                                System.out.println("(Enter '0' to go back)");
                                                if (input.nextLine().equals("0")) {
                                                    break;
                                                }
                                            case "6":
                                                admin.checkLeastPaidCustomer();
                                                System.out.println("(Enter '0' to go back)");
                                                if (input.nextLine().equals("0")) {
                                                    break;
                                                }
                                            case "7":
                                                Customer.membershipNumbers();
                                                System.out.println("(Enter '0' to go back)");
                                                if (input.nextLine().equals("0")) {
                                                    break;
                                                }
                                            case "0":
                                                continue label;
                                        }
                                    }
                                case "11":
                                    admin.viewAllOrderMadeInDay();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "12":
                                    Category.addCategory();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "13":
                                    Category.removeCategory();
                                    System.out.println("(Enter '0' to go back)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "0":
                                    System.out.println("Thank you for using our System!\n");
                                    continue label1;
                            }
                        }
                    } else {
                        Customer customer = (Customer) user;
                        customer.updateMembership();
                        System.out.println("\nHello, " + customer.getFullName() + "!");
                        label2:
                        while (true) {
                            System.out.println("\n< Main page options >");
                            System.out.println("1. View products");
                            System.out.println("2. Create a new order");
                            System.out.println("3. View my order");
                            System.out.println("4. Search order by order ID");
                            System.out.println("5. View today's order");
                            System.out.println("6. View my information");
                            System.out.println("7. Edit my profile");
                            System.out.println("0. Logout");
                            System.out.println("(Enter the according number to proceed)");
                            switch (input.nextLine()) {
                                case "1":
                                    while (true) {
                                        System.out.println("\n< Product page options >");
                                        System.out.println("1. View all products");
                                        System.out.println("2. Sort and filter products");
                                        System.out.println("0. Return to main page");
                                        System.out.println("(Enter the according number to proceed)");
                                        switch (input.nextLine()) {
                                            case "1":
                                                Product.resetProduct();
                                                Product.initializeProduct();
                                                Product.displayProducts();
                                                System.out.println("(Enter '0' to go back)");
                                                if (input.nextLine().equals("0")) {
                                                    break;
                                                }
                                            case "2":
                                                Product.resetProduct();
                                                Product.filterProductCategory();
                                                Product.filterProductPrice();
                                                Product.sortProductPrice();
                                                Product.displayProducts();
                                                System.out.println("(Enter '0' to go back)");
                                                if (input.nextLine().equals("0")) {
                                                    break;
                                                }
                                            case "0":
                                                continue label2;
                                        }
                                    }
                                case "2":
                                    Order.placeOrder(customer);
                                    customer.updateMembership();
                                    System.out.println("(Enter '0' to go back to main menu)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "3":
                                    System.out.println("\nMy orders:\n----------");
                                    customer.viewMyOrder();
                                    System.out.println("(Enter '0' to go back to main menu)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "4":
                                    customer.viewOrderByOrderID();
                                    System.out.println("(Enter '0' to go back to main menu)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "5":
                                    customer.viewTodaysOrder();
                                    System.out.println("(Enter '0' to go back to main menu)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "6":
                                    System.out.println("\n<My information>");
                                    for (Customer cus: Customer.getCustomers()) {
                                        if (cus.getUserName().equalsIgnoreCase(customer.getUserName())) {
                                            System.out.println(cus);
                                        }
                                    }
                                    System.out.println("(Enter '0' to go back to main menu)");
                                    if (input.nextLine().equals("0")) {
                                        break;
                                    }
                                case "7":
                                    while (true) {
                                        System.out.println("\n< Edit my profile page >");
                                        System.out.println("1. Edit Address");
                                        System.out.println("2. Edit Email");
                                        System.out.println("3. Edit Phone number");
                                        System.out.println("0. Return to main menu");
                                        System.out.println("(Enter the according number to proceed)");
                                        switch (input.nextLine()) {
                                            case "1":
                                                customer.editAddress();
                                                break;
                                            case "2":
                                                customer.editEmail();
                                                break;
                                            case "3":
                                                customer.editPhone();
                                                break;
                                            case "0":
                                                continue label2;
                                        }
                                    }
                                case "0":
                                    System.out.println("Thank you for using our System!\n");
                                    continue label1;
                            }
                        }
                    }
                case "0":
                    return;
            }
        }
    }
}

