# FirstOrion

FirstOrion

Install Direction

Checkout - 
 git clone https://github.com/vignesh1rajan/FirstOrion.git
 
  IDE:
     In order to run the server on IDE import the project as a maven project. Then run the main method from Main.java . Once the server is started (the port printed in console) it is ready for testing. 

Automated API test exists via test\java\vertx\ApiTest directory. Tests can be from the IDE by configuring the Run Configuration to run TestNG ApiTest class and methods acordingly.

Running via command line -
  Run the following command to build the jar file - 
  
     mvn package
     
     java -jar target/FirstOrion-3.3.3-fat.jar
     
  Running the test - on a different command line run 
  
  mvn install -DskipTests=false
  
   
The API can also be access via POSTMAN 

   GET http://localhost:8080/persons/                 - Get a list of persons
   GET http://localhost:8080/persons/{personID}    -  get a person by id
   PUT http://localhost:8080/persons/{personID}    - Add new person

     Body-
    {
    "name": "New Name",
    "address": "Volcano",
    "status": "unprocessed"
   }
   
