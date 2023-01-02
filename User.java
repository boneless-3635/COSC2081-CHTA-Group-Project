import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public abstract class  User {
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static User login() throws IOException {
        User user = null;
        boolean loggedIn = false;
        Scanner input = new Scanner(System.in);
        java.lang.System.out.println("username: ");
        String userNameFromUser = input.nextLine();
        java.lang.System.out.println("password: ");
        String passwordFromUser = input.nextLine();
        Scanner fileScanner = new Scanner(new File("account.txt"));
        String userName, password, role, id, fullName, phoneNumber, email, address; //declare variables and assign them to info in text file
        while (fileScanner.hasNext()){
            String line = fileScanner.nextLine();
            StringTokenizer inReader = new StringTokenizer(line, ";");
            userName = inReader.nextToken();
            password = inReader.nextToken();

            if (userNameFromUser.equals(userName) && passwordFromUser.equals(password)){
                role = inReader.nextToken();
                id = inReader.nextToken();
                fullName = inReader.nextToken();
                phoneNumber = inReader.nextToken();
                email = inReader.nextToken();
                address = inReader.nextToken();

                loggedIn = true;
                if (role.equals("customer")){
                    user = new Customer(userName, password, id,fullName, phoneNumber, email, address);
                } else if (role.equals("admin")){
                    user = new Admin(userName, password);
                }
            }
        }
        fileScanner.close();
        if (loggedIn){
            System.out.println("Logged in successfully!");
        } else{
            System.out.println("Log in fail!");
        }
        return user;
    }

    public static boolean validateInput(String userInput, String pattern){
        boolean valid;
        Pattern validPattern = Pattern.compile(pattern);
        if (validPattern.matcher(userInput).find()){
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    public abstract boolean isAdmin();

    //getter n setter
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}