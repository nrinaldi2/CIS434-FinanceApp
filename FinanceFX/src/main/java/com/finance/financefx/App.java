package com.finance.financefx;

import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 * JavaFX Finance App by Nick Rinaldi & Gavin Wells
 */

public class App extends Application {
        //********************************
	private double newMonthlyEarn;
	private double monthlySavings;
	private double savedMonths;
	private double savedwithinterest; 
        //******* GAVIN ******************
        
        
    @Override
    public void start(Stage stage) {
        //          HOME Scene
        AnchorPane hPane = new AnchorPane();
        Scene hScene = new Scene(hPane, 640, 480);
        //          COSTS Scene
        AnchorPane expensesPane = new AnchorPane();
        Scene expensesScene = new Scene(expensesPane, 720, 520);
        //          CALCULATIONS Scene
        AnchorPane calculationsPane = new AnchorPane();
        Scene calculationsScene = new Scene(calculationsPane, 640, 480);
        
        
        //          ADD STYLE SHEET
        hScene.getStylesheets().add("style.css");
        expensesScene.getStylesheets().add("style.css");
        calculationsScene.getStylesheets().add("style.css");
        
        // ACCOUNT OVERVIEW IMAGE
        Image accOImage = new Image("AccountOverview.jpg");
        ImageView hpImageView = new ImageView(accOImage);
        
        
        //          STAGE TITLE & CALL
        stage.setTitle("Finance FX");
        stage.setScene(hScene);
        stage.show();
        
        
        // Declare Load and Save objects for single User
        LoadUser LDub1Bal = new LoadUser("UserBal.txt");
        LoadUser LDub1Inc = new LoadUser("UserInc.txt");
        SaveUser SVub1 = new SaveUser();
        
        
        // User balance object (Single User Account)
        UserBalance ub1 = new UserBalance();
        
        
        // Load previous balance and income if present
        
        // Load Balance
        try{
        ub1.SetTotal(LDub1Bal.readDouble()); 
        }
        catch(IOException | NumberFormatException e){
            System.err.println("Error: " + e.getMessage());
        }
        
        // Load Income
        try{
        ub1.SetIncome(LDub1Inc.readDouble());
        }
        catch(IOException | NumberFormatException e){
            System.err.println("Error: " + e.getMessage());
        }
        
        
        
        
        
        // Table View Declaration and Definition for Monthly Expenses
        
        TableView<MtlyExpense> mtlyExpTV = new TableView();
        
        // Add Columns
        TableColumn<MtlyExpense,String> typeColumn = 
                new TableColumn<>("Type");
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        
        TableColumn<MtlyExpense,String> nameColumn = 
                new TableColumn<>("Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        
        TableColumn<MtlyExpense,String> costColumn = 
                new TableColumn<>("Cost ($)");
        costColumn.setCellValueFactory(
                new PropertyValueFactory<>("cost"));
            
        TableColumn<MtlyExpense,String> DDColumn = 
                new TableColumn<>("Day Due");
        DDColumn.setCellValueFactory(
                new PropertyValueFactory<>("dayDue"));
        
        mtlyExpTV.getColumns().add(typeColumn);
        mtlyExpTV.getColumns().add(nameColumn);
        mtlyExpTV.getColumns().add(costColumn);
        mtlyExpTV.getColumns().add(DDColumn);
        
        // Make Table a resonable size
        mtlyExpTV.setMaxWidth(310);
        mtlyExpTV.setMaxHeight(360);
        
        
        
        // Calculations page net income label
        Label netMtlyIncLbl = new Label("Net monthly income after expenses: $" + String.format("%,.2f", ub1.GetIncAfExp(mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum())));
        
        
        // Load mtlyExpTV with values from file
        try{
              File f = new File("expenses.csv");
              FileReader fr = new FileReader(f);
              BufferedReader file = new BufferedReader(fr);
              
              String line;
              while((line = file.readLine()) != null){
                  String[] parts = line.split(",");
                  String type = parts[0];
                  String name = parts[1];
                  double cost = Double.parseDouble(parts[2]);
                  int dayDue = Integer.parseInt(parts[3]);
                  
                  mtlyExpTV.getItems().add(new MtlyExpense(type, name, cost,dayDue));
              }
              file.close();
          }
          catch(FileNotFoundException ex){
              System.out.println("File not found!");
          }
          catch(IOException ex){
              System.out.println("Problem with file!");
          }
        
        
        
        // Pie chart for earnings vs expenses
        PieChart eViPC = new PieChart();     
   
        PieChart.Data slice1 = new PieChart.Data("Income",ub1.GetInc());
        PieChart.Data slice2 = new PieChart.Data("Expenses",mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum());   

        eViPC.getData().add(slice1);
        eViPC.getData().add(slice2); 

        eViPC.setScaleX(0.6);
        eViPC.setScaleY(0.6);
        
        
        
        
        
//          ACCOUNT OVERVIEW SCENE

        // Account Overview Scene Title label
        Label titleLbl = new Label("Account Overview");
        titleLbl.getStyleClass().add("titleLbl");
//        titleLbl.setFont(new Font("Arial",18));
        
        
        
        
        // Account Overview Label Declarations
        Label totUserAccBalLbl = new Label("Your total account balance is: $" + String.format("%,.2f", ub1.GetTotal()));
        Label updateBalLbl = new Label("Update Account Balance (All checking and cash): ");
        Label userIncLbl = new Label ("Monthly Net Income: $" + String.format("%,.2f", ub1.GetInc()));
        Label updateIncLbl = new Label ("Update Monthly Income (After taxes): ");
        Label addEarnLbl = new Label ("Add unexpected earnings: ");
        
        // Account Overview Prompt Label Declarations
        Label updateBalPromptLbl = new Label ();
        Label updateIncPromptLbl = new Label ();
        Label addEarnPromptLbl = new Label ();
        
        
        // Account Overview Label Formatting
        
        addEarnPromptLbl.setWrapText(true);
        addEarnPromptLbl.setMaxWidth(400);
        
        // Account Overview Text Field Declarations
        TextField updateBalTF = new TextField();
        TextField updateIncTF = new TextField();
        TextField addEarnTF = new TextField();
        
       
        // Account Overview Button Declarations
        
        // Account Overview Button Declarations & Actions
        Button updateBalBtn = new Button("Update");
        updateBalBtn.setOnAction(ActionEvent -> {
            try{
                double userBal = Double.parseDouble(updateBalTF.getText());
                if(userBal > 0){
                ub1.SetTotal(userBal);
                totUserAccBalLbl.setText("Your total account balance is: $" + String.format("%,.2f", userBal));
                updateBalTF.clear();
                updateBalPromptLbl.setText(" ");
                SVub1.saveBal("UserBal.txt", userBal);
                }
                else{
                    updateBalPromptLbl.setText("Please type a positive number greater than zero above.");
                    updateBalTF.clear();
                }
                
            }
            catch (Exception e){
                updateBalPromptLbl.setText("Please type a positive number greater than zero above.");
                updateBalTF.clear();
            } 
        }); //Update balance button
        
        Button updateIncBtn = new Button ("Update");
        updateIncBtn.setOnAction(ActionEvent ->{
            try{
                double userInc = Double.parseDouble(updateIncTF.getText());
                if(userInc > 0){
               
                ub1.SetIncome(userInc);
                userIncLbl.setText("Monthly Net Income: $" + String.format("%,.2f", userInc));
                updateIncPromptLbl.setText("Income updated");
                updateIncTF.clear();
                SVub1.saveInc("UserInc.txt", userInc);
                
                slice1.setPieValue(userInc);
                }
                else{
                    updateIncPromptLbl.setText("Please type a positive number greater than zero above.");
                    updateIncTF.clear();
                }
                
            }
            catch (Exception e){
                updateIncPromptLbl.setText("Please type a positive number greater than zero above.");
                updateIncTF.clear();
            }
        }); //Update income button
        
        
        Button addEarnBtn = new Button ("Add");
        addEarnBtn.setOnAction(ActionEvent ->{
        try{
                double unexEarn = Double.parseDouble(addEarnTF.getText());
                if(unexEarn > 0){
                ub1.AddEarn(unexEarn);
                addEarnPromptLbl.setText("$" + unexEarn + " has been added to your balance.");
                totUserAccBalLbl.setText("Your total account balance is: $" + String.format("%,.2f", ub1.GetTotal()));
                addEarnTF.clear();
                SVub1.saveBal("UserBal.txt", ub1.GetTotal());
                }
                else{
                    addEarnPromptLbl.setText("Please type a positive number greater than zero above.");
                    addEarnTF.clear();
                }
                
            }
            catch (Exception e){
                addEarnPromptLbl.setText("Please type a positive number greater than zero above.");
                addEarnTF.clear();
            }
        }); // Add earnings button
        
        
        Button undoEarnBtn = new Button("Undo");
        undoEarnBtn.setOnAction(ActionEvent ->{
            if(ub1.GetPrevEarn() != 0){
            ub1.UndoEarn();
            addEarnPromptLbl.setText("Previous earnings have been removed from your balance.");
            totUserAccBalLbl.setText("Your total account balance is: $" + String.format("%,.2f", ub1.GetTotal()));
            SVub1.saveBal("UserBal.txt", ub1.GetTotal());
            }
            else{
            addEarnPromptLbl.setText("Can only undo the last earning. You must reset total balance or add "
                    +"unwanted earnings to losses on expense page if you wish to remove more.");
            }
        }); // Undo last unexpected earning
        
        
        Button expSceneBtn = new Button ("Expenses");
        expSceneBtn.setOnAction(ActionEvent ->{
            // Clear all fields & prompts
            updateBalTF.clear();
            updateIncTF.clear();
            addEarnTF.clear();
            updateBalPromptLbl.setText(" ");
            updateIncPromptLbl.setText(" ");
            addEarnPromptLbl.setText(" ");
         
            
            stage.setScene(expensesScene);
        }); //Go to expenses page
        
        Button calcSceneBtn = new Button ("Calculations");
        calcSceneBtn.setOnAction(ActionEvent ->{
            // Clear all fields & prompts
            updateBalTF.clear();
            updateIncTF.clear();
            addEarnTF.clear();
            updateBalPromptLbl.setText(" ");
            updateIncPromptLbl.setText(" ");
            addEarnPromptLbl.setText(" "); 
            
        // Update net income on calc page if there is a new monthly income or monthly expense
        netMtlyIncLbl.setText("Net monthly income after expenses: $" + String.format("%,.2f", ub1.GetIncAfExp(mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum())));
        
        
        stage.setScene(calculationsScene);
        }); //Go to calculations page
        
        
        //          Account Overview Placements
        AnchorPane.setTopAnchor(titleLbl, 20.0); //Title
        AnchorPane.setLeftAnchor(titleLbl, 240.0);
        
        
        AnchorPane.setTopAnchor(totUserAccBalLbl,80.0); //User balance Lbl
        AnchorPane.setLeftAnchor(totUserAccBalLbl,40.0);
        
        AnchorPane.setTopAnchor(updateBalLbl, 135.0); //Update Balance Lbl
        AnchorPane.setLeftAnchor(updateBalLbl, 40.0);
        
        AnchorPane.setTopAnchor(updateBalTF, 160.0); //Update Balance TF
        AnchorPane.setLeftAnchor(updateBalTF, 40.0);
        
        AnchorPane.setTopAnchor(updateBalBtn, 160.0); //Update Balance Btn
        AnchorPane.setLeftAnchor(updateBalBtn, 200.0);
        
        AnchorPane.setTopAnchor(updateBalPromptLbl, 185.0);//Update Balance prompt Lbl
        AnchorPane.setLeftAnchor(updateBalPromptLbl, 40.0);
        
        
        AnchorPane.setTopAnchor(userIncLbl,215.0); //User net monthly income Lbl
        AnchorPane.setLeftAnchor(userIncLbl,40.0);
        
        AnchorPane.setTopAnchor(updateIncLbl,245.0); //Update net monthly income Lbl
        AnchorPane.setLeftAnchor(updateIncLbl,40.0);
        
        AnchorPane.setTopAnchor(updateIncTF,270.0); //Update net monthly income TF
        AnchorPane.setLeftAnchor(updateIncTF,40.0);
        
        AnchorPane.setTopAnchor(updateIncBtn,270.0); //User net monthly income Btn
        AnchorPane.setLeftAnchor(updateIncBtn,200.0);
        
        AnchorPane.setTopAnchor(updateIncPromptLbl,295.0); //User net monthly income prompt Lbl
        AnchorPane.setLeftAnchor(updateIncPromptLbl,40.0);
        
        
        AnchorPane.setTopAnchor(addEarnLbl,330.0); //Add unexpected earnings Lbl
        AnchorPane.setLeftAnchor(addEarnLbl,40.0);
        
        AnchorPane.setTopAnchor(addEarnTF,350.0); //Add unexpected earnings TF
        AnchorPane.setLeftAnchor(addEarnTF,40.0);
        
        AnchorPane.setTopAnchor(addEarnBtn,350.0); //Add unexpected earnings Btn
        AnchorPane.setLeftAnchor(addEarnBtn,200.0);
        
        AnchorPane.setTopAnchor(undoEarnBtn,350.0); //Undo last earnings Btn
        AnchorPane.setLeftAnchor(undoEarnBtn,250.0);
        
        AnchorPane.setTopAnchor(addEarnPromptLbl, 380.0);//Add Earnings prompt Lbl
        AnchorPane.setLeftAnchor(addEarnPromptLbl, 40.0);
        
        
        AnchorPane.setBottomAnchor(expSceneBtn, 20.0);//Expenses Scene Btn
        AnchorPane.setLeftAnchor(expSceneBtn, 40.0);
        
        AnchorPane.setBottomAnchor(calcSceneBtn, 20.0);//Calculations Scene Btn
        AnchorPane.setRightAnchor(calcSceneBtn, 40.0);
        
        AnchorPane.setBottomAnchor(hpImageView, 100.0);//Account overview image
        AnchorPane.setRightAnchor(hpImageView, -50.0);
        
        //Show all placements on Account Overview Scene
        hPane.getChildren().addAll(titleLbl, totUserAccBalLbl,updateBalLbl,
        updateBalTF, updateBalBtn,updateBalPromptLbl,userIncLbl,
        updateIncLbl,updateIncTF,updateIncBtn,updateIncPromptLbl,addEarnLbl,addEarnTF,addEarnBtn,undoEarnBtn,addEarnPromptLbl,
        expSceneBtn,calcSceneBtn,hpImageView);
        
        
        
        
//          EXPENSES SCENE
       
   
        // Expenses Label Declarations
        Label expensesLbl = new Label("Expenses");
        
        expensesLbl.getStyleClass().add("titleLbl");
//        expensesLbl.setFont(Font.font("cursive"));
        
        
        Label addMtlyExpLbl = new Label("Add Monthly Expense");
        addMtlyExpLbl.getStyleClass().add("addMtlyExpLbl");
        
        Label addMtlyTypeLbl = new Label("Type (utility, expected necessities, subscriptions, etc.): ");
        Label addMtlyNameLbl = new Label("Name: ");
        Label addMtlyCostLbl = new Label("Cost: ");
        Label addMtlyDDLbl = new Label("Day due (each month): ");
        Label addUnexLossLbl = new Label ("Add unexpected losses: ");
        Label mtlyExpLbl = new Label("Monthly Costs");
        Label totMtlyExpLbl = new Label ("Monthly Expenses Total: $");
        
        // Expenses Prompt Label Declarations
        Label addMtlyExpPromptLbl = new Label (" ");
        Label addUnexExpPromptLbl = new Label (" ");
        
        // Expenses Label formatting
        addUnexExpPromptLbl.setWrapText(true);
        addUnexExpPromptLbl.setMaxWidth(270);
        
        // Expenses Text Field Declarations
        TextField addMtlyTypeTF = new TextField();
        TextField addMtlyNameTF = new TextField();
        TextField addMtlyCostTF = new TextField();
        TextField addMtlyDDTF = new TextField();
        TextField addUnexLossTF = new TextField ();
        
        
        // Developing calendar to see each expense? (Nick)
        
        // Expenses Button Declarations
        
        // Calendar button declaration and action (Gavin)
        Button calendarBtn = new Button("Calendar");
        calendarBtn.setOnAction(e -> {
            System.out.println("Calendar button clicked");
            // Add functionality to open calendar, if needed
        });
        
        calendarBtn.setLayoutX(10); // X position near the left edge
        calendarBtn.setLayoutY(10); // Y position near the top edge

   
        Button addUnexBtn = new Button("Add");
        addUnexBtn.setOnAction(ActionEvent ->{
            try {
                double unexCost = Double.parseDouble(addUnexLossTF.getText());
                
                if(unexCost > 0){
                ub1.AddExp(unexCost); // Subtract unexpected expense from total
                addUnexExpPromptLbl.setText("$" + String.format("%,.2f", unexCost) + " has been subtracted from your balance. Your balance is now $" + String.format("%,.2f",ub1.GetTotal()));
                addUnexLossTF.clear(); // Clear text field
                
                SVub1.saveBal("UserBal.txt", ub1.GetTotal()); // Update balance file
                }
                else if (unexCost < 0){
                ub1.AddExp(-unexCost); // Subtract unexpected expense from total
                addUnexExpPromptLbl.setText("$" + String.format("%,.2f", -unexCost) + " has been subtracted from your balance. Your balance is now $" + String.format("%,.2f",ub1.GetTotal()));
                addUnexLossTF.clear(); // Clear text field
                
                SVub1.saveBal("UserBal.txt", ub1.GetTotal()); // Update balance file
                }
                else{
                    addUnexExpPromptLbl.setText("Please enter a nonzero number above...");
                    addUnexLossTF.clear(); // Clear text field
                }
            }
            catch(Exception e){
                    addUnexExpPromptLbl.setText("Please enter a nonzero number above...");
                    addUnexLossTF.clear(); // Clear text field
            }
            
        });
        

        Button backBtn = new Button ("Back");
        backBtn.setOnAction(ActionEvent ->{
        //Clear textfields
        addMtlyTypeTF.clear();
        addMtlyNameTF.clear();
        addMtlyCostTF.clear();
        addMtlyDDTF.clear();
        addUnexLossTF.clear();
        
        addMtlyExpPromptLbl.setText(" ");
        addUnexExpPromptLbl.setText(" ");
        
        totUserAccBalLbl.setText("Your total account balance is: $" + String.format("%,.2f", ub1.GetTotal())); // Refresh balance label
        userIncLbl.setText("Monthly Net Income: $" + String.format("%,.2f", ub1.GetInc())); // Refresh income label
        
        stage.setScene(hScene);
        }); // Returns to Account Overview scene from Expenses scene
        
        
        
        
        
        // Monthly Expenses Buttons
        Button addMtlyExpBtn = new Button ("Add Monthly Expense");
        addMtlyExpBtn.setOnAction(ActionEvent ->{
            try{
                String type = addMtlyTypeTF.getText();
                String name = addMtlyNameTF.getText();
                double cost = Double.parseDouble(addMtlyCostTF.getText());
                int dayDue = Integer.parseInt(addMtlyDDTF.getText());
                
                
                if(name.isEmpty()){
                    addMtlyExpPromptLbl.setText("You must at least provide a name to the expense.");
                }
                else if(cost > 0 && dayDue > 0 && dayDue < 32 ){
                    mtlyExpTV.getItems().add(new MtlyExpense(type,name,cost,dayDue));
                    addMtlyExpPromptLbl.setText("Expense added.");
                    
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("expenses.csv", true))) {
                    // Append the instance variables to the file
                    writer.write(type + "," + name + "," +  cost + "," + dayDue);
                    writer.newLine();
                    System.out.println("Expense successfully written to file");
                    } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    }
                    
                    slice2.setPieValue(mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum());
                    
                    addMtlyTypeTF.clear();
                    addMtlyNameTF.clear();
                    addMtlyCostTF.clear();
                    addMtlyDDTF.clear();
                    
                    totMtlyExpLbl.setText("Monthly Expenses Total: $" + String.format("%,.2f", mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum())); // Update total Lbl
                }
                else if(cost < 0 && dayDue > 0 && dayDue < 32 ){
                    mtlyExpTV.getItems().add(new MtlyExpense(type,name,-cost,dayDue));
                    addMtlyExpPromptLbl.setText("Expense added.");
                    
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("expenses.csv", true))) {
                    // Append the instance variables to the file
                    writer.write(type + "," + name + "," +  -cost + "," + dayDue);
                    writer.newLine();
                    System.out.println("Expense successfully written to file");
                    } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    }
                    
