/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv_parser;

import java.util.*;

/**
 *
 * @author Eug
 * 
 * Maps Category -> Items -> Quantity
 */
public class Totals {
    
    HashMap<String, HashMap<String, Integer>> totalsTable;
    
    public Totals () {
        this.totalsTable = new HashMap<>();
    }
    
    //automatically finds proper category and updates food quantity
    //if category for a food doesn't exist, item is added to missing category list (which will be displayed to user)
    public void order(String foodName, int quantity, Category category, Set<String> missingCategory) {
       HashMap<String, String> catTable = category.catTable;
        if(catTable.containsKey(foodName)) {
            String cat = catTable.get(foodName);
            
            if(!totalsTable.containsKey(cat)) {
                totalsTable.put(cat, new HashMap<>());
            } 
            
            HashMap<String, Integer> foodQuantityMap = totalsTable.get(cat);
            foodQuantityMap.put(foodName, foodQuantityMap.getOrDefault(foodName, 0) + 1);
        } else {
            missingCategory.add(foodName);
        }
    }
    
    //add totals1 into calling totals table
    public void merge (Totals totals1, Category category, Set<String> missingCategory) {
        
        HashMap<String, String> catTable = category.catTable;
 
        HashMap<String, HashMap<String, Integer>> t1 = totals1.totalsTable;

        for (String cat : t1.keySet()) {
            HashMap<String, Integer> t1CatTable = t1.get(cat);
            
            for(String foodName : t1CatTable.keySet()) {
                int quantity = t1CatTable.get(foodName);
                
                this.order(foodName, quantity, category, missingCategory);
            }
        }
    }
    
    //just prints out category + food + quantity
    public void print() {
        for(String cat: totalsTable.keySet()) {
            System.out.println("    Category: " + cat);
            HashMap<String, Integer> foodQuantityMap = totalsTable.get(cat);
            for(String food : foodQuantityMap.keySet()) {
                System.out.println("        " + foodQuantityMap.get(food) + " x " + food);
            }
        }
    }
    
    //"converts" old food into new food
    public void updateOldOrderWithSide(String prevFood, String prevFoodWithSide, Category category) {
        String cat = category.catTable.get(prevFood);
        HashMap<String, Integer> foodQuantityMap = totalsTable.get(cat);
        
        //get old food's quantity
        foodQuantityMap.put(prevFoodWithSide, foodQuantityMap.get(prevFood));
        category.catTable.put(prevFoodWithSide, cat); //necessary, otherwise will fail for multiple sides.
        
        //decrease quantity of old order
        foodQuantityMap.put(prevFood, foodQuantityMap.get(prevFood)-1);
        if(foodQuantityMap.get(prevFood) == 0) foodQuantityMap.remove(prevFood);
        
        
    }
}
