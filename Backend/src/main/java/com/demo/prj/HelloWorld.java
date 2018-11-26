package com.demo.prj;
import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class HelloWorld extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
        System.out.println("Headers set!");
    	try {
    		Class.forName("com.mysql.jdbc.Driver");  
    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_my_game","root","mysql");   
    		System.out.println("Connection established!");
    		Statement stmt=con.createStatement();  
    		ResultSet rs=stmt.executeQuery("select * from customer"); 
    		System.out.println("Success");  
    			while (rs.next()) {
    				int customer_id=rs.getInt("customer_id");
    		        String first_name = rs.getString("first_name");
    		        String last_name = rs.getString("last_name");
    		        String phone_number = rs.getString("phone_number");
    		        String username = rs.getString("username");
    		        System.out.println(customer_id + "::");
    		        System.out.println(first_name + "::");
    		        System.out.println(last_name + "::");
    		        System.out.println(phone_number + "::");
    		        System.out.println(username + "::");
    		        response.getWriter().println("<h1>"+" "+rs.getInt(1)+rs.getString(2)+rs.getString(3)+rs.getString(4)+rs.getString(5)+" "+"</h1>");
    		     }
    		con.close();  
            }catch(Exception e){ System.out.println(e);}  
    q }
  
}