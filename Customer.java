import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class Customer extends User{
    private final String ID;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String membership;
    private int totalPay;
    private static ArrayList<Customer> customers = new ArrayList<>();
    
    public Customer(String id , String userName, String password,String fullName, String phoneNumber, String email, String address, String membership) {
        super(userName, password);
        this.ID = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.membership = membership;
        this.totalPay = 0;
    }

    public Customer(String id , String userName, String password,String fullName, String phoneNumber, String email, String address, String membership, int totalPay) {
        super(userName, password);
        this.ID = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.membership = membership;
        this.totalPay = totalPay;
    }

    public static void initializeCustomers(){
        customers.clear();
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File("account.txt"));
            while (fileScanner.hasNext()) {
                //read per line and split line into array
                List<String> accountFields = Arrays.asList(fileScanner.nextLine().split(";"));

//                for (int i =0; i <accountFields.size(); i++){
//                    System.out.println(accountFields.get(i));
//                }

                if (accountFields.get(3).equals("admin")){
                    continue;
                }

                //store customer in arraylist. Notice that we will cast productNames to arraylist to fit with the constructor.
                customers.add(new Customer(accountFields.get(0),
                        accountFields.get(1),
                        accountFields.get(2),
                        accountFields.get(4),
                        accountFields.get(5),
                        accountFields.get(6),
                        accountFields.get(7),
                        accountFields.get(8),
                        Integer.parseInt(accountFields.get(9))
                        ));
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

    public static void register() throws IOException {
        boolean registerLoop = true;
        while (registerLoop){
            System.out.println("Register here: ");
            String errorMessage = "Errors: \n";
            Scanner userInput = new Scanner(System.in);
            String userName, password, fullName, phoneNumber, email, address; //declare necessary fields for registration
            while (true){
                System.out.println("Username (only letters and digits, length 1-15): ");
                userName = userInput.nextLine();
                boolean seemUserName = checkUniqueness("account.txt", userName, 1); //index 1 is username
                if (seemUserName){
                    System.out.println("Username already exists");
                } else {
                    break;
                }
            }
            System.out.println("Password (at least 1 lower case, 1 upper case, 1 digit, 1 special characters, length 8-20): ");
            password = userInput.nextLine();
            System.out.println("Full name (only letters, at least 5 letters): ");
            fullName = userInput.nextLine();
            System.out.println("Phone number (must start with 0, 10-11 digits): ");
            phoneNumber = userInput.nextLine();
            System.out.println("Email (must contains @ in email): ");
            email = userInput.nextLine();
            System.out.println("Address (at least 10 letters): ");
            address = userInput.nextLine();
            if (!validateInput(userName, "^[a-zA-Z0-9 ]{1,15}$")){
                errorMessage += "Invalid user name \n";
            }
            if (!validateInput(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,20}$")){
                errorMessage += "Invalid password \n";
            }
            if (!validateInput(fullName, "^[a-zA-Z ]{5,}$")){
                errorMessage += "Invalid full name \n";
            }
            if (!validateInput(phoneNumber, "^0[0-9]{9,10}$")){
                errorMessage += "Invalid phone number \n";
            }
            //not optimal email pattern
            if (!validateInput(email, "^.+@.+$")){
                errorMessage += "Invalid email \n";
            }
            //not optimal address pattern
            if (!validateInput(address, "^.{10,}$")){
                errorMessage += "Invalid address \n";
            }

            if (errorMessage != "Errors: \n"){
                System.out.println(errorMessage);
            } else {
                String userId;
                while (true){
                    userId = UUID.randomUUID().toString(); //generate id for user
                    boolean seemUserId = checkUniqueness(userId, "account.txt", 0); //index 0 is id
                    if (!seemUserId){
                        break;
                    }
                }
                //write registered info to file
                PrintWriter pw = null;
                pw = new PrintWriter(new FileWriter("account.txt", true));
                pw.println(userId + ";" + userName + ";" + password + ";" + "customer" + ";" + fullName + ";" + phoneNumber + ";" + email + ";" + address + ":" + "none" + ";0");
                pw.close();
                System.out.println("Register successfully!");
                registerLoop = false;
                break;
            }
        }
    }

    public void updateTotalPay(int addPay) throws IOException {
        int updateTotalPay = addPay + this.getTotalPay();
        Utility.updateTextFile(
                this.getUserName(),
                Integer.toString(updateTotalPay),
                1,
                9,
                "account.txt"
        );
    }

    public static boolean checkUniqueness(String filePath, String stringToCheck, int indexToCheck){
        boolean alreadyExist = false;
        try (Scanner fileScanner = new Scanner(Paths.get(filePath))) {
            while (fileScanner.hasNext()){
                String line = fileScanner.nextLine();
                String [] lineArray = line.split(";");
                if (stringToCheck.equals(lineArray[indexToCheck])){
                    alreadyExist = true;
                    break;
                }
            }
        }
        catch (FileNotFoundException fileNotFoundException){
            System.out.println("File path does not exist to read!");
        }
        catch (NoSuchElementException noSuchElementException){
            System.out.println("Info in file is not formatted well!");
        }
        finally {
            return alreadyExist;
        }
    }

    public void viewOrderByOrderID() {
        String orderID;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Order> orders;

        System.out.println("Please enter order ID:");
        orderID = scanner.nextLine();
        orders = Order.getOrders();
        boolean validId = false;
        for (Order order: orders){
            if (order.getORDER_ID().equals(orderID) && order.getCUSTOMER_ID().equals(this.getId())){
                validId = true;
                order.viewOrder();
                System.out.println("----------");
                break;
            }
        }
        if (!validId){
            System.out.println("This order is not belong to you.");
        }
    }

    public void viewUserOrder() {
        String customerID;
        ArrayList<Order> orders;

        customerID = this.getId();
        orders = Order.getOrders();
        for (Order order: orders){
            if (order.getCUSTOMER_ID().equals(customerID)){
                order.viewOrder();
                System.out.println("----------");
            }
        }
    }

    public static void viewUserInfoById(String userID) throws FileNotFoundException {
        for (Customer customer: customers){
            if (userID.equals(customer.getId())){
                System.out.println(customer.toString());
            }
        }
    }

    public static void viewUserInfoByTotalPay(int totalPay) throws FileNotFoundException {
        for (Customer customer: customers){
            if (totalPay == (customer.totalPay)){
                System.out.println(customer.toString());
            }
        }
    }

    public static void viewUserNameAndTotalPayByTotalPay(int totalPay) throws FileNotFoundException {
        for (Customer customer: customers){
            if (totalPay == (customer.totalPay)){
                System.out.println("Name: "+ customer.getUserName());
                System.out.println("Total Pay: "+ customer.getTotalPay());
                System.out.println("----------");
            }
        }
    }

    @Override
    public String toString(){
        return String.format("userid: %s \nusername: %s \nfull name: %s \nphone: %s \nemail: %s \naddress %s \nmembership %s \ntotal Pay: %d \n----------\n", this.getId(), this.getUserName(), this.getFullName(), this.getPhoneNumber(), this.getEmail(), this.getAddress(), this.getMembership(), this.getTotalPay());
    }

    @Override
    public boolean isAdmin (){
        return false;
    }

    //getter n setter

    public String getId() {return ID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMembership() {return membership;}

    public void setMembership(String membership) {this.membership = membership;}

    public int getTotalPay() {return totalPay;}

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }
}
