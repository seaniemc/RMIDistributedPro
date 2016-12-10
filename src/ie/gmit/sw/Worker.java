package ie.gmit.sw;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable{
	
	private BlockingQueue<Request> inQueue;
	private Map<String, Resultator> outQueue;
	private Resultator res;
	private StringService strSer;
	
	
	public Worker(BlockingQueue<Request> inQueue, Map<String, Resultator> outQueue, 
			StringService strSer){
		this.inQueue = inQueue;
		this.outQueue = outQueue;
		//this.res = res;
		this.strSer = strSer;
		
	}

	@Override
	public void run() {
		Request req = inQueue.poll();
		
		try {
			
			Thread.sleep(10000);
			
			res = strSer.compare(req.str1, req.str2, req.algorithim);
			
			outQueue.put(req.taskNumber, res);
		} catch (RemoteException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
