package com.finance.financefx;

import java.util.HashSet;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;



/**
 * JavaFX Finance App by Nick Rinaldi & Gavin Wells
 */

public class App extends Application {

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
        
        
        
        //          STAGE TITLE & CALL
        stage.setTitle("Finance FX");
        stage.setScene(hScene);
        stage.show();
        
        
//          ACCOUNT OVERVIEW SCENE

        // Account Overview Scene Title label
        Label titleLbl = new Label("Account Overview");
        titleLbl.getStyleClass().add("titleLbl");
        titleLbl.setFont(Font.font("cursive"));
        
        UserBalance ub1 = new UserBalance();
        
        // Account Overview Label Declarations
        Label totUserAccBalLbl = new Label("Your total account balance is: $" + ub1.GetTotal());
        Label updateBalLbl = new Label("Update Account Balance (All checking and cash): ");
        Label userIncLbl = new Label ("Monthly Net Income: $" + ub1.GetInc());
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
                }
                else{
                    updateBalPromptLbl.setText("Please type a positive number, with only 2 decimal places, above.");
                    updateBalTF.clear();
                }
                
            }
            catch (Exception e){
                updateBalPromptLbl.setText("Please type a positive number, with only 2 decimal places, above.");
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
                }
                else{
                    updateIncPromptLbl.setText("Please type a positive number, with only 2 decimal places, above.");
                    updateIncTF.clear();
                }
                
            }
            catch (Exception e){
                updateIncPromptLbl.setText("Please type a positive number, with only 2 decimal places, above.");
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
                }
                else{
                    updateIncPromptLbl.setText("Please type a positive number, with only 2 decimal places, above.");
                    addEarnTF.clear();
                }
                
            }
            catch (Exception e){
                addEarnPromptLbl.setText("Please type a positive number, with only 2 decimal places, above.");
                addEarnTF.clear();
            }
        }); // Add earnings button
        
        
        Button undoEarnBtn = new Button("Undo");
        undoEarnBtn.setOnAction(ActionEvent ->{
            if(ub1.GetPrevEarn() != 0){
            ub1.UndoEarn();
            addEarnPromptLbl.setText("Previous earnings have been removed from your balance.");
            totUserAccBalLbl.setText("Your total account balance is: $" + String.format("%,.2f", ub1.GetTotal()));
            }
            else{
            addEarnPromptLbl.setText("Can only undo the last earning. You must reset total balance or add "
                    +"unwanted earnings to losses on expense page if you wish to remove more.");
            }
        });
        
        
        Button expSceneBtn = new Button ("Expenses");
        expSceneBtn.setOnAction(ActionEvent ->{
        stage.setScene(expensesScene);
        }); //Go to expenses page
        
        Button calcSceneBtn = new Button ("Calculations");
        calcSceneBtn.setOnAction(ActionEvent ->{
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
        
        AnchorPane.setTopAnchor(addEarnPromptLbl, 375.0);//Add Earnings prompt Lbl
        AnchorPane.setLeftAnchor(addEarnPromptLbl, 40.0);
        
        
        AnchorPane.setBottomAnchor(expSceneBtn, 20.0);//Expenses Scene Btn
        AnchorPane.setLeftAnchor(expSceneBtn, 40.0);
        
        AnchorPane.setBottomAnchor(calcSceneBtn, 20.0);//Calculations Scene Btn
        AnchorPane.setRightAnchor(calcSceneBtn, 40.0);
        
        //Show all placements on Account Overview Scene
        hPane.getChildren().addAll(titleLbl, totUserAccBalLbl,updateBalLbl,
        updateBalTF, updateBalBtn,updateBalPromptLbl,userIncLbl,
        updateIncLbl,updateIncTF,updateIncBtn,updateIncPromptLbl,addEarnLbl,addEarnTF,addEarnBtn,undoEarnBtn,addEarnPromptLbl,
        expSceneBtn,calcSceneBtn);
        
        
        
        
//          EXPENSES SCENE
        
        // Expenses Label Declarations
        Label expensesLbl = new Label("Expenses");
        Label addMtlyExpLbl = new Label("Add Monthly Expense");
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
        
        // Expenses Text Field Declarations
        TextField addMtlyTypeTF = new TextField();
        TextField addMtlyNameTF = new TextField();
        TextField addMtlyCostTF = new TextField();
        TextField addMtlyDDTF = new TextField();
        TextField addUnexLossTF = new TextField ();
        
        // Expenses Button Declarations
        
        
        Button addUnexBtn = new Button("Add");
        
        Button backBtn = new Button ("Back");
        backBtn.setOnAction(ActionEvent ->{
        //Clear textfields
        stage.setScene(hScene);
        });
        
        
        
        // Table View Declaration and Definition for Monthly Expenses
        
        TableView mtlyExpTV = new TableView();
        
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
        
        // Monthly Expenses Buttons
        Button addMtlyExpBtn = new Button ("Add Monthly Expense");
        addMtlyExpBtn.setOnAction(ActionEvent ->{
            try{
                String type = addMtlyTypeTF.getText();
                String name = addMtlyNameTF.getText();
                double cost = Double.parseDouble(addMtlyCostTF.getText());
                int dayDue = Integer.parseInt(addMtlyDDTF.getText());
                
                if(cost > 0 && dayDue > 0 && dayDue < 32 ){
                    mtlyExpTV.getItems().add(new MtlyExpense(type,name,cost,dayDue));
                    addMtlyExpPromptLbl.setText("Expense added.");
                    
                    addMtlyTypeTF.clear();
                    addMtlyNameTF.clear();
                    addMtlyCostTF.clear();
                    addMtlyDDTF.clear();
                    
                }
                else{
                    addMtlyExpPromptLbl.setText("Please type a positive cost and a day between 0 and 32");
                }
    
            }
            catch(Exception e){
                addMtlyExpPromptLbl.setText("Something was not typed correctly...");
            }
        });
        
        Button removeMtlyExpBtn = new Button ("Remove");
        
        
        
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
        
        
        AnchorPane.setTopAnchor(mtlyExpTV, 60.0); //Add Monthly Expense Table View
        AnchorPane.setRightAnchor(mtlyExpTV, 15.0);
        
        AnchorPane.setTopAnchor(mtlyExpLbl, 40.0); //Add Monthly Expense Table Title Lbl
        AnchorPane.setRightAnchor(mtlyExpLbl, 250.0);
        
        AnchorPane.setTopAnchor(addMtlyExpPromptLbl, 15.0); //Add Monthly Expense Table Prompt Lbl
        AnchorPane.setRightAnchor(addMtlyExpPromptLbl, 250.0);
        
        AnchorPane.setBottomAnchor(removeMtlyExpBtn, 70.0); //Add Monthly Expense Remove Btn
        AnchorPane.setRightAnchor(removeMtlyExpBtn, 265.0);
        
        AnchorPane.setBottomAnchor(totMtlyExpLbl, 75.0); //Add Monthly Expense Table Total Lbl
        AnchorPane.setRightAnchor(totMtlyExpLbl, 85.0);
        
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
        Label netMtlyIncLbl = new Label("Net monthly income after expenses: $");
        Label netAfSavLbl = new Label("Calculate net monthly income after desired savings");
        Label pSavedLbl = new Label("Percent of earnings saved (monthly): ");
        Label pLbl = new Label ("%"); // Meant to be put next to respective text field
        Label pSaved2Lbl = new Label("Percent of earnings saved: " + "%"); // NOTE: This is fully set once first pSaved is entered
        Label calcFBalLbl = new Label("Calculate future balance");
        Label calcFBMonthsLbl = new Label("Months: ");
        
        // Calculation Info Prompt Labels
        Label netASPromptLbl = new Label(" ");
        Label calcFBPromptLbl = new Label(" ");
        
        // Calculation Text Fields
        TextField pSavedTF = new TextField("");
        TextField calcFBMonthsTF = new TextField("");
        
        // Calculation Button Declarations and Actions
        Button netAfSavBtn = new Button("Calculate");
        Button calcFbBtn = new Button("Calculate");
        Button backBtn2 = new Button("Back");
        backBtn2.setOnAction(ActionEvent ->{
        //Clear textfields
        stage.setScene(hScene);
        });
        
        
        
        // Calculations Scene Placements
        AnchorPane.setTopAnchor(calcLbl, 20.0); //Title
        AnchorPane.setLeftAnchor(calcLbl, 240.0);
        
        
        AnchorPane.setTopAnchor(netMtlyIncLbl, 80.0); // Net Monthly Income Lbl
        AnchorPane.setLeftAnchor(netMtlyIncLbl, 40.0);
        
        
        AnchorPane.setTopAnchor(netAfSavLbl, 130.0); // Net Monthly Income After Savings Lbl
        AnchorPane.setLeftAnchor(netAfSavLbl, 40.0);
        
        AnchorPane.setTopAnchor(pSavedLbl, 160.0); // Percent Saved Lbl
        AnchorPane.setLeftAnchor(pSavedLbl, 40.0);
        
        AnchorPane.setTopAnchor(pSavedTF, 180.0); // Percent Saved TF
        AnchorPane.setLeftAnchor(pSavedTF, 40.0);
        
        AnchorPane.setTopAnchor(pLbl, 185.0); // Percent Lbl
        AnchorPane.setLeftAnchor(pLbl, 190.0);
        
        AnchorPane.setTopAnchor(netAfSavBtn, 180.0); // Net Monthly Income Calculation Btn
        AnchorPane.setLeftAnchor(netAfSavBtn, 220.0);
        
        AnchorPane.setTopAnchor(netASPromptLbl, 200.0); // Net Monthly Income After Savings Prompt Lbl
        AnchorPane.setLeftAnchor(netASPromptLbl, 40.0);
        
        
        AnchorPane.setTopAnchor(calcFBalLbl, 240.0); // Calculate Future Balance Lbl
        AnchorPane.setLeftAnchor(calcFBalLbl, 40.0);
        
        AnchorPane.setTopAnchor(pSaved2Lbl, 270.0); // Percent of Monthly Income Saved (monthly)(Relies on past input)
        AnchorPane.setLeftAnchor(pSaved2Lbl, 40.0);
        
        AnchorPane.setTopAnchor(calcFBMonthsLbl, 290.0); // Calculate Future Balance Months Lbl
        AnchorPane.setLeftAnchor(calcFBMonthsLbl, 40.0);
        
        AnchorPane.setTopAnchor(calcFBMonthsTF, 310.0); // Calculate Future Balance Months TF
        AnchorPane.setLeftAnchor(calcFBMonthsTF, 40.0);
        
        AnchorPane.setTopAnchor(calcFbBtn,350.0); // Calculate Future Balance Months Btn
        AnchorPane.setLeftAnchor(calcFbBtn, 40.0);
        
        AnchorPane.setTopAnchor(calcFBPromptLbl, 370.0); // Calculate Future Balance Prompt Lbl
        AnchorPane.setLeftAnchor(calcFBPromptLbl, 40.0);
        
        
        AnchorPane.setBottomAnchor(backBtn2, 20.0); // Back Btn
        AnchorPane.setLeftAnchor(backBtn2, 40.0);
        
        
        
        calculationsPane.getChildren().addAll(calcLbl,netMtlyIncLbl,
                netAfSavLbl,pSavedLbl,pLbl,pSaved2Lbl,
                calcFBalLbl,calcFBMonthsLbl,netASPromptLbl,
                calcFBPromptLbl,pSavedTF,calcFBMonthsTF,
                netAfSavBtn,calcFbBtn, backBtn2);
        
    }

    public static void main(String[] args) {
        launch();
    }

}