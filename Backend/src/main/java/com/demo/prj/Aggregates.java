/*
No of games per stadium
select COUNT(*) as noOfGames,s.field_name as name, s.field_id as id from event_list as e, field_details as s WHERE e.event_location=s.field_id GROUP BY e.event_location;

No of tickets per city
select COUNT(*) as noOfTickets,s.City as city from event_list as e, field_details as s, ticket_details as t WHERE e.event_location=s.field_id AND t.event_id=e.event_id GROUP BY s.City,YEAR(e.event_date) ORDER BY noOfTickets DESC;

Most Buying customer
select count(*) as noOfTickets from ticket_details as t, customer as c WHERE c.customer_id=t.customer_id GROUP BY t.customer_id ORDER BY noOfTickets DESC LIMIT 10;

revenue per city per year
select sum(t.amount) as revenue, s.City as city from transactions as t, ticket_details as ti, field_details as s, event as e WHERE t.transaction_id = ti.transaction_id AND e.event_location=s.field_id AND ti.event_id=e.vent_id GROUP BY s.City,YEAR(t.date) ORDER BY revenue DESC LIMIT 10;
*/ 

package com.demo.prj;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.ajax.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class Aggregates extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json; charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        Connection con = null;
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_my_game","root","root");   
            System.out.println("DB connection successful");
            // String cityName = (request.getParameter("q")!=null) ? request.getParameter("q") : "Chicago";
            JSONObject json = new JSONObject();

            String noOfGamesPerStadium = "select COUNT(*) as noOfGames,s.field_name as name, s.field_id as id from event_list as e, field_details as s WHERE e.event_location=s.field_id GROUP BY e.event_location;";
            String ticketsPerCity  ="select COUNT(*) as noOfTickets,s.City as city from event_list as e, field_details as s, ticket_details as t WHERE e.event_location=s.field_id AND t.event_id=e.event_id GROUP BY s.City,YEAR(e.event_date) ORDER BY noOfTickets DESC;";
            String frequentCustomers="select c.customer_id as id,c.first_name as fname,c.last_name as lname ,count(*) as noOfTickets from transactions as t, customer as c WHERE c.customer_id=t.customer_id GROUP BY t.customer_id ORDER BY noOfTickets DESC LIMIT 10";
           	String revenuePerCity="select sum(t.amount) as revenue, s.City as city, YEAR(t.date_of_transaction) as year from transactions as t, ticket_details as ti, field_details as s, event_list as e WHERE t.id = ti.transaction_id AND e.event_location=s.field_id AND ti.event_id=e.event_id GROUP BY s.City,YEAR(t.date_of_transaction) ORDER BY revenue DESC LIMIT 10";
            
            Statement stmt1=con.createStatement();
            Statement stmt2=con.createStatement();
            Statement stmt3=con.createStatement();
            Statement stmt4=con.createStatement();
            java.sql.ResultSet rs1 = stmt1.executeQuery(noOfGamesPerStadium);
            java.sql.ResultSet rs2 = stmt2.executeQuery(ticketsPerCity);
            java.sql.ResultSet rs3 = stmt3.executeQuery(frequentCustomers);
            java.sql.ResultSet rs4 = stmt4.executeQuery(revenuePerCity);
            System.out.println("Success");
            JSONObject result = new JSONObject();
            JSONArray subResult = new JSONArray();
            JSONArray subResult2 = new JSONArray();
            JSONArray subResult3 = new JSONArray();
            JSONArray subResult4 = new JSONArray();
            while(rs1.next()){
                JSONObject stadium = new JSONObject();
                stadium.put("gamesCount",rs1.getInt("noOfGames"));
                stadium.put("name",rs1.getString("name"));
                stadium.put("id",rs1.getInt("id"));
                System.out.println(stadium+"\n\n");
                subResult.add(stadium);
            }
            result.put("stadiumGameCount",subResult);

            while(rs2.next()){
                JSONObject city = new JSONObject();
                city.put("noOfTickets",rs2.getInt("noOfTickets"));
                city.put("city",rs2.getString("city"));
                
                System.out.println(city+"\n\n");
                subResult2.add(city);
            }
            result.put("ticketsPerCity",subResult2);

            while(rs3.next()){
                JSONObject cutomers = new JSONObject();
                cutomers.put("noOfTickets",rs3.getInt("noOfTickets"));
                cutomers.put("name",rs3.getString("fname")+" "+rs3.getString("lname"));
                cutomers.put("id",rs3.getInt("id"));
                System.out.println(cutomers+"\n\n");
                subResult3.add(cutomers);
            }
            result.put("topCustomers",subResult3);

            while(rs4.next()){
                JSONObject city = new JSONObject();
                city.put("revenue",rs4.getInt("revenue"));
                city.put("city",rs4.getString("city"));
                city.put("year",rs4.getInt("year"));
                System.out.println(city+"\n\n");
                subResult4.add(city);
            }
            result.put("revenuePerCityPerYear",subResult4);

            response.getWriter().println(JSON.toString(result));  
        } catch(Exception e){ 
        	JSONObject errRes = new JSONObject();
        	JSONObject err = new JSONObject();
        	err.put("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	err.put("message", e.getMessage());
        	errRes.put("errors", err);
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	response.getWriter().println(JSON.toString(errRes));
        	System.out.println(e);
        } finally {
        	if(con != null) {
        		try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
  }
}