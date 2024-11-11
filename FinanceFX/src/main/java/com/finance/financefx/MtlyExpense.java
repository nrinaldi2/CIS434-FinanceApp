
package com.finance.financefx;

import java.lang.String;


public class MtlyExpense {
    private double totMtlyExpenses;
    private String type;
    private String name;
    private double cost;
    
    private int dayDue;
    
    
    
    
    public MtlyExpense(String type, String name, double cost, int dayDue){
        this.type = type;
        this.cost = cost;
        this.name = name;
        this.dayDue = dayDue;
}
    
    
    //Getters
    
    public String getType (){
        return type;
    }
    
    public String getName (){
        return name;
    }
    public double getCost (){
        return cost;
    }
    public int getDayDue (){
        return dayDue;
    }

    
}
