package Classes;/*
RMIT University Vietnam
Course: COSC2081 Programming 1
Semester: 2022C
Assessment: Assignment 3
Authors: Nguyen Quoc An, Pham Minh Hoang, Tran Gia Minh Thong, Yoo Christina
ID: s3938278, s3930051, s3924667, s3938331
Acknowledgement:
*/

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class Order {
    private final String ORDER_ID;
    private final String CUSTOMER_ID;
    private String customerName;
    private String[] productNamesAndQuantity;
    private int totalPrice;
    private String customerAddress;
    private String status;
    private String date;
    private static ArrayList<Order> orders = new ArrayList<>();

    public Order(String ORDER_ID, String CUSTOMER_ID, String customerName, String[] productNamesAndQuantity, int totalPrice, String customerAddress ,String status, String date) {
        this.ORDER_ID = ORDER_ID;
        this.CUSTOMER_ID = CUSTOMER_ID;
        this.customerName = customerName;
        this.productNamesAndQuantity = productNamesAndQuantity;
        this.status = status;
        this.totalPrice = totalPrice;
        this.customerAddress = customerAddress;
        this.date = date;
    }

    public static void initializeOrders(){
        orders.clear();
        try (Scanner fileScanner = new Scanner(new File("Database/order.txt"))) {
            while (fileScanner.hasNext()) {
                //read per line and split line into array
                List<String> orderFields = Arrays.asList(fileScanner.nextLine().split(";;;"));

                //index 3 will store product names, convert them to array.
                String[] productNamesAndQuantity = orderFields.get(3).split(",");

                //store products in arraylist. Notice that we will cast productNamesAndQuantity to arraylist to fit with the constructor.
                orders.add(new Order(orderFields.get(0), orderFields.get(1), orderFields.get(2), productNamesAndQuantity, Integer.parseInt(orderFields.get(4)), orderFields.get(5), orderFields.get(6), orderFields.get(7)));
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File path does not exist to read!");
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Info in file is not formatted well!");
        }
    }

    public static int calculateTotalPrice(Map<String, Integer>  productAndQuantityArray, Customer customer){
        int totalPrice = 0;
        double discount = 0;
        for (String item : productAndQuantityArray.keySet()){
            //loop through initialized products array to compare productName and take out its price
            for (Product product : Product.getProductArrayList()){
                if (item.equalsIgnoreCase(product.getName())){
                    totalPrice += productAndQuantityArray.get(item) * product.getPrice();
                }
            }
        }
        //check customer's membership to discount
        switch (customer.getMembership()){
            case "Silver":
                discount = 0.05;
                break;
            case "Gold":
                discount = 0.1;
                break;
            case "Platinum":
                discount = 0.15;
                break;
        }
        totalPrice = (int) (totalPrice - totalPrice*discount);
        return totalPrice;
    }

    public static void addOrderToFile(Order order) throws IOException {
        //turn array of productAndQuantity to String to store to text file
        String productNamesAndQuantity = String.join(",", order.getProductNamesAndQuantity());
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("Database/order.txt", true));
        pw.println(order.getORDER_ID() + ";;;"  + order.getCUSTOMER_ID() + ";;;" + order.getCustomerName() + ";;;" + productNamesAndQuantity + ";;;" + order.getTotalPrice() + ";;;" + order.getCustomerAddress() + ";;;" + order.getStatus() + ";;;" + order.getDate());
        pw.close();
    }

    //specifically used for customer
    public void display(String discountMessage){
        System.out.printf("order id: %s \ncustomer id: %s \ncustomer name: %s \n", this.getORDER_ID(), this.getCUSTOMER_ID(), this.getCustomerName());
        for (String productAndNum : this.getProductNamesAndQuantity()){
            System.out.println(productAndNum);
        }
        System.out.printf("total price: %d %s\naddress: %s \ndate: %s \n", this.getTotalPrice(), discountMessage, this.getCustomerAddress(), this.getDate());
    }

    public static void placeOrder(Customer customer) throws IOException {
        boolean keepAdding = true;
        String enteredProduct="";
        String discountMessage="";
        int enteredQuantity=0;
        Scanner userInput = new Scanner(System.in);
        //initialize dictionary to store product and its quantity
        Map<String, Integer> shoppingCart = new HashMap<String, Integer>();

        while (keepAdding){
            //initialize variable for user prompting
            boolean validProductInput = false;
            boolean validQuantity = false;

            //prompt user to enter valid products
            while (!validProductInput){
                System.out.println("Enter product to add into order:");
                enteredProduct = userInput.nextLine();
                for (Product availableProduct : Product.getProductArrayList()){
                    if (enteredProduct.equalsIgnoreCase(availableProduct.getName())){
                        validProductInput = true;
                        break;
                    }
                }
                if (!validProductInput){
                    System.out.println("Product does not exist!");
                }
            }

            //prompt user to enter valid quantity
            while (!validQuantity){
                System.out.println("Quantity(1-99): ");
                try {
                    enteredQuantity = userInput.nextInt();
                    if (enteredQuantity>=1 && enteredQuantity <=99){
                        validQuantity = true;
                    } else {
                        System.out.println("Invalid number!");
                    }
                } catch (InputMismatchException inputMismatchException){
                    System.out.println("Must enter integer!");
                } finally {
                    userInput.nextLine(); //to skip
                }
            }

            //add to cart. Check if entered product is already in dictionary or not. If yes, increase number of that product in cart
            boolean alreadyExist = false;
            for (String item : shoppingCart.keySet()){
                if (enteredProduct.equalsIgnoreCase(item)){
                    alreadyExist = true;
                    shoppingCart.put(item, shoppingCart.get(item) + enteredQuantity);
                }
            }
            if (!alreadyExist){
                shoppingCart.put(enteredProduct, enteredQuantity);
            }
            System.out.println("Added to order.");

            //test to ask user add more or not
            boolean validAnswer = false;
            while (!validAnswer){
                System.out.println("Add more? (yes/no)");
                String addMore = userInput.nextLine();
                if (addMore.equalsIgnoreCase("no")){
                    validAnswer = true;
                    keepAdding = false;
                    //check customer's membership to display discount messase
                    switch (customer.getMembership()){
                        case "Silver":
                            discountMessage += "(discount by 5%)";
                            break;
                        case "Gold":
                            discountMessage += "(discount by 10%)";
                            break;
                        case "Platinum":
                            discountMessage += "(discount by 15%)";
                            break;
                    }
                    int totalPrice = Order.calculateTotalPrice(shoppingCart, customer);

                    String orderId = UUID.randomUUID().toString(); //generate id for order
                    //we will process the dictionary productNameAndQuantity, turn it to string with to format "product:quantity,product:quantity,..."
                    String productNameAndQuantity = "";
                    for (String item : shoppingCart.keySet()){
                        productNameAndQuantity += item + ":" + shoppingCart.get(item) + ",";
                    }
                    productNameAndQuantity = productNameAndQuantity.substring(0, productNameAndQuantity.length() - 1); //get rid of the last comma
                    //turn the string to array to create object
                    String [] productAndQuantityArray = productNameAndQuantity.split(",");
                    //create current date and turn it to string with format "dd-mm-yyyy"
                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
                    String formattedString = date.format(formatter);
                    //create object and print the order for customer
                    Order order = new Order(orderId, customer.getId(), customer.getUserName(), productAndQuantityArray, totalPrice, customer.getAddress(), "delivered", formattedString);
                    System.out.println("Details of your order:");
                    order.display(discountMessage);

                    boolean confirmAnswer = false;
                    while (!confirmAnswer){
                        System.out.println("Please confirm to place order (confirm/cancel):");
                        String userConfirm = userInput.nextLine();
                        if (userConfirm.equalsIgnoreCase("confirm")){
                            confirmAnswer = true;
                            //save new order to initialized arraylist and text file
                            addOrderToFile(order);
                            //update num sold of products ordered
                            Product.updateNumSold(shoppingCart);
                            //update total pay for customer
                            System.out.println(customer.getUserName());
                            customer.updateTotalPay(order.getTotalPrice());
                            customer.updateMembership();
                            //recall initializeOrders and initializeCUstomers to update orders and customer arraylist
                            Customer.initializeCustomers();
                            System.out.println("Placed order successfully!");
                        } else if (userConfirm.equalsIgnoreCase("cancel")){
                            confirmAnswer = true;
                            System.out.println("Canceled your order.");
                        } else {
                            System.out.println("Invalid answer!");
                        }
                    }
                } else if (addMore.equalsIgnoreCase("yes")){
                    validAnswer = true;
                } else {
                    System.out.println("Invalid answer!");
                }
            }
        }
    }

    public void viewOrder(){
        System.out.println(this.toString());
    }

    @Override
    public String toString(){
        //turn array of productAndQuantity to string
        String productNamesAndQuantity = String.join(",", this.getProductNamesAndQuantity());
        return String.format("order id: %s \ncustomer id: %s \ncustomer name: %s \nproducts: %s \ntotal price: %d VND \naddress: %s \nstatus: %s \ndate: %s", this.getORDER_ID(), this.CUSTOMER_ID, this.getCustomerName(), productNamesAndQuantity.toLowerCase(), this.getTotalPrice(),this.getCustomerAddress(), this.getStatus(),this.getDate());
    }

    //getter for all and setter for some field
    public String getORDER_ID() { return ORDER_ID; }

    public String getCUSTOMER_ID() { return CUSTOMER_ID; }

    public String[] getProductNamesAndQuantity() { return productNamesAndQuantity; }

    public String getCustomerName(){ return customerName; }

    public String getStatus() { return status; }

    public void setStatus(String status){ this.status = status; }

    public int getTotalPrice() { return totalPrice; }

    public String getDate() { return date; }

    public static ArrayList<Order> getOrders() { return orders; }

    public String getCustomerAddress() { return customerAddress; }
}
