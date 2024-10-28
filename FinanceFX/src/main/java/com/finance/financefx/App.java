package com.finance.financefx;

import java.util.HashSet;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
        Scene expensesScene = new Scene(expensesPane, 640, 480);
        //          CALCULATIONS Scene
        AnchorPane calculationsPane = new AnchorPane();
        Scene calculationsScene = new Scene(calculationsPane, 640, 480);
        
        
        
        //          STAGE TITLE & CALL
        stage.setTitle("Finance FX");
        stage.setScene(hScene);
        stage.show();
        
        //          Account Overview Scene Title label
        Label titleLbl = new Label("Account Overview");
        titleLbl.getStyleClass().add("titleLbl");
        titleLbl.setFont(Font.font("cursive"));
        
        UserBalance ub1 = new UserBalance();
        
        //          Account Overview Label Declarations
        Label totUserAccBalLbl = new Label("Your total account balance is: $" + ub1.GetTotal());
        Label updateBalLbl = new Label("Update Account Balance (All checking and cash): ");
        Label userIncLbl = new Label ("Monthly Net Income: $" + ub1.GetInc());
        Label updateIncLbl = new Label ("Update Monthly Income (After taxes): ");
        Label addEarnLbl = new Label ("Add unexpected earnings: ");
        
        //          Account Overview Prompt Label Declarations
        Label updateBalPromptLbl = new Label ();
        
        
        //          Account Overview Label Formatting
        
        
        //          Account Overview Text Field Declarations
        TextField updateBalTF = new TextField();
        TextField updateIncTF = new TextField();
        TextField addEarnTF = new TextField();
        
        
        
        
        //          Account Overview Button Declarations
        
        //          Account Overview Button Declarations & Actions
        Button updateBalBtn = new Button("Update");
        updateBalBtn.setOnAction(ActionEvent -> {
            try{
                double userBal = Double.parseDouble(updateBalTF.getText());
                
                ub1.setTotal(userBal);
                totUserAccBalLbl.setText("Your total account balance is: $" + ub1.GetTotal());
                updateBalTF.clear();
                
            }
            catch (Exception e){
                updateBalPromptLbl.setText("Please type a positive decimal, rounded to the nearest tenth, above.");
            }
            
        }); //Update balance button
        
        Button updateIncBtn = new Button ("Update");
        
        Button addEarnBtn = new Button ("Add");
        Button expSceneBtn = new Button ("Expenses");
        Button calcSceneBtn = new Button ("Calculations");
        
        //          Account Overview Placements
        AnchorPane.setTopAnchor(titleLbl, 20.0); //Title
        AnchorPane.setLeftAnchor(titleLbl, 260.0);
        
        AnchorPane.setTopAnchor(totUserAccBalLbl,100.0); //User balance Lbl
        AnchorPane.setLeftAnchor(totUserAccBalLbl,40.0);
        
        AnchorPane.setTopAnchor(updateBalLbl, 130.0); //Update Balance Lbl
        AnchorPane.setLeftAnchor(updateBalLbl, 40.0);
        
        AnchorPane.setTopAnchor(updateBalTF, 160.0); //Update Balance TF
        AnchorPane.setLeftAnchor(updateBalTF, 40.0);
        
        AnchorPane.setTopAnchor(updateBalBtn, 160.0); //Update Balance Btn
        AnchorPane.setLeftAnchor(updateBalBtn, 200.0);
        
        AnchorPane.setTopAnchor(updateBalPromptLbl, 170.0);//Update Balance prompt Lbl
        AnchorPane.setLeftAnchor(updateBalPromptLbl, 40.0);
        
        AnchorPane.setTopAnchor(userIncLbl,210.0); //User net monthly income Lbl
        AnchorPane.setLeftAnchor(userIncLbl,40.0);
        
        AnchorPane.setTopAnchor(updateIncLbl,240.0); //Update net monthly income Lbl
        AnchorPane.setLeftAnchor(updateIncLbl,40.0);
        
        AnchorPane.setTopAnchor(updateIncTF,270.0); //Update net monthly income TF
        AnchorPane.setLeftAnchor(updateIncTF,40.0);
        
        AnchorPane.setTopAnchor(updateIncBtn,270.0); //User net monthly income Btn
        AnchorPane.setLeftAnchor(updateIncBtn,200.0);
        
        //Show all placements on Account Overview Scene
        hPane.getChildren().addAll(titleLbl, totUserAccBalLbl,updateBalLbl,
        updateBalTF, updateBalBtn, updateBalPromptLbl,userIncLbl,updateIncLbl,updateIncTF,updateIncBtn);
    }

    public static void main(String[] args) {
        launch();
    }

}