# RMI Distributed Systems ProJect

Name: Sean McGrath.  
Id: G00316649.  
Module: Distributed Systems.  

##Introduction 
The application uses the Java RMI framework to compare 2 strings and return the distance between them. The application also incorpoates the use of Threads, Blocking Queues and Hashmaps. When the Application is started in Appache Tom cat, the user is greeted with a home screen which alows them to choose which string comparisson algorithim to use. Users can enter in the 2 strings to be compared. The applcation then asynchronously sends the strings to the algorithims using a combination of RMI framework, Threads, Blocking Queues and Hashmaps.

##Execution of Application
- In order to run the following program remove the string-service.jar and comparator.war from the the starter folder.  
- To run the RMI server enter the following command via CMD  java –cp ./string-service.jar ie.gmit.sw.StringServant
- Download Apache Tomcat(version 8.0.35 used in development)
- Drag the "comparator.war" File into the apache-tomcat\webapps folder,
- Navigate into the apache-tomcat\bin and run the "startUp.bat" file.
- 
