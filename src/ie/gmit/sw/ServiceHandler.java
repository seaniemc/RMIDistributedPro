package ie.gmit.sw;

import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServiceHandler extends HttpServlet {
	private String remoteHost = null;
	private static long jobNumber = 0;
	private final int POOL_SIZE = 6;
	
	private static Map<String, Resultator> outQueue; 
	private static BlockingQueue<Request> inQueue;
	private static ExecutorService executor ;
	
	private boolean checkProcessed;
	private String returningDistance = "";
	
	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER"); //Reads the value from the <context-param> in web.xml
		
		outQueue = new HashMap<String, Resultator>();
		inQueue = new LinkedBlockingQueue<Request>();
		executor = Executors.newFixedThreadPool(POOL_SIZE);
		
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringService service = null;
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		
		try {
			service = (StringService) Naming.lookup("rmi://localhost:1099/MyStringCompareService");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Initialise some request varuables with the submitted form info. These are local to this method and thread safe...
		String algorithm = req.getParameter("cmbAlgorithm");
		String str1 = req.getParameter("txtS");
		String str2 = req.getParameter("txtT");
		String taskNumber = req.getParameter("frmTaskNumber");
		
		

		out.print("<html><head><title>Distributed Systems Assignment</title>");		
		out.print("</head>");		
		out.print("<body>");
		
		if (taskNumber == null){
			taskNumber = new String("T" + jobNumber);
			
			checkProcessed = false;

			Request r = new Request(algorithm,str1,str2, taskNumber );
			inQueue.add(r);
			
			
			Runnable work = new Worker(inQueue, outQueue, service);
			executor.execute(work);
			
			jobNumber++;
		} else {
			
			if (outQueue.containsKey(taskNumber)) {
				//get the Resultator object from outMap based on tasknumber
				Resultator outQItem = outQueue.get(taskNumber);

				System.out.println("\nChecking Status of Task No:" + taskNumber);

				checkProcessed = outQItem.isProcessed();

				// Check to see if the Resultator Item is Processed
				if (checkProcessed == true) {
					// Remove the processed item from Map by taskNumber
					outQueue.remove(taskNumber);
					//Get the Distance of the Current Task
					returningDistance = outQItem.getResult();

					System.out.println("\nTask " + taskNumber + " Processed");
					System.out.println("String (" + str1 + ") and String (" + str2 + ") Distance = " + returningDistance);
				}
			}
		}
		
		out.print("<html><head><title>Distributed Systems Assignment</title>");		
		out.print("</head>");		
		out.print("<body>");
		
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<div id=\"r\"></div>");
		
		out.print("<font color=\"#993333\"><b>");
		out.print("RMI Server is located at " + remoteHost);
		out.print("<br>Algorithm: " + algorithm);		
		out.print("<br>String <i>s</i> : " + str1);
		out.print("<br>String <i>t</i> : " + str2);
		
		if(returningDistance != null){
			out.print("<br>Distance:  " + returningDistance);
		}
		
		//put an if statement here to stop page refresh
		out.print("<form name=\"frmRequestDetails\">");
		out.print("<input name=\"cmbAlgorithm\" type=\"hidden\" value=\"" + algorithm + "\">");
		out.print("<input name=\"txtS\" type=\"hidden\" value=\"" + str1 + "\">");
		out.print("<input name=\"txtT\" type=\"hidden\" value=\"" + str2 + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");								
		out.print("</body>");	
		out.print("</html>");	
		//this refreshes the page
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
		out.print("</script>");
				
		//You can use this method to implement the functionality of an RMI client
		
		//
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}