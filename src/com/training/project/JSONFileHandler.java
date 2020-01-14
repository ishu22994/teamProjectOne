package com.training.project;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.*;

import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class JSONFileHandler implements MyFileHandler{

    public  void  read() throws Exception {
        String filename="/Users/ishitshah/Downloads/employee.json";
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        Object jsonData = jsonParser.parse(reader);
        JSONArray jsonArray = (JSONArray) jsonData;
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String firstName = (String)jsonObject.get("firstName");
            String lastName = (String)jsonObject.get("firstName");
            Date date =  parseDate((String) jsonObject.get("dateOfBirth"), "MM/dd/yy");
            long experiance = (long) jsonObject.get("experience");
            Employee employee = new Employee(firstName,lastName,date,experiance);

            MyCollection.add(employee);
        }System.out.println(MyCollection.writeCounter);
    }
        private static  Date parseDate(String date, String format) throws ParseException,
                java.text.ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(date);
        }

    public void write() throws Exception {



        /*String filePath = "/Users/ishitshah/Desktop/new.json";
        PrintWriter pw = new PrintWriter(filePath);
        JSONArray ja = new JSONArray();*/
        MongoClient mongo = new com.mongodb.MongoClient("localhost", 27017);
        MongoCredential credential;
        credential = MongoCredential.createCredential("team8", "team8db", "team8".toCharArray());
        System.out.println("Connected successfully");
        MongoDatabase database = mongo.getDatabase("team8db");
        //System.out.println(credential);
        MongoCollection<Document> collection = database.getCollection("employee");
        for(int i=0;i<100;i++) {
            /*Employee employee = MyCollection.get();
            JSONObject obj = new JSONObject();
            obj.put("firstName", employee.getFirstName());
            obj.put("lastName", employee.getLastName());


            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
            String strDate = dateFormat.format(employee.getDateOfBirth());
            obj.put("dateOfBirth", strDate);

            obj.put("experience", employee.getExperience());
            ja.add(obj);
        }
        pw.write(ja.toJSONString());
        pw.flush();
        pw.close();*/



            Employee emp = MyCollection.get();


            org.bson.Document document = new org.bson.Document("title", "MongoDb")
                    .append("firstName", emp.getFirstName())
                    .append("lastName", emp.getLastName())
                    .append("dateOfBirth", emp.getDateOfBirth())
                    .append("experience", emp.getExperience());
            collection.insertOne(document);
           // System.out.println("Print all the documents.");

            MongoCursor cursor = collection.find().iterator();
            try {
                while (cursor.hasNext()) {
                    System.out.println(cursor.next());
                }

            } finally {
                cursor.close();
            }
        }
        System.out.println(MyCollection.readCounter);

    }

    }
