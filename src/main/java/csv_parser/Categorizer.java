/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv_parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Eug
 * 
 * Responsible for reading food categories from "category.csv" and putting into a dictionary 
 * which can be used to lookup a food's category.
 * 
 */
public class Categorizer {
    HashMap<String, String> catTable; //dictionary to determine food's category

    public Categorizer() {
        this.catTable = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("categorizer.csv"));
            String line = br.readLine();
            
            while (line != null) {
                String[] catAndItem = line.split(",");
                catTable.put(catAndItem[0], catAndItem[1]);
                line = br.readLine();
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }     
    }
}
