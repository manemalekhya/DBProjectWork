package com.demo.prj;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.ajax.JSON;
import org.json.simple.JSONObject;
@SuppressWarnings("serial")
public class Signup extends HttpServlet
{
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        
        String firstname = request.getParameter("f");
        String lastname = request.getParameter("l");
        String emailaddress= request.getParameter("u");
        String phone= request.getParameter("p");
        
    	try 
    	{
    		Class.forName("com.mysql.jdbc.Driver");
    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_my_game","root","mysql");   
    		System.out.println("DB connection successful");
    		out.println("Welcome to the login page");
    		
    		//JSONArray json = new JSONArray();
    		String sql2="insert into customer (first_name,last_name,phone_number,username) values(?,?,?,?)";
    		System.out.println(sql2);
            JSONObject json = new JSONObject();
            //String sql = "SELECT * FROM login_creds" + " WHERE customer_id= " + username + " AND pwd=\""+ password + "\"";
            //String sql2="insert into customer (first_name,last_name,phone_number,username) values('"+firstname+"','"+lastname+"','"+phone+"','"+emailaddress+"')";
            //PreparedStatement rs2=con.prepareStatement("insert into customer (first_name,last_name,phone_number,username) values('"+firstname+"','"+lastname+"','"+phone+"','"+emailaddress+"')");
            PreparedStatement rs2=con.prepareStatement(sql2);          
            
            //PreparedStatement rs2=con.prepareStatement("insert into customer values(?,?,?,?)");
            //out.println(sql2);            
            //Statement stmt=con.createStatement();
            //java.sql.ResultSet rs2 = stmt.executeQuery(sql2);
    		System.out.println("Success");    
    		out.println(firstname);
    		out.println(lastname);
    		out.println(emailaddress);
    		out.println(phone);
    		//String dbUsername = rs.getString("customer_id");
            //String dbPassword = rs.getString("pwd");
    		//rs2.setString("first_name","last_name","phone_number","username");
    		rs2.setString(1,firstname);  
    		rs2.setString(2,lastname);  
    		rs2.setString(3,phone);
    		rs2.setString(4, emailaddress);
    		System.out.println(rs2);
    		int i=rs2.executeUpdate();  
    		if(i>0)  
    		//out.print("You are successfully registered...");  
    		json.put("success", "Succesfully registered");
			response.getWriter().println(JSON.toString(json));
			con.close();  
        }catch(Exception e){ System.out.println(e);}  
  }
}