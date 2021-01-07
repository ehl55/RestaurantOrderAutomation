/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv_parser;

import java.util.HashMap;

/**
 *
 * @author Eug
 * 
 */
public class Person {
    
    String name;
    
    Totals totals; //food item totals at person level

    public Person(String n) {
        this.name = n;
        
        this.totals = new Totals();
    }
}
