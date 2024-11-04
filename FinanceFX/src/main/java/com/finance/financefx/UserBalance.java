
package com.finance.financefx;


public class UserBalance {
   private double currentTotal;
   private double mtlyIncome;
   private double previousEarn;
   
    
   // UserBalance getters
   
   public double GetTotal(){
       return this.currentTotal;
   } //Get total balance
   
   public double GetInc(){
       return this.mtlyIncome;
   } //Get net monthly income
   
   public double GetPrevEarn (){
       return this.previousEarn;
   }
   
   //UserBalance setters
   
   public void SetTotal(double userBal){
       this.currentTotal = userBal; 
   } //Set total balance
   
   public void AddEarn(double unexEarn){
       this.previousEarn = unexEarn;
       this.currentTotal += unexEarn;
   } //Add unexpected earnings to user total balance
   
   public void UndoEarn(){
       this.currentTotal -= this.previousEarn;
       this.previousEarn = 0;
   } //Undo the last add earnings operation
   
   public void SetIncome(double userInc){
       this.mtlyIncome = userInc;
   } //Set monthly income (after income tax)
   
   
   
   
}
