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
			System.out.println("\nChecking Status of Task No: " + req.getTaskNumber());
			Thread.sleep(1000);
			
			res = strSer.compare(req.getStr1(), req.getStr2(), req.getAlgorithim());
			System.out.println(req.getStr1());
			outQueue.put(req.taskNumber, res);
		} catch (RemoteException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
