import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.UUID;

public class Customer extends User{
    private final String ID;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;

    public Customer(String userName, String password, String id ,String fullName, String phoneNumber, String email, String address) {
        super(userName, password);
//        this.ID = UUID.randomUUID().toString();
        this.ID = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public static void register() throws IOException {
        while (true){
            System.out.println("Register here: ");
            String errorMessage = "Errors: \n";
            Scanner userInput = new Scanner(java.lang.System.in);
            java.lang.System.out.println("Username: ");
            String userName = userInput.nextLine();
            java.lang.System.out.println("Password: ");
            String password = userInput.nextLine();
            java.lang.System.out.println("Full name: ");
            String fullName = userInput.nextLine();
            java.lang.System.out.println("Phone number: ");
            String phoneNumber = userInput.nextLine();
            java.lang.System.out.println("Email: ");
            String email = userInput.nextLine();
            java.lang.System.out.println("Address: ");
            String address = userInput.nextLine();
            if (!validateInput(userName, "^[a-zA-Z0-9 ]{1,15}$")){
                errorMessage += "Invalid user name (only letters and digits, length 1-15) \n";
            }
            if (!validateInput(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,20}$")){
                errorMessage += "Invalid password (at least 1 lower case, 1 upper case, 1 digit, 1 special characters, length 8-20) \n";
            }
            if (!validateInput(fullName, "^[a-zA-Z ]{5,}$")){
                errorMessage += "Invalid full name (only letters, at least 5 letters) \n";
            }
            if (!validateInput(phoneNumber, "^0[0-9]{9,10}$")){
                errorMessage += "Invalid phone number (must start with 0, 10-11 digits) \n";
            }
            //not optimal email pattern
            if (!validateInput(email, "^.+@.+$")){ //not done////
                errorMessage += "Invalid email (must contains @ in email) \n";
            }
            //not optimal address pattern
            if (!validateInput(address, "^.{10,}$")){ //not done////
                errorMessage += "Invalid address (at least 10 letters) \n";
            }

            if (errorMessage != "Errors: \n"){
                System.out.println(errorMessage);
            } else {
                String userId = UUID.randomUUID().toString(); //generate id for user
                //write registered info to file
                PrintWriter pw = new PrintWriter(new FileWriter("account.txt", true));
                pw.println(userName + ";" + password + ";" + "customer" + ";" + userId + ";" + fullName + ";" + phoneNumber + ";" + email + ";" + address);
                pw.close();
                System.out.println("Register successfully!");
                break;
            }
        }
    }

    @Override
    public String toString(){
        return String.format("username: %s \nfull name: %s \nphone: %s \nemail: %s \naddress %s", this.getUserName(), this.getFullName(), this.getPhoneNumber(), this.getEmail(), this.getAddress());
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
}
