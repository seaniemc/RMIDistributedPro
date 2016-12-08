package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class StringServiceImpl extends UnicastRemoteObject implements StringService{
	private static final long serialVersionUID = 1L;
	//private RemoteMessage message;
	
	public StringServiceImpl()throws RemoteException{

    }

    public Resultator compare(String s, String t, String algo) throws RemoteException {
    	//
    	Resultator r = new ResultatorIMPL();
    	
    	r.setResult("sean");
    	
        return r;
    }
}
