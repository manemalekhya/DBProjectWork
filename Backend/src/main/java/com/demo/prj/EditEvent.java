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
@SuppressWarnings("serial")
public class EditEvent extends HttpServlet
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
            // System.out.println("=======================");
            // System.out.println(request);            
            // System.out.println("=======================");   
    		int eventId = Integer.parseInt(request.getParameter("eid"));
            String eventname = request.getParameter("name");
            int locid = Integer.parseInt(request.getParameter("locid"));
            Date edate = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("edate"));
            int t1id = Integer.parseInt(request.getParameter("t1id"));
            int t2id = Integer.parseInt(request.getParameter("t2id"));
            String status = request.getParameter("status");
            int result = (request.getParameter("result")!=null)?Integer.parseInt(request.getParameter("result")):-1;
            //JSONArray result = new JSONArray();
            System.out.println(edate);
            JSONObject json = new JSONObject();
            String sql1="";
            if(eventId == 0){
                sql1+="INSERT INTO event_date (name,event_date,event_location,team1_id"+
                ",team2_id,event_status) values ("+eventname+","+edate+","+locid+","+t1id+","+t2id
                +","+status+");";
            }else{
                sql1+="UPDATE event_date SET name = \'"+eventname+"\', event_date = \'"+edate+"\', event_location = "
                +locid+", team1_id = "+t1id+", team2_id = "+t2id+
                ", event_status = \'"+status+"\' WHERE event_id="+eventId+";";
            }
                   
            
            Statement stmt=con.createStatement();
            // java.sql.ResultSet rs = stmt.executeQuery(sql1);
            System.out.println("=====================");
    	    System.out.println(sql1);
            JSONObject event = new JSONObject();

   //          System.out.println(event);
			// response.getWriter().println(JSON.toString(event));
			con.close();  
        }catch(Exception e){ System.out.println(e);}  
   }
}
/*
No of games per stadium
select COUNT(*) as noOfGames,s.field_name as name, s.field_id as id from event_list as e, field_details as s WHERE e.event_location=s.field_id GROUP BY e.event_location;

No of tickets per city
select COUNT(*) as noOfTickets,s.City as city from event_list as e, field_details as s, ticket as t WHERE e.event_location=s.field_id AND t.event_id=e.event_id GROUP BY s.City,YEAR(e.event_date) ORDER BY noOfTickets DESC;

Most Buying customer
select count(*) as noOfTickets from ticket as t, customer as c WHERE c.customer_id=t.customer_id GROUP BY t.customer_id ORDER BY noOfTickets DESC LIMIT 10;

revenue per city per year
select sum(t.amount) as revenue, s.City as city from transactions as t, ticket as ti, field_details as s, event as e WHERE t.transaction_id = ti.transaction_id AND e.event_location=s.field_id AND ti.event_id=e.vent_id GROUP BY s.City,YEAR(t.date) ORDER BY revenue DESC LIMIT 10;
*/
