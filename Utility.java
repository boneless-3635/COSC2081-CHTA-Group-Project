import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Utility {
    public static void updateTextFile(String productName, String updateValue, int indexNeedToUpdate, String fileName) throws IOException {
        //temporary arraylist to store lines
        ArrayList<String> tempLines = new ArrayList<>();

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
             Scanner fileScanner = new Scanner(Paths.get(fileName))) {
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] lineArray = line.split(",");
                //split a line to array, then use index 0 storing id to take out the product need to be updated
                if (lineArray[1].equals(productName)) {
                    //after update the index with given value, turn in to string again
                    lineArray[indexNeedToUpdate] = String.valueOf(updateValue);
                    line = String.join(",", lineArray);
                }
                tempLines.add(line);
            }
            //Write to file again
            for (String line : tempLines) {
                pw.println(line);
            }
        }
    }
}
