import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
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

    public static User login() {
        User user = null;
        boolean loggedIn = false;
        Scanner input = new Scanner(System.in);
        java.lang.System.out.println("username: ");
        String userNameFromUser = input.nextLine();
        java.lang.System.out.println("password: ");
        String passwordFromUser = input.nextLine();
        String id, userName, password, role,fullName, phoneNumber, email, address, membership; //declare variables and assign them to info in text file
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File("account.txt"));
            while (fileScanner.hasNext()){
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ";");
                id = inReader.nextToken();
                userName = inReader.nextToken();
                password = inReader.nextToken();

                if (userNameFromUser.equals(userName) && passwordFromUser.equals(password)){
                    role = inReader.nextToken();
                    fullName = inReader.nextToken();
                    phoneNumber = inReader.nextToken();
                    email = inReader.nextToken();
                    address = inReader.nextToken();
                    membership = inReader.nextToken();

                    loggedIn = true;
                    if (role.equals("customer")){
                        user = new Customer(userName, password, id,fullName, phoneNumber, email, address, membership);
                    } else if (role.equals("admin")){
                        user = new Admin(userName, password);
                    }
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
            fileScanner.close();
        }
        if (loggedIn){
            System.out.println("Logged in successfully!\n");
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
