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
    	//StringCompare alg = null;
    	switch(algo)
    	{
	    	case "Levenshtein Distance":
				Levenshtein ll = new Levenshtein();	
				r.setResult(ll.distance(s, t));
				r.setProcessed();
			case "Hamming Distance":
				HammingDistance hd = new HammingDistance();
				r.setResult(hd.distance(s, t));
				r.setProcessed();
			case "Damerau-Levenshtein Distance":
				DamerauLevenshtein dl = new DamerauLevenshtein();
				r.setResult(dl.distance(s, t));
				r.setProcessed();
    	}
    	
        return r;
    }
}
