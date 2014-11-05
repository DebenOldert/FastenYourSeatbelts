/* 	This is the main page of the captive portal.
*	Copy right: Deben Oldert
*	Here users are asked about there credentials to login and allow internet access.
*	It inserts the html page => index.html
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
 * Servlet implementation class Index
 */
@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        		response.setContentType("text/html;charset=UTF-8");
        		PrintWriter out = response.getWriter();
        		try {
        		request.getRequestDispatcher("/index.html").include(request, response);
        		} finally {
        		out.close(); }
        		
	}

}
