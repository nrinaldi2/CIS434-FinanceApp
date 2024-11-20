
package com.finance.financefx;


public class UserBalance{
    
   private double currentTotal = 0;
   private double mtlyIncome = 0;
   private double previousEarn;
   
    
   // UserBalance getters
   
   public double GetTotal(){
       return this.currentTotal;
   } //Get total balance
   
   public double GetInc(){
       return this.mtlyIncome;
   } //Get net monthly income
   
   public double GetIncAfExp(double totMtlyExp){
       double incBfExp = this.mtlyIncome;
       
       return incBfExp - totMtlyExp;
   }
   
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
   
   public void AddExp (double unexExp){
       this.currentTotal -= unexExp;
   } //Subtract unexpected expense from total balance
   
   public void SetIncome(double userInc) {
	    this.mtlyIncome = userInc;
	    System.out.println("Income set to: " + mtlyIncome); // Debugging print statement
	} //Set monthly income (after income tax)
   
   
   
}
