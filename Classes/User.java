package Classes;/*
RMIT University Vietnam
Course: COSC2081 Programming 1
Semester: 2022C
Assessment: Assignment 3
Authors: Nguyen Quoc An, Pham Minh Hoang, Tran Gia Minh Thong, Yoo Christina
ID: s3938278, s3930051, s3924667, s3938331
Acknowledgement:
RMIT canvas for class casting
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

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
        System.out.println("\n<Login page>");
        java.lang.System.out.println("Username: ");
        String userNameFromUser = input.nextLine();
        java.lang.System.out.println("Password: ");
        String passwordFromUser = input.nextLine();

        String id, userName, password, role,fullName, phoneNumber, email, address, membership;
        //declare variables and assign them to info in text file 
        int totalPay;
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File("Database/account.txt"));
            while (fileScanner.hasNext()){
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ";;;");
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
                    totalPay = Integer.parseInt(inReader.nextToken());

                    loggedIn = true;
                    if (role.equals("customer")){
                        user = new Customer( id, userName, password, fullName, phoneNumber, email, address, membership, totalPay);
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
            System.out.println("Logged in successfully!");
        } else{
            System.out.println("Log in fail!");
        }
        return user;
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
