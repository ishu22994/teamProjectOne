package com.training.project;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class XMLFileHandler implements MyFileHandler {


    public  void  read() {

            try {
                File file = new File("/Users/ishitshah/Downloads/employee.xml");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = null;

                db = dbf.newDocumentBuilder();
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("employee");
                for (int iterator = 0; iterator < nodeList.getLength(); iterator++) {

                    Node node = nodeList.item(iterator);
                    Element element = (Element) node;


                    String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                    Date date = new SimpleDateFormat("MM/dd/yy").parse(element.getElementsByTagName("dateOfBirth").item(0).getTextContent());
                    Date dateOfBirth = date;
                    double exp = new Double(element.getElementsByTagName("experience").item(0).getTextContent());
                    Employee employee = new Employee(firstName, lastName, dateOfBirth, exp);

                    MyCollection.add(employee);
                }
                System.out.println(MyCollection.writeCounter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public  void write() {
        try {
        /*DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element employees = document.createElement("employees");
        document.appendChild(employees);*/
            //for (int i = 0; i < 100; i++) {
            /*Employee e = MyCollection.get();


                Element employee = document.createElement("employee");
                employees.appendChild(employee);

                Element firstName = document.createElement("firstName");
                firstName.appendChild(document.createTextNode(e.getFirstName()));
                employee.appendChild(firstName);

                Element lastName = document.createElement("lastName");
                lastName.appendChild(document.createTextNode(e.getLastName()));
                employee.appendChild(lastName);


                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                String strDate = dateFormat.format(e.getDateOfBirth());
                Element dateOfBirth = document.createElement("dateOfBirth");
                dateOfBirth.appendChild(document.createTextNode(strDate));
                employee.appendChild(dateOfBirth);


                String exp = String.valueOf(e.getExperience());
                Element experience = document.createElement("experience");
                experience.appendChild(document.createTextNode(exp));
                employee.appendChild(experience);

                TransformerFactory transformarFactory = TransformerFactory.newInstance();
                Transformer transformer = transformarFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File("/Users/ishitshah/Desktop/new.xml"));

                transformer.transform(domSource, streamResult);*/
                MongoClient mongo = new com.mongodb.MongoClient("localhost", 27017);
                MongoCredential credential;
                credential = MongoCredential.createCredential("team8", "team8db", "team8".toCharArray());
                System.out.println("Connected successfully");
                MongoDatabase database = mongo.getDatabase("team8db");
                //System.out.println(credential);
                MongoCollection<org.bson.Document> collection = database.getCollection("employee");
                for (int i = 0; i < 100; i++) {

                    Employee emp = MyCollection.get();
                    org.bson.Document document = new org.bson.Document("title", "MongoDb")
                            .append("firstName", emp.getFirstName())
                            .append("lastName", emp.getLastName())
                            .append("dateOfBirth", emp.getDateOfBirth())
                            .append("experience", emp.getExperience());
                    collection.insertOne(document);
                    FindIterable<org.bson.Document> iterDoc = collection.find();
                    Iterator it = iterDoc.iterator();
                    while (it.hasNext()) {
                        System.out.println(it.next());
                    }


                }
                System.out.println(MyCollection.readCounter);

        }catch (Exception exp) {
            System.out.println(exp);
        }
    }
    }







