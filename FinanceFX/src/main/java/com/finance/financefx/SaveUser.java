
package com.finance.financefx;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


public class SaveUser {
    
    public static void saveBal(String filePath, double balance){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write(balance + "\n");
            
            System.out.println("Balance: $" + balance + " saved to file.");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static void saveInc (String filePath, double income){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write(income + "\n");
            System.out.println("Income: $" + income + " saved to file");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
