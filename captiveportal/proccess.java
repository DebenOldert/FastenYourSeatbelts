/* 	This is the proccessing page of the captive portal.
*	Copy right: Deben Oldert
*	Here classes are called to check and update database query's and to configure iptables.
*	Called classe are: {JSON, Command}
*/
package captiveportal;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class proccess
 */
@WebServlet("/proccess")
public class proccess extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set the MIME type for the page
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
		// Send user back to login form
	      try {
	    	  out.println("<script>window.location.replace('http://portal.corendon.nl/Portal');</script>");
	      	} finally {
	      		out.close();  // Close the output writer
	      	}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Set the MIME type for the page
	      response.setContentType("text/html");
	      // Define variables
	      //#####################################
	      String ticketNumber = request.getParameter("ticket");
	      int exitCode = 2;
	      String javaReturn0 = "<script>setTimeout(function(){window.location.replace('http://corendon.nl')},2700);</script>";
	      String javaReturnErrorS = "<script>window.location.replace('http://portal.corendon.nl/Portal/?err=";
	      String javaReturnErrorE = "');</script>";
	      //#####################################
	      //Start the output writer
	      PrintWriter out = response.getWriter();
	      //Load classes
	      Command CMD = new Command();
	      json JSON = new json();

	      try {
	    	// Fallback to version 04-01-2015 (Only if you want to be a badboy)
			//if(JSON.checkTicket(ticketNumber)) {
	    	  	//Call to the grandAccess function in json.java
				if(JSON.grandAccess(ticketNumber))
					{
					//Now lets give the user actually access to the interwebzz
					if(CMD.Grand(request.getRemoteAddr())) {
					  exitCode = 0;
					}
					//If you did not pass, say so
					else {
						exitCode = 3;
					}
				}
				//If you did not pass, say so
				else
					{
					exitCode = 2;
					}
			//Part of the fallback
			/*}
			else {
				exitCode = 1;
			}*/
			// what ya' gonna do, what ya' gonna do when they come for you....
		} catch (Exception e) {
			e.printStackTrace();
			exitCode = 4;
		}
	    //What to do with a exitCode?
	    switch (exitCode) {
	    //Whet could we do, what could we do...
	    case 0:
	    	//Eureka!! Making the path to the interwebzz
	    	request.getRequestDispatcher("/loading.html").include(request, response);
	    	out.println(javaReturn0);
	    	break;
	    //For the following cases: Get back where you came from (index.html) but with a return code
	    case 1:
	    	out.println(javaReturnErrorS+exitCode+javaReturnErrorE);
	    	break;
	    case 2:
	    	out.println(javaReturnErrorS+exitCode+javaReturnErrorE);
	    	break;
	    case 3:
	    	out.println(javaReturnErrorS+exitCode+javaReturnErrorE);
	    	break;
	    case 4:
	    	out.println(javaReturnErrorS+exitCode+javaReturnErrorE);
	    	break;
	    case 5:
	    	out.println(javaReturnErrorS+exitCode+javaReturnErrorE);
	    	break;
	    }
	}

}
