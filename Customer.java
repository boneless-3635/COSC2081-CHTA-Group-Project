/*
RMIT University Vietnam
Course: COSC2081 Programming 1
Semester: 2022C
Assessment: Assignment 3
Authors: Nguyen Quoc An, Pham Minh Hoang, Tran Gia Minh Thong, Yoo Christina
ID: s3938278, s3930051, s3924667, s3938331
Acknowledgement:
*/


import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                List<String> accountFields = Arrays.asList(fileScanner.nextLine().split(";;;"));

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
            if (!Utility.validateInput(userName, "^[a-zA-Z0-9 ]{1,15}$")){
                errorMessage += "Invalid user name \n";
            }
            if (!Utility.validateInput(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,20}$")){
                errorMessage += "Invalid password \n";
            }
            if (!Utility.validateInput(fullName, "^[a-zA-Z ]{5,}$")){
                errorMessage += "Invalid full name \n";
            }
            if (!Utility.validateInput(phoneNumber, "^0[0-9]{9,10}$")){
                errorMessage += "Invalid phone number \n";
            }
            //not optimal email pattern
            if (!Utility.validateInput(email, "^.+@.+$")){
                errorMessage += "Invalid email \n";
            }
            //not optimal address pattern
            if (!Utility.validateInput(address, "^.{10,}$")){
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
                pw.println(userId + ";;;" + userName + ";;;" + password + ";;;" + "customer" + ";;;" + fullName + ";;;" + phoneNumber + ";;;" + email + ";;;" + address + ";;;" + "none" + ";;;0");
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
        this.updateMembership();
    }

    public static boolean checkUniqueness(String filePath, String stringToCheck, int indexToCheck){
        boolean alreadyExist = false;
        try (Scanner fileScanner = new Scanner(Paths.get(filePath))) {
            while (fileScanner.hasNext()){
                String line = fileScanner.nextLine();
                String [] lineArray = line.split(";;;");
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

    public void viewMyOrder() {
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
    public void viewTodaysOrder(){
        int count =0;
        ArrayList<Order> orders = Order.getOrders();

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        String today = date.format(formatter);

        System.out.println("<Today's order>");
        for (Order order: orders){
            if (order.getCUSTOMER_ID().equals(this.ID)&&today.equals(order.getDate())){
                count++;
                order.viewOrder();
                System.out.println("----------");
            }
        }
        if (count==0){
            System.out.println("No order is made today.");
        }
    }

    public void editAddress() {
        Scanner input = new Scanner(System.in);

        System.out.println("<Edit address>\nEnter new address (at least 10 letters):");
        String address = input.nextLine();

        if (!Utility.validateInput(address, "^.{10,}$")){
            System.out.println("Invalid address input");
        } else {
            this.setAddress(address);
            System.out.println("New address is updated!");
        }
    }
    public void editEmail() {
        Scanner input = new Scanner(System.in);

        System.out.println("<Edit email>\nEnter new email:");
        String email = input.nextLine();

        if (!Utility.validateInput(email, "^.+@.+$")){
            System.out.println("Invalid email input");
        } else {
            this.setAddress(address);
            System.out.println("New email is updated!");
        }
    }
    public void editPhone() {
        Scanner input = new Scanner(System.in);

        System.out.println("<Edit phone number>\nEnter new phone number (start with 0, 10-11 numbers):");
        String phone = input.nextLine();

        if (!Utility.validateInput(phone, "^0[0-9]{9,10}$")){
            System.out.println("Invalid phone number input");
        } else {
            this.setPhoneNumber(phone);
            System.out.println("New phone number is updated!");
        }
    }

    public static void listMembers() throws IOException {
        System.out.println("\n<View all members>\n----------");
        for (Customer customer: customers) {
            customer.updateMembership();
            System.out.println(customer);
            System.out.println("----------");
        }
    }

    public void updateMembership() throws IOException {
        ArrayList<String> tempLines = new ArrayList<>();
        String membershipUpdate;
        Scanner accounts = new Scanner(Paths.get("account.txt"));
        while (accounts.hasNext()) {
            String line = accounts.nextLine();
            String[] lineArray = line.split(";;;");
            if (lineArray[1].equalsIgnoreCase(this.getUserName())) {
                int pay = Integer.parseInt(lineArray[9]);
                if (5000000 < pay && pay <= 10000000) {
                    membershipUpdate = "Silver";
                } else if (10000000 < pay && pay <= 25000000) {
                    membershipUpdate = "Gold";
                } else if (25000000 < pay) {
                    membershipUpdate = "Platinum";
                } else {
                    membershipUpdate = "none";
                }
                lineArray[8] = membershipUpdate;
                line = String.join(";;;", lineArray);
            }
            tempLines.add(line);
        }
        accounts.close();
        PrintWriter pw = new PrintWriter(new FileWriter("account.txt"));
        for (String line : tempLines) {
            pw.println(line);
        }
        pw.close();
    }


    public static void membershipNumbers() throws IOException {
        int regular=0, silver=0, gold=0, platinum=0;
        System.out.println("\n<View all members>\n----------");
        for (Customer customer: customers) {
            System.out.println(customer);
            System.out.println("----------");
        }
        Scanner accounts = new Scanner(new File("account.txt"));
        while (accounts.hasNext()) {
            String[] info = accounts.nextLine().split(";;;");
            if (info[8].equals("Silver")) {
                silver++;
            }
            else if (info[8].equals("Gold")) {
                gold++;
            }
            else if (info[8].equals("Platinum")) {
                platinum++;
            }
            else {
                regular++;
            }
        }
        accounts.close();
        System.out.println("<Membership count>");
        System.out.println(regular+" Regular _ "+silver+" Silver _ "+gold+" Gold _ "+platinum+" Platinum");
    }


    @Override
    public String toString(){
        return String.format("User ID: %s \nUsername: %s \nFull name: %s \nPhone: %s \nEmail: %s \nAddress: %s \nMembership: %s \nTotal Pay: %d", this.getId(), this.getUserName(), this.getFullName(), this.getPhoneNumber(), this.getEmail(), this.getAddress(), this.getMembership(), this.getTotalPay());
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
