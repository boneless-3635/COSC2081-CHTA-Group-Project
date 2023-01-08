import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Order {
    private final String ORDER_ID;
    private final String CUSTOMER_ID;
    private String customerName;
    private String[] productNames;
    private int totalPrice;
    private String status;
    private String date;
    private static ArrayList<Order> orders = new ArrayList<>();

    public Order(String ORDER_ID, String CUSTOMER_ID, String customerName, String[] productNames, int totalPrice, String status, String date) {
        this.ORDER_ID = ORDER_ID;
        this.CUSTOMER_ID = CUSTOMER_ID;
        this.customerName = customerName;
        this.productNames = productNames;
        this.status = status;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public static void initializeOrders(){
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File("order.txt"));
            while (fileScanner.hasNext()) {
                //read per line and split line into array
                List<String> orderFields = Arrays.asList(fileScanner.nextLine().split(";"));
                for (int i =0; i <orderFields.size(); i++){
                    System.out.println(orderFields.get(i));
                }

                //index 2 will store product names, convert them to array.
                String [] productNames = orderFields.get(3).split(",") ;

                //store products in arraylist. Notice that we will cast productNames to arraylist to fit with the constructor.
                orders.add(new Order(orderFields.get(0), orderFields.get(1), orderFields.get(2), productNames, Integer.parseInt(orderFields.get(4)), orderFields.get(5), orderFields.get(6)));
            }
        }
        catch (FileNotFoundException fileNotFoundException){
            System.out.println("File path does not exist to read!");
        }
        catch (NoSuchElementException noSuchElementException){
            System.out.println("Info in file is not formatted well!");
        }
        finally {
            fileScanner.close();
        }
    }

    public static void placeOrder(ArrayList <Product> availableProducts){
        boolean keepAdding = true;
        String enteredProduct="";
        int enteredQuantity=0;
        int originalTotalPrice = 0;
        Scanner userInput = new Scanner(System.in);
        //initialize dictionary to store product and its quantity
        Map<String, Integer> shoppingCart = new HashMap<String, Integer>();

        while (keepAdding){
            //initialize variable for user prompting
            boolean validProductInput = false;
            boolean validQuantity = false;

            //initialize variable to assign product's price
            int productPrice=0;

            //prompt user to enter valid products
            while (!validProductInput){
                System.out.println("Enter product to add into order:");
                enteredProduct = userInput.nextLine();
                for (Product availableProduct : availableProducts){
                    if (enteredProduct.equals(availableProduct.getNAME())){
                        validProductInput = true;
                        productPrice = availableProduct.getPrice();
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

            //add to cart and calculate total price. Check if entered product is already in dictionary or not. If yes, increase number of that product in cart
            boolean alreadyExist = false;
            for (String item : shoppingCart.keySet()){
                if (enteredProduct.equals(item)){
                    alreadyExist = true;
                    shoppingCart.put(item, shoppingCart.get(item) + enteredQuantity);
                }
            }
            if (!alreadyExist){
                shoppingCart.put(enteredProduct, enteredQuantity);
            }
            originalTotalPrice += (productPrice * enteredQuantity);
            System.out.println("Added to order.");

            //test to ask user add more or not
            System.out.println("Add more?");
            String addMore = userInput.nextLine();
            if (addMore.equals("no")){
                keepAdding = false;
                System.out.println("Details of your order:");
                for (String item : shoppingCart.keySet()){
                    System.out.println(item + ": " + shoppingCart.get(item));
                }
                System.out.println("total price: " + originalTotalPrice + "VND");
                //TESTING LOOKS GUD, HAVENT HAD SAVE TO FILE CODE.
            }
        }
    }

    public void viewOrder(){
        System.out.println(this.toString());
    }

    @Override
    public String toString(){
        //turn arraylist of product name to string
        String productNames = String.join(", ", this.getProductNames());
        return String.format("order id: %s \n" +
                "customer id: %s \n" +
                "customer name: %s \n" +
                "products: %s \ntotal price: %d \n" +
                "status: %s \n" +
                "date: %s",
                this.getORDER_ID(),
                this.getCUSTOMER_ID(),
                this.getCustomerName(),
                productNames,
                this.getTotalPrice(),
                this.getStatus(),
                this.getDate());
    }

    //getter
    public String getORDER_ID() { return ORDER_ID; }

    public String getCUSTOMER_ID() { return CUSTOMER_ID; }

    public String[] getProductNames() { return productNames; }

    public String getCustomerName(){ return customerName; }

    public String getStatus() { return status; }

    public int getTotalPrice() { return totalPrice; }

    public String getDate() { return date; }

    public static ArrayList<Order> getOrders() { return orders; }
}
