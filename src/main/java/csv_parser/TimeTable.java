/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv_parser;

import java.util.*;
import javax.lang.model.SourceVersion;
import java.time.*;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Eug
 * 
 * Represents a time block
 */
public class TimeTable {
    LocalTime time;
    
    Totals totals; //item totals at the time block level
    
    List<Person> people; //people who ordered in this time block
    
    public TimeTable (LocalTime time) {
        this.time = time;
        this.totals = new Totals();
        this.people = new ArrayList<>();
    }

    //prints current time + individual people's order for this time block
    public void print(DateTimeFormatter formatter) {
        System.out.println(time.format(formatter));
        
        for(Person p: people) {
            System.out.println("Name: " + p.name);
            p.totals.print();
        }
    }
}
