package com.demo.prj;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.ajax.JSON;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
@SuppressWarnings("serial")
public class CustHistory extends HttpServlet
{
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		response.setContentType("application/json; charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
    	try 
    	{
    		Class.forName("com.mysql.jdbc.Driver");
    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_my_game","root","root");   
    		int custId = Integer.parseInt(request.getParameter("cid"));
            JSONObject json = new JSONObject();
            String sql="SELECT c.first_name as fname, c.last_name as lname, t.ticket_id , e.name as name, adult_seats_booked as adult, child_seats_booked as child FROM customer as c, ticket_details as t, event_list as e WHERE  t.customer_id=c.customer_id AND t.event_id=e.event_id AND c.customer_id="+custId+";";        
                               
            Statement stmt=con.createStatement();
            JSONObject event = new JSONObject();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            JSONArray result = new JSONArray();
            while(rs.next()){
                JSONObject ticket = new JSONObject();
                ticket.put("eventName",rs.getString("name"));
                ticket.put("custname",rs.getString("fname")+" "+rs.getString("lname"));
                ticket.put("id",rs.getInt("ticket_id"));
                ticket.put("adult",rs.getInt("adult"));
                ticket.put("child",rs.getInt("child"));
                System.out.println(ticket+"\n\n");
                result.add(ticket);
            }

            response.getWriter().println(JSON.toString(result));
			con.close();  
        }catch(Exception e){ System.out.println(e);}  
   }
}