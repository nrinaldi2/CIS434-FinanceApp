
package com.finance.financefx;


public class MtlyExpense {
    private double totMtlyExpenses;
    private String type;
    private double Cost;
    private String name;
    private int DD;
    
    
    
    
    public void MtlyExpense(String expType, double expCost, String expName, int dayDue){
        this.type = expType;
        this.Cost = expCost;
        this.name = expName;
        this.DD = dayDue;
}
    

    
}
