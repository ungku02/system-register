package javaclasses;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
   

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://ec2-3-93-160-246.compute-1.amazonaws.com:5432/dd67br7uc3qkne","jckaczutxzdypp","cfd35c3ab957f428ca44f080733890235dc5feeca68a060218c3be7c3e58fad4");
			PreparedStatement pst =con.prepareStatement("insert into users(uname,uemail,upwd,umobile) values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, uemail);
			pst.setString(3, upwd);
			pst.setString(4, umobile);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if(rowCount>0) {
			
				   out.println("<script type=\"text/javascript\">");
				   out.println("alert('Registration Success! Log in now.');");
				   out.println("location='login.jsp';");
				   out.println("</script>");
	
			}
			else {
				request.setAttribute("status", "failed");
			dispatcher.forward(request, response);}
			
		} catch (Exception e) { 
			e.printStackTrace();
		}
			
		}public void freeConnection(Connection con) {
		    if (con == null) {
		        return;
		    }
		    try {
		        con.close();
		    } catch (SQLException e) {
		        System.out.println(e);
		    }
		}
	
}
	



