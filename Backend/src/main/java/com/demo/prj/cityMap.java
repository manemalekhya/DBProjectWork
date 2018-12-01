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
public class cityMap extends HttpServlet
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
            // out.println("Welcome to the login page");
            System.out.println("==============================");
            System.out.println(request.getParameter("q"));
            System.out.println("==============================");
            String cityName = (request.getParameter("q")!=null) ? request.getParameter("q") : "Chicago";
            // String password = request.getParameter("p");
            // //JSONArray result = new JSONArray();
            JSONObject json = new JSONObject();
            // String cityName = "pittsburgh";
            // //String sql = "SELECT * FROM login_creds" + " WHERE customer_id= " + username + " AND pwd=\""+ password + "\"";
            String stadiumSQLQuery="SELECT * from field_details  WHERE city LIKE \"%" + cityName + "%\"";
            System.out.println(stadiumSQLQuery);           
            // out.println(sql1);
            // if(username==null || password==null ) 
            // {
            //     //out.println("Please enter username and password");
            //     json.put("login", "Please enter valid username and password");
            //     response.getWriter().println(JSON.toString(json));
            //     con.close();
            //     return;
            // }
            Statement stmt=con.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(stadiumSQLQuery);
            System.out.println("Success");
            JSONArray result = new JSONArray();
            // while(rs.next()){
            //    System.out.println(rs.next());
            // }

          //   $scope.stadiums=[
          // {coordinates:{lat: 40.4396, lng: -79.9893}, name :"PPG Paints Arena"},
          // {coordinates:{lat: 40.4468, lng: -80.0158}, name :"Heinz Field"},
          // {coordinates:{lat: 40.4469, lng: -80.0057}, name :"PNC Arena"}
          // ];
            while(rs.next()){
                JSONObject stadium = new JSONObject();
                JSONObject coordinates = new JSONObject();
                coordinates.put("lat" , rs.getFloat("Latitude"));
                coordinates.put("lng" , rs.getFloat("Longitude"));
                stadium.put("id",rs.getInt("field_id"));
                stadium.put("name",rs.getString("field_name"));
                stadium.put("coordinates",coordinates);
                System.out.println(stadium+"\n\n");
                result.add(stadium);
            }
            System.out.println(result);
            // out.println(password);
            // //while (rs.next()) 
            // if(rs1.next())
            // {
            //     //For unique username an password rs.next will have one row
            //     //String dbUsername = rs.getString("customer_id");
            //     //String dbPassword = rs.getString("pwd");
            //     //out.println(dbUsername);
            //     //out.println(dbPassword);
            //     //if(dbUsername.equals(username) && dbPassword.equals(password)){
            //     //System.out.println("Login Success");              
            //     //out.println("Login successful");
            //     json.put("login", "Login successful");
            // } 
            // else 
            // {
            //         //System.out.println("Login Failed"); 
            //         //out.println("Login failed");
            //         //out.println("Wrong username or password....");
            //         json.put("login", "Login failed");
            //  }
            response.getWriter().println(JSON.toString(result));
            con.close();  
        }catch(Exception e){ System.out.println(e);}  
  }
}