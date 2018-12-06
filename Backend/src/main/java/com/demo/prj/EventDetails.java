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
public class EventDetails extends HttpServlet
{
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Hell");
		response.setContentType("application/json; charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
    	try 
    	{
    		Class.forName("com.mysql.jdbc.Driver");
    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_my_game","root","root");   
    		String eventId = request.getParameter("eid");
            
            //JSONArray result = new JSONArray();
            JSONObject json = new JSONObject();            
            String sql1="SELECT e.event_status as status, e.event_location as locid, e.team2_id as t2id, e.team1_id as t1id, e.event_date as edate, e.name as ename,t1.team_name as t1Name,t2.team_name as t2Name from event_list as e, team_details as t1, team_details as t2, field_details as s WHERE event_id ="+eventId+ " AND e.team1_id=t1.team_id AND e.team2_id=t2.team_id AND e.event_location = s.field_id";           
            
            Statement stmt=con.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql1);
    	    System.out.println(sql1);
            JSONObject event = new JSONObject();
   //  		// out.println(username);
   //  		// out.println(password); 
    		if(rs.next())
			{   			
                // System.out.println("Success");
                
                event.put("ename",rs.getString("ename"));
                event.put("t1Name",rs.getString("t1Name"));
                event.put("t2Name",rs.getString("t2Name"));
                // edate
                event.put("edate",rs.getDate("edate"));
                event.put("t1id",rs.getInt("t1id"));
                
                event.put("t2id",rs.getInt("t2id"));

                event.put("locid",rs.getInt("locid"));
                event.put("status",rs.getString("status"));
                
                // stadium.put("coordinates",coordinates);
                // System.out.println(event);
    			
			} 
    		else 
    		{
                // System.out.println("Failure");

		    }
            System.out.println(event);
			response.getWriter().println(JSON.toString(event));
			con.close();  
        }catch(Exception e){ System.out.println(e);}  
   }
}