package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            // INSERT YOUR CODE HERE
            
            //Creates new JSON Object
            JSONObject jsonObj = new JSONObject();                        
            
            //Creating 3 arrays: 1 for columns, 1 for rows, and 1 for data
            JSONArray colHeaders = new JSONArray();
            JSONArray rowHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            JSONArray holder;
            String [] records = iterator.next();
            
            //Iterates through input file
            //String[] records = iterator.next();
            
            //Initiate for loop (set to continue converting as long as "i" is not out of input)
            for(int i = 0; i < records.length; i++){
                colHeaders.add(records[i]);
            }
            while(iterator.hasNext()){
                holder = new JSONArray();
                records = iterator.next();
                rowHeaders.add(records[0]);
            //  String [] rows = iterator.next();
            //  JSONArray list = new JSONArray();
            //  rowHeaders.add(rows[0]);
                
                for(int i = 1; i < (records.length); i++){
                    int stringHolder = Integer.parseInt(records[i]);
                    holder.add(stringHolder);
            //  int stringHolder = Integer.parseInt(records[i]);
            //  holder.add(stringHolder);                    
                }
                data.add(holder);
            }
            jsonObj.put("rowHeaders", rowHeaders);
            jsonObj.put("colHeaders", colHeaders);
            jsonObj.put("data", data);
            
            //Converts results to JSON
            results = JSONValue.toJSONString(jsonObj);
                               
        }        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            
           //Creating a new JSON Parser to read through the input file
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(jsonString);
            
            //Creating 3 arrays: 1 for columns, 1 for rows, and 1 for data
            JSONArray colHeaders = (JSONArray) jsonObj.get("colHeaders");
            JSONArray rowHeaders = (JSONArray) jsonObj.get("rowHeaders");
            JSONArray data = (JSONArray) jsonObj.get("data");
            
            String[] heading = new String[colHeaders.size()];
            
            //Initiating for loops that runs until i, j, and k are out of input
            for(int i = 0; i < colHeaders.size(); i++){
                heading[i] = (String) colHeaders.get(i);
            }
            csvWriter.writeNext(heading);
            
            for(int j = 0; j < data.size(); j++){
                JSONArray jsonArray = (JSONArray) data.get(j);
                String[] rows = new String[jsonArray.size() + 1];
                rows[0] = (String) rowHeaders.get(j);
            for(int k = 0; k < jsonArray.size(); k++){
                rows[k + 1] = Long.toString((long)jsonArray.get(k));
            }
            //Writes next rows in CSV format
            csvWriter.writeNext(rows);
            }
            //Coverts results to a String
            results = writer.toString();
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}