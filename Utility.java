/*
RMIT University Vietnam
Course: COSC2081 Programming 1
Semester: 2022C
Assessment: Assignment 3
Authors: Nguyen Quoc An, Pham Minh Hoang, Tran Gia Minh Thong, Yoo Christina
ID: s3938278, s3930051, s3924667, s3938331
Acknowledgement:

*/

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utility {
    public static void updateTextFile(String nameOrID, String updateValue, int indexSource, int indexNeedToUpdate,
                                      String fileName) throws IOException {
//        We can't remove a row from a csv file with java. This means we have to create an arrayList, we then
//        go through the original file. All the rows are copied over to the arrayList and the "deleted" row is
//        not copied over. Any editing is done in the arrayList. The arrayList is then written to the file.

        //temporary arraylist to store lines
        ArrayList<String> tempLines = new ArrayList<>();
        Scanner fileScanner = new Scanner(Paths.get(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            String [] lineArray = line.split(";;;");
            //split a line to array, then use indexSource storing id or name to take out the product/order need to be updated
            if (lineArray[indexSource].equalsIgnoreCase(nameOrID)){
                //after update the index with given value, turn in to string again
                lineArray[indexNeedToUpdate] = String.valueOf(updateValue);
                line = String.join(";;;", lineArray);
            }
            tempLines.add(line);
        }
        fileScanner.close();
        //Write to file again
        PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        for (String line: tempLines){
            pw.println(line);
        }
        pw.close();
    }

    public static void deleteRowTextFile(String nameOrID, int indexSource, String fileName) throws IOException {
        //temporary arraylist to store lines
        ArrayList<String> tempLines = new ArrayList<>();
        Scanner fileScanner = new Scanner(Paths.get(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            String [] lineArray = line.split(";;;");
            //split a line to array, then use index 0 storing id to take out the product need to be updated
            if (lineArray[indexSource].equalsIgnoreCase(nameOrID)){
                System.out.println("Delete success");
            } else {
                tempLines.add(line);
            }
        }
        fileScanner.close();
        //Write to file again
        PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        for (String line: tempLines){
            pw.println(line);
        }
        pw.close();
    }

    public static boolean validateInput(String userInput, String pattern) {
        Pattern validPattern = Pattern.compile(pattern);
        return !validPattern.matcher(userInput).find();
    }
}