                    slice2.setPieValue(mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum());
                    
                    addMtlyTypeTF.clear();
                    addMtlyNameTF.clear();
                    addMtlyCostTF.clear();
                    addMtlyDDTF.clear();
                    
                    totMtlyExpLbl.setText("Monthly Expenses Total: $" + String.format("%,.2f", mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum())); // Update total Lbl
                }
                else{
                    addMtlyExpPromptLbl.setText("Please type a cost and a day between 0 and 32");
                }
    
            }
            catch(Exception e){
                addMtlyExpPromptLbl.setText("Something was not typed correctly...");
            }
        });
        
        Button removeMtlyExpBtn = new Button ("Remove");
        removeMtlyExpBtn.setOnAction(ActionEvent ->{
            
            // Create Mtly expense object so that selection can be converted to csv format for comparison to file
            MtlyExpense selectedItem = mtlyExpTV.getSelectionModel().getSelectedItem();
            
            if (selectedItem == null){
                addMtlyExpPromptLbl.setText("No item selected to remove.");
                return;
            }
            
            // Remove item
            mtlyExpTV.getItems().remove(mtlyExpTV.getSelectionModel().getSelectedItem());

            
            // Remove selection from file
            try {
            List<String> lines = new BufferedReader(new FileReader("expenses.csv"))
                    .lines()
                    .filter(line -> !line.equals(selectedItem.toCSV()))
                    .collect(Collectors.toList());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("expenses.csv"))) {
                if(!lines.isEmpty()){
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
                totMtlyExpLbl.setText("Monthly Expenses Total: $" + String.format("%,.2f", mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum())); // Update total Lbl
                }
                else {
                    writer.write("");
                    totMtlyExpLbl.setText("Monthly Expenses Total: $" + String.format("%,.2f", 0.0)); // Update total Lbl
                }
            }
            
            
            System.out.println("Expense successfully removed from file.");
            
        } 
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
            
            slice2.setPieValue(mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum()); // Update eVi pie chart
            
            mtlyExpTV.getSelectionModel().clearSelection();
            
            
        }); // Remove an expense from the table
        
        
        // Total Monthly Expenses Calculation and insertion below table
        totMtlyExpLbl.setText("Monthly Expenses Total: $" + String.format("%,.2f", mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum()));
        
        
        // Expenses page placements
        AnchorPane.setTopAnchor(expensesLbl, 20.0); //Title
        AnchorPane.setLeftAnchor(expensesLbl, 260.0);
        
        
        AnchorPane.setTopAnchor(addMtlyExpLbl, 80.0); //Add Monthly Expense Lbl
        AnchorPane.setLeftAnchor(addMtlyExpLbl, 20.0);
        
        
        AnchorPane.setTopAnchor(addMtlyTypeLbl, 110.0); //Add Monthly Type Lbl
        AnchorPane.setLeftAnchor(addMtlyTypeLbl, 20.0);
        
        AnchorPane.setTopAnchor(addMtlyTypeTF, 130.0); //Add Monthly Type TF
        AnchorPane.setLeftAnchor(addMtlyTypeTF, 20.0);
 
        
        AnchorPane.setTopAnchor(addMtlyNameLbl, 160.0); //Add Monthly Name Lbl
        AnchorPane.setLeftAnchor(addMtlyNameLbl, 20.0);
        
        AnchorPane.setTopAnchor(addMtlyNameTF, 180.0); //Add Monthly Name TF
        AnchorPane.setLeftAnchor(addMtlyNameTF, 20.0);
        
        
        AnchorPane.setTopAnchor(addMtlyCostLbl, 210.0); //Add Monthly Cost Lbl
        AnchorPane.setLeftAnchor(addMtlyCostLbl, 20.0);
        
        AnchorPane.setTopAnchor(addMtlyCostTF, 230.0); //Add Monthly Cost TF
        AnchorPane.setLeftAnchor(addMtlyCostTF, 20.0);
        
        
        AnchorPane.setTopAnchor(addMtlyDDLbl, 260.0); //Add Monthly Due Day Lbl
        AnchorPane.setLeftAnchor(addMtlyDDLbl, 20.0);
        
        AnchorPane.setTopAnchor(addMtlyDDTF, 280.0); //Add Monthly Due Day TF
        AnchorPane.setLeftAnchor(addMtlyDDTF, 20.0);
        
        
        AnchorPane.setTopAnchor(addMtlyExpBtn, 320.0); //Add Monthly Expense Btn
        AnchorPane.setLeftAnchor(addMtlyExpBtn, 20.0);
        
        
        AnchorPane.setTopAnchor(addUnexLossLbl, 370.0); //Add Unexpected Loss Lbl
        AnchorPane.setLeftAnchor(addUnexLossLbl, 20.0);
        
        AnchorPane.setTopAnchor(addUnexLossTF, 390.0); //Add Unexpected Loss TF
        AnchorPane.setLeftAnchor(addUnexLossTF, 20.0);
        
        AnchorPane.setTopAnchor(addUnexBtn, 390.0); //Add Unexpected Loss Btn
        AnchorPane.setLeftAnchor(addUnexBtn, 180.0);
        
        AnchorPane.setTopAnchor(addUnexExpPromptLbl, 420.0); //Add Unexpected Loss Prompt Lbl
        AnchorPane.setLeftAnchor(addUnexExpPromptLbl, 20.0);
        
        
        AnchorPane.setTopAnchor(mtlyExpTV, 60.0); //Add Monthly Expense Table View
        AnchorPane.setRightAnchor(mtlyExpTV, 15.0);
        
        AnchorPane.setTopAnchor(mtlyExpLbl, 40.0); //Add Monthly Expense Table Title Lbl
        AnchorPane.setRightAnchor(mtlyExpLbl, 250.0);
        
        AnchorPane.setTopAnchor(addMtlyExpPromptLbl, 15.0); //Add Monthly Expense Table Prompt Lbl
        AnchorPane.setLeftAnchor(addMtlyExpPromptLbl, 395.0);
        
        AnchorPane.setBottomAnchor(removeMtlyExpBtn, 70.0); //Add Monthly Expense Remove Btn
        AnchorPane.setRightAnchor(removeMtlyExpBtn, 265.0);
        
        AnchorPane.setBottomAnchor(totMtlyExpLbl, 75.0); //Add Monthly Expense Table Total Lbl
        AnchorPane.setLeftAnchor(totMtlyExpLbl, 490.0);
        
        AnchorPane.setBottomAnchor(backBtn, 20.0); //Add Back Btn
        AnchorPane.setRightAnchor(backBtn, 40.0);
        
       
        expensesPane.getChildren().addAll(expensesLbl,addMtlyExpLbl,
                addMtlyTypeLbl,addMtlyTypeTF,addMtlyCostLbl,addMtlyCostTF,addMtlyNameLbl,addMtlyNameTF,
                addMtlyDDLbl,addMtlyDDTF,addUnexLossLbl,addUnexLossTF,mtlyExpLbl,
                totMtlyExpLbl,addMtlyExpPromptLbl,
                addUnexExpPromptLbl,addMtlyExpBtn,removeMtlyExpBtn,
                addUnexBtn,mtlyExpTV,backBtn);
        
   
  
   
        
