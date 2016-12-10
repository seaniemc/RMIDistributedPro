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
	private final int THREAD_POOL_SIZE = 6;
	
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
		executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		
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
			
			//Resultator rs = new ResultatorIMPL();
			
			Runnable work = new Worker(inQueue, outQueue, service);
			executor.execute(work);
			
			jobNumber++;
		} else {
			// ELSE - Check outQueue for finished job

			// Get the Value associated with the current job number
			if (outQueue.containsKey(taskNumber)) {
				// Get the Resultator item from the MAP by Current taskNumber
				Resultator outQItem = outQueue.get(taskNumber);

				System.out.println("\nChecking Status of Task No:" + taskNumber);

				checkProcessed = outQItem.isProcessed();

				// Check to see if the Resultator Item is Processed
				if (checkProcessed == true) {
					// Remove the processed item from Map by taskNumber
					outQueue.remove(taskNumber);
					//Get the Distance of the Current Task
					returningDistance = outQItem.getResult();

					System.out.println("\nTask " + taskNumber + " Successfully Processed and Removed from OutQueue");
					System.out.println("Distance Between String (" + str1 + ") and String (" + str2 + ") = " + returningDistance);
				}
			}
		}
		
		
		
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<div id=\"r\"></div>");
		
		out.print("<font color=\"#993333\"><b>");
		out.print("RMI Server is located at " + remoteHost);
		out.print("<br>Algorithm: " + algorithm);		
		out.print("<br>String <i>s</i> : " + str1);
		out.print("<br>String <i>t</i> : " + str2);
		out.print("<br>This servlet should only be responsible for handling client request and returning responses. Everything else should be handled by different objects.");
		out.print("Note that any variables declared inside this doGet() method are thread safe. Anything defined at a class level is shared between HTTP requests.");				
		out.print("</b></font>");

		out.print("<P> Next Steps:");	
		out.print("<OL>");
		out.print("<LI>Generate a big random number to use a a job number, or just increment a static long variable declared at a class level, e.g. jobNumber.");	
		out.print("<LI>Create some type of an object from the request variables and jobNumber.");	
		out.print("<LI>Add the message request object to a LinkedList or BlockingQueue (the IN-queue)");			
		out.print("<LI>Return the jobNumber to the client web browser with a wait interval using <meta http-equiv=\"refresh\" content=\"10\">. The content=\"10\" will wait for 10s.");	
		out.print("<LI>Have some process check the LinkedList or BlockingQueue for message requests.");	
		out.print("<LI>Poll a message request from the front of the queue and make an RMI call to the String Comparison Service.");			
		out.print("<LI>Get the <i>Resultator</i> (a stub that is returned IMMEDIATELY by the remote method) and add it to a Map (the OUT-queue) using the jobNumber as the key and the <i>Resultator</i> as a value.");	
		out.print("<LI>Return the result of the string comparison to the client next time a request for the jobNumber is received and the <i>Resultator</i> returns true for the method <i>isComplete().</i>");	
		out.print("</OL>");	
		
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