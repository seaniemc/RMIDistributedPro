# RMI Distributed Systems ProJect

Name: Sean McGrath.  
Id: G00316649.  
Module: Distributed Systems.  

##Introduction 
The application uses the Java RMI framework to compare 2 strings and return the distance between them. The application also incorporates the use of Threads, Blocking Queues and Hash maps. When the Application is started in Apache Tom Cat, the user is greeted with a home screen which allows them to choose different string comparison algorithm to use. Users can enter in the 2 strings to be compared. The application then asynchronously sends the strings to the algorithms using a combination of RMI framework, Threads, Blocking Queues and Hash maps.

##Execution of Application
- To run the following program, remove the string-service.jar and comparator.war from the starter folder.  
- To run the RMI server, by entering the following command via terminal:  java –cp ./string-service.jar ie.gmit.sw.StringServant
- Download Apache Tomcat(perferable version 8.0.35 used in development)
- Drag the comparator.war File into the webapps folder of Apache-Tomcat.
- Navigate into the apache-tomcat/bin and run the startUp.bat file.
- Open web browser enter localhost:8080/comparator
- Enter your 2 strings and choose which Algorithm you want.