//          CALCULATIONS SCENE
        
        // Calulation Labels
        Label calcLbl = new Label("Calculations");
        
        calcLbl.getStyleClass().add("titleLbl");
//        calcLbl.setFont(Font.font("cursive"));
        
        
        Label netAfSavLbl = new Label("Calculate net monthly income after desired savings");
        netAfSavLbl.getStyleClass().add("netAfSavLbl");
        
        Label pSavedLbl = new Label("Percent of income saved (monthly): ");
        Label pLbl = new Label ("%"); // Meant to be put next to respective text field
        Label pSaved2Lbl = new Label("Percent of income saved: " + "%"); // NOTE: This is fully set once first pSaved is entered
        
        Label calcFBalLbl = new Label("Calculate future balance");
        calcFBalLbl.getStyleClass().add("calcFBalLbl");
        
        Label calcFBMonthsLbl = new Label("Months: ");
        
        // Calculation Info Prompt Labels
        Label netASPromptLbl = new Label(" "); // after savings calculation 
        Label netASPrompt2Lbl = new Label(" "); // after savings calculation with amount saved per month
        Label calcFBPromptLbl = new Label(" "); // after future balance calculation
        Label calcFBPrompt2Lbl = new Label(" "); // after future balance calculation with amount saved in amount of time
        
        Label investedPromptLbl = new Label(""); // displays investment calculation
        investedPromptLbl.setWrapText(true);
        investedPromptLbl.setMaxWidth(250);
        
        // Calculation Text Fields
        //months and percent of monthly earnings
        TextField pSavedTF = new TextField("");
        TextField calcFBMonthsTF = new TextField("");

        // Calculation Button Declarations and Actions
        Button netAfSavBtn = new Button("Calculate");
        Button calcFbBtn = new Button("Calculate");
        
        Button backBtn2 = new Button("Back");
        backBtn2.setOnAction(ActionEvent ->{
        pSavedTF.clear(); // Clear text field  
        netASPromptLbl.setText(" "); // Clear prompt
        netASPrompt2Lbl.setText(" "); // Clear prompt
        calcFBMonthsTF.clear(); // Clear text field
        calcFBPromptLbl.setText(" "); // Clear Prompt
        calcFBPrompt2Lbl.setText(" "); // Clear Prompt
        investedPromptLbl.setText("");
        
        totUserAccBalLbl.setText("Your total account balance is: $" + String.format("%,.2f", ub1.GetTotal())); // Refresh balance label
        userIncLbl.setText("Monthly Net Income: $" + String.format("%,.2f", ub1.GetInc())); // Refresh income label
        
        stage.setScene(hScene);
        }); // Return to Account Overview Scene from Calculations scene
        
        
        // Back button declaration & action for savings accrued vs. months graph
        Button backBtn3 = new Button("Back");
        backBtn3.setOnAction(ActionEvent ->{
        stage.setScene(calculationsScene);
        }); // Return to Calculations scene from graph
        
        backBtn3.setTranslateX(20.0); // Move back button right 20 pixels
        backBtn3.setTranslateY(40.0); // Move back button down 20 pixels
        
        Button sVsMtsGraphBtn = new Button("Graph Savings vs. Months");
        
        //Start display of the savings accrued vs. months graph which shows months on the x axis and shows total savings on the y axis
        sVsMtsGraphBtn.setOnAction(event -> {
            pSavedTF.clear(); // Clear text field
            netASPromptLbl.setText(" "); // Clear prompt
            netASPrompt2Lbl.setText(" "); // Clear prompt
            calcFBMonthsTF.clear(); // Clear text field
            calcFBPromptLbl.setText(" "); // Clear Prompt
            calcFBPrompt2Lbl.setText(" "); // Clear Prompt
            investedPromptLbl.setText("");
            
            
        	LineChart<Number, Number> lineChart = createSavingsGraph(); // Create graph
            VBox graphLayout = new VBox(10, lineChart, backBtn3); // Add graph and back button in a VBox
            stage.setScene(new Scene(graphLayout, 600, 500)); // Change scene to graph display
        	
        });
        
        
        Button InvestedFunds = new Button("Project savings if invested in S&P500");
        //Displays what the total compounded worth of the savings could be at the end of the set term in months if invested in the S&P500 index assuming 8% annual return
        //8% Per year also averages 0.67% return on investment per month as the set rate
        InvestedFunds.setOnAction(event -> {
        	savedwithinterest = monthlySavings;
        for(int i = 0; i <= savedMonths; i++) {
        	
        	savedwithinterest *= 1.0067;
        	savedwithinterest += monthlySavings;
        }
        investedPromptLbl.setText("This is the worth of your savings if invested throughout that entire time: $" + String.format("%,.2f", savedwithinterest));
        });
        
        
        
        
        
