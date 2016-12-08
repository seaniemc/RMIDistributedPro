package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Sean on 07/12/2016.
 */
public class StringServiceImpl extends UnicastRemoteObject implements StringService{

    public StringServiceImpl()throws RemoteException{

    }

    public Resultator compare(String s, String t, String algo) throws RemoteException {
        return null;
    }
}
