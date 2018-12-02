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
public class eventList extends HttpServlet
{
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
            System.out.println("DB connection successful");
            
            String eventSQLQuery="SELECT * from event_list as e, field_details as s, team_details as t1, team_details as t2  WHERE e.event_location = s.field_id AND (e.team1_id = t1.team_id AND e.team2_id = t2.team_id)";
            
            if(request.getParameter("q")!=null){
                String q=request.getParameter("q");
                eventSQLQuery+="AND (s.field_name LIKE \'%"+q+"%\' OR s.field_location LIKE \'%"+q+"%\' OR s.City LIKE \'%"+q+"%\' )";
            }

            if(request.getParameter("c")!=null){
                eventSQLQuery+=" AND s.City LIKE \'%"+request.getParameter("c")+"%\'";
            }

            if(request.getParameter("s")!=null){
                eventSQLQuery+=" AND s.field_name LIKE \'%"+request.getParameter("s")+"%\'";
            }

            if(request.getParameter("sp")!=null){
                eventSQLQuery+=" AND t1.sport_played LIKE \'%"+request.getParameter("sp")+"%\'";
                eventSQLQuery+=" AND t2.sport_played LIKE \'%"+request.getParameter("sp")+"%\'";
            }
            
            // String stadiumSQLQuery="SELECT * from field_details  WHERE city LIKE \"%" + cityName + "%\"";
            System.out.println(eventSQLQuery);

            Statement stmt=con.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(eventSQLQuery);
            // System.out.println("Success");
            JSONArray result = new JSONArray();
            
            while(rs.next()){
                // System.out.println(rs.getS);
            //     JSONObject stadium = new JSONObject();
            //     JSONObject coordinates = new JSONObject();
            //     coordinates.put("lat" , rs.getFloat("Latitude"));
            //     coordinates.put("lng" , rs.getFloat("Longitude"));
            //     stadium.put("id",rs.getInt("field_id"));
            //     stadium.put("name",rs.getString("field_name"));
            //     stadium.put("coordinates",coordinates);
            //     System.out.println(stadium+"\n\n");
            //     result.add(stadium);
            }
            // System.out.println(result);
            // response.getWriter().println(JSON.toString(result));
            con.close();  
        }catch(Exception e){ System.out.println(e);}  
  }
}