//Calculations for providing income based on the saved percent by the user -Gavin Wells
//Printing out currently just in System. Need Nick to make a text field to print out the projected income post savings
        netAfSavBtn.setOnAction(event -> {
            try {
            double savedPC = Double.parseDouble(pSavedTF.getText()); // (NICK) Adjusted to parse for double (in case user wants decimal percentage)
            
            if(savedPC > 0 && savedPC < 100){
            pSaved2Lbl.setText("Percent of income saved: " + savedPC + "%");
            
            System.out.println("Retrieved income for calculation: " + ub1.GetInc());                                                                          
            newMonthlyEarn = ub1.GetIncAfExp(mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum()) - (ub1.GetIncAfExp(mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum()) * (savedPC / 100.0)); // Store the result in the class-level variable
            monthlySavings = ub1.GetIncAfExp(mtlyExpTV.getItems().stream().mapToDouble(MtlyExpense::getCost).sum()) * (savedPC / 100.0);             //(NICK) Swapped local variable for direct income retrieval from 
                                                                                //       ub1 object
            netASPromptLbl.setText("New monthly earnings after savings: $" + String.format("%,.2f", newMonthlyEarn)); // (NICK) Savings text is now sent to prompt for it
            netASPrompt2Lbl.setText("You will be saving $" + String.format("%,.2f", monthlySavings) + " per month.");
            
            
            pSavedTF.clear(); // Clear text field
            }
            else{
                netASPromptLbl.setText("Please enter a percentage between 0 and 100");
                netASPrompt2Lbl.setText("");
                
            }
            
            } 
            catch (NumberFormatException e) {
                System.out.println("Invalid input: " + e.getMessage()); //(NICK) Rearranged catch so that any errors caught during calc attempt are output
            pSavedTF.clear(); // Clear text field 
            }
            
        });
        //End of Calculation for percent of saved monthly income
        
        
        
        //Start of Calculation for Total Saved amount after modified income 
        // (NICK) My original intention for this calculation was to show what the user's 
        //        balance would be in x amount of months after saving whatever percentage 
        //        they entered of their paycheck per month.
        calcFbBtn.setOnAction(event -> {
        	 try {
                 savedMonths = Double.parseDouble(calcFBMonthsTF.getText());
                 if(savedMonths > 0){
        	 double totalAFSav = ub1.GetTotal()+(savedMonths * monthlySavings);
        	 calcFBPromptLbl.setText("Your balance will be around $" + String.format("%,.2f",totalAFSav) + " in " + savedMonths + " months.");
                 calcFBPrompt2Lbl.setText("You will have saved $" + String.format("%,.2f",(savedMonths * monthlySavings)) + " in that time.");
                 
                 calcFBMonthsTF.clear(); // Clear text field
                 }
                 else{
                     calcFBPromptLbl.setText("Please enter a number of months greater than zero");
                 }
                 } 
                 catch (NumberFormatException e) {
                 System.out.println("Invalid input: " + e.getMessage());
                 calcFBMonthsTF.clear(); // Clear text field
                 }
        });
        
        
        
        
        
        
        
        
        // Calculations Scene Placements
        AnchorPane.setTopAnchor(calcLbl, 20.0); //Title
        AnchorPane.setLeftAnchor(calcLbl, 240.0);
        
        
        AnchorPane.setTopAnchor(netMtlyIncLbl, 80.0); // Net Monthly Income Lbl
        AnchorPane.setLeftAnchor(netMtlyIncLbl, 40.0);
        
        
        AnchorPane.setTopAnchor(netAfSavLbl, 140.0); // Net Monthly Income After Savings Lbl
        AnchorPane.setLeftAnchor(netAfSavLbl, 40.0);
        
        AnchorPane.setTopAnchor(pSavedLbl, 160.0); // Percent Saved Lbl
        AnchorPane.setLeftAnchor(pSavedLbl, 40.0);
        
        AnchorPane.setTopAnchor(pSavedTF, 180.0); // Percent Saved TF
        AnchorPane.setLeftAnchor(pSavedTF, 40.0);
        
        AnchorPane.setTopAnchor(pLbl, 185.0); // Percent Lbl
        AnchorPane.setLeftAnchor(pLbl, 190.0);
        
        AnchorPane.setTopAnchor(netAfSavBtn, 180.0); // Net Monthly Income Calculation Btn
        AnchorPane.setLeftAnchor(netAfSavBtn, 220.0);
        
        AnchorPane.setTopAnchor(netASPromptLbl, 210.0); // Net Monthly Income After Savings Prompt Lbl
        AnchorPane.setLeftAnchor(netASPromptLbl, 40.0);
        
        AnchorPane.setTopAnchor(netASPrompt2Lbl, 225.0); // Net Monthly Income After Savings Prompt 2 Lbl
        AnchorPane.setLeftAnchor(netASPrompt2Lbl, 40.0);
        
        
        AnchorPane.setTopAnchor(calcFBalLbl, 250.0); // Calculate Future Balance Lbl
        AnchorPane.setLeftAnchor(calcFBalLbl, 40.0);
        
        AnchorPane.setTopAnchor(pSaved2Lbl, 270.0); // Percent of Monthly Income Saved (monthly)(Relies on past input)
        AnchorPane.setLeftAnchor(pSaved2Lbl, 40.0);
        
        AnchorPane.setTopAnchor(calcFBMonthsLbl, 290.0); // Calculate Future Balance Months Lbl
        AnchorPane.setLeftAnchor(calcFBMonthsLbl, 40.0);
        
        AnchorPane.setTopAnchor(calcFBMonthsTF, 310.0); // Calculate Future Balance Months TF
        AnchorPane.setLeftAnchor(calcFBMonthsTF, 40.0);
        
        AnchorPane.setTopAnchor(calcFbBtn,350.0); // Calculate Future Balance Months Btn
        AnchorPane.setLeftAnchor(calcFbBtn, 40.0);
        
        AnchorPane.setTopAnchor(calcFBPromptLbl, 380.0); // Calculate Future Balance Prompt Lbl
        AnchorPane.setLeftAnchor(calcFBPromptLbl, 40.0);
        
        AnchorPane.setTopAnchor(calcFBPrompt2Lbl, 395.0); // Calculate Future Balance Prompt 2 Lbl
        AnchorPane.setLeftAnchor(calcFBPrompt2Lbl, 40.0);
        
        
        AnchorPane.setBottomAnchor(backBtn2, 20.0); // Back Btn
        AnchorPane.setLeftAnchor(backBtn2, 40.0);
        
        
        AnchorPane.setBottomAnchor(sVsMtsGraphBtn,350.0); 
        AnchorPane.setLeftAnchor(sVsMtsGraphBtn, 400.0);
        
        
        AnchorPane.setBottomAnchor(InvestedFunds, 310.0);
        AnchorPane.setLeftAnchor(InvestedFunds, 400.0);
        
        AnchorPane.setTopAnchor(investedPromptLbl, 175.0);
        AnchorPane.setLeftAnchor(investedPromptLbl, 400.0);
        
        
        AnchorPane.setBottomAnchor(eViPC, -50.0); // Earnings vs. Expenses Pie Chart
        AnchorPane.setRightAnchor(eViPC, -90.0);
        
        
        
        calculationsPane.getChildren().addAll(calcLbl,netMtlyIncLbl,
                netAfSavLbl,pSavedLbl,pLbl,pSaved2Lbl,
                calcFBalLbl,calcFBMonthsLbl,netASPromptLbl,
                calcFBPromptLbl,pSavedTF,calcFBMonthsTF,
                netAfSavBtn,calcFbBtn, backBtn2, sVsMtsGraphBtn, InvestedFunds,netASPrompt2Lbl,calcFBPrompt2Lbl,investedPromptLbl,eViPC);
        
    }
 // Create the graph based on total savings per month
    private LineChart<Number, Number> createSavingsGraph() {
        // X and Y axes for the graph
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Months");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Saved ($)");

        // Create the LineChart with axes
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Total Saved Over Time");

        // Calculate and plot the data points for each month
        for (double month = 0; month <= savedMonths; month++) {
            double totalSaved = month * monthlySavings; // Total savings by the end of the month
            series.getData().add(new XYChart.Data<>(month, totalSaved)); // Add data to series
        }

        lineChart.getData().add(series); // Add the series to the chart
        return lineChart; // Return the chart to be displayed
    }
    
    public static void main(String[] args) {
        launch();
    }

}