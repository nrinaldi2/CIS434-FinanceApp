
package com.finance.financefx;


public class UserBalance {
   private double currentTotal;
   private double mtlyIncome;
   
    
   // UserBalance getters
   public double GetTotal(){
       return this.currentTotal;
   }
   
   public double GetInc(){
       return this.mtlyIncome;
   }
   
   //UserBalance setters
   public void setTotal(double userBal){
       this.currentTotal = userBal; 
   }
}
