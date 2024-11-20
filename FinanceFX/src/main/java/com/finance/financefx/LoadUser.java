
package com.finance.financefx;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadUser {
    private String filePath;

    // Constructor to initialize the file path
    public LoadUser(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the first line of the file and converts it to a double.
     * 
     * @return The double value from the first line of the file.
     * @throws IOException if there is an issue reading the file.
     * @throws NumberFormatException if the line cannot be converted to a double.
     */
    public double readDouble() throws IOException, NumberFormatException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Read the first line
            if (line == null) {
                throw new IOException("The file is empty.");
            }
            return Double.parseDouble(line); // Convert to double
        }
    }
}
