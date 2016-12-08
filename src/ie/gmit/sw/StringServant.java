package ie.gmit.sw;

//import ie.gmit.sw;

import java.rmi.*;
import java.rmi.registry.*;

public class StringServant {

    public static void main(String[] args) throws Exception {

        StringService mss = new StringServiceImpl();

        //Start the RMI regstry on port 1099
        LocateRegistry.createRegistry(1099);

        //Bind our remote object to the registry with the human-readable name "howdayService"
        Naming.rebind("My String Compare Service", mss);

        //Print a nice message to standard output
        System.out.println("My String Compare Service is listening on port 1099.");
    }
}