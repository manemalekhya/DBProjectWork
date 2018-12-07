package com.demo.prj;
import java.sql.*;
import java.util.concurrent.ExecutionException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.ajax.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;
import org.eclipse.jetty.util.ajax.JSON;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Checkout extends HttpServlet
{
  //   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  //   {
  //       response.setContentType("application/json; charset=utf-8");
  //       response.addHeader("Access-Control-Allow-Origin", "*");
  //       response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  //       response.setStatus(HttpServletResponse.SC_OK);
  //       PrintWriter out = response.getWriter();
  //       try 
  //       {
  //           Class.forName("com.mysql.jdbc.Driver");
  //           Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_my_game","root","root");   
  //           System.out.println("DB connection successful");
            
  //           String eventSQLQuery="INSERT INTO transaction (amount,card_no,customer_id) values("+100+","+request.getParameter("card")+","+request.getParameter("cust")+")";
            
            
  //           if(request.getParameter("q")!=null){
  //               String q=request.getParameter("q");
  //               eventSQLQuery+="AND (s.field_name LIKE \'%"+q+"%\' OR s.field_location LIKE \'%"+q+"%\' OR s.City LIKE \'%"+q+"%\' )";
  //           }

  //           if(request.getParameter("c")!=null){
  //               eventSQLQuery+=" AND s.City LIKE \'%"+request.getParameter("c")+"%\'";
  //           }

  //           if(request.getParameter("s")!=null){
  //               eventSQLQuery+=" AND s.field_name LIKE \'%"+request.getParameter("s")+"%\'";
  //           }

  //           if(request.getParameter("sp")!=null){
  //               eventSQLQuery+=" AND t1.sport_played LIKE \'%"+request.getParameter("sp")+"%\'";
  //               eventSQLQuery+=" AND t2.sport_played LIKE \'%"+request.getParameter("sp")+"%\'";
  //           }

  //           // String stadiumSQLQuery="SELECT * from field_details  WHERE city LIKE \"%" + cityName + "%\"";
  //           System.out.println(eventSQLQuery);

  //           Statement stmt=con.createStatement();
  //           java.sql.ResultSet rs = stmt.executeQuery(eventSQLQuery);
  //           // System.out.println("Success");
  //           JSONArray result = new JSONArray();
            
  //           while(rs.next()){
  //               // System.out.println(rs.getS);
  //               JSONObject event = new JSONObject();
  //           //     JSONObject coordinates = new JSONObject();
  //           //     coordinates.put("lat" , rs.getFloat("Latitude"));
  //           //     coordinates.put("lng" , rs.getFloat("Longitude"));
                
  //               event.put("id",rs.getInt("id"));
  //               event.put("name",rs.getString("name"));
  //               event.put("team1",rs.getString("team1"));
  //               event.put("team2",rs.getString("team2"));
  //               event.put("stadiumname",rs.getString("stadiumname"));
  //               event.put("event_time",rs.getString("event_time"));
  //           //     stadium.put("name",rs.getString("field_name"));
  //           //     stadium.put("coordinates",coordinates);
  //           //     System.out.println(stadium+"\n\n");
  //               result.add(event);
  //           }
  //           System.out.println(result);
  //           response.getWriter().println(JSON.toString(result));
  //           con.close();  
  //       }catch(Exception e){ System.out.println(e);}  
  // }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {   
        response.setContentType("application/json; charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        Connection con = null;
        
        System.out.println("here");
        
        PreparedStatement insertTransaction = null;
        PreparedStatement insertTicket = null;
        PreparedStatement updateSeats = null;
        
        String insertTransactionString = "INSERT INTO transactions (amount, card_no, date_of_transaction, customer_id) VALUES (?,?,?,?)";

        String insertTicketString = "INSERT INTO ticket_details (event_id, customer_id, transaction_id, seat_id, adult_seats_booked, child_seats_booked, ticket_creation_date) VALUES (?,?,?,?,?,?,?)";
        
        String updateRegularSeatsString = "UPDATE event_list SET r_remaining_seats = ? WHERE event_id = ?";
        
        String updateVipSeatsString = "UPDATE event_list SET v_remaining_seats = ? WHERE event_id = ?";
        
        String checkRemainingSeats = "SELECT r_remaining_seats, v_remaining_seats FROM event_list where event_id = ";
        
        
        
    	try 
    	{
    		 	   	       
    	        StringBuilder buffer = new StringBuilder();
    	        BufferedReader reader = request.getReader();
    	        String line;
    	        while ((line = reader.readLine()) != null) 
    	        {
    	            buffer.append(line);
    	        }
    	        String data = "{\"eventId\":"+request.getParameter("eid")+
                ",\"userId\":"+request.getParameter("cid")+
                ",\"stadiumId\":"+request.getParameter("sid")+","+
                "\"adult\":"+request.getParameter("adult")+
                ",\"child\":"+request.getParameter("child")+
                ",\"type\":\""+request.getParameter("type")+"\","+
                "\"card\":"+request.getParameter("card")+"}";
    	        JSONParser parser = new JSONParser(); 
    	        JSONObject json = new JSONObject();
    	        
                // System.out.println(data);
    			try 
    			{
    				json = (JSONObject) parser.parse(data);
    			} catch (ParseException e) 
    			{
    			// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			System.out.println("here2");
    			Long userId = (Long)json.get("userId");
    			Long eventId =(Long)json.get("eventId");
    			Long stadiumId =(Long)json.get("stadiumId");
    	        String seatType =(String) json.get("type");
    	        Long adult=(Long)json.get("adult");
    	        Long child=(Long)json.get("child");
    	        Long card=(Long)json.get("card");
    	        
    	        //json.put("status", "new");
    	        System.out.println("u: " +userId+ "e: "+ eventId + "s: "+stadiumId+ "seat: "+seatType+ "a: "+ adult + "c: "+ child);
    	        
				Class.forName("com.mysql.jdbc.Driver");
    	        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_my_game","root","mysql");   
    	        System.out.println("DB connection successful");
    	        con.setAutoCommit(false); //transaction block start
    	        //check remaining seats
    	        Statement stmt=con.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(checkRemainingSeats + eventId);
                System.out.println(1);
    	        if(rs.next()) {
    	        	System.out.println(2);
    	        	if(((rs.getInt("r_remaining_seats") == 0 ||  rs.getInt("r_remaining_seats") < adult + child ) && seatType.equals("Regular")) || ((rs.getInt("v_remaining_seats") == 0 ||  rs.getInt("v_remaining_seats") < adult + child ) && seatType.equals("VIP"))) {
    	        		System.out.println(3);
    	        		JSONObject errRes = new JSONObject();
        	        	JSONObject err = new JSONObject();
        	        	err.put("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	        	err.put("message", "Cant book! Sold out!");
        	        	errRes.put("errors", "No seats remaining");
        	        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	        	response.getWriter().println(JSON.toString(errRes));
        	        	System.out.println("Seats full, cant book anymore");
    	        	}else {
    	        		
    	        				
    	        		System.out.println(4);
    	        		insertTransaction = con.prepareStatement(insertTransactionString);
    	        		insertTicket = con.prepareStatement(insertTicketString);
    	        		updateSeats = con.prepareStatement(updateRegularSeatsString);
    	        		System.out.println(5);
    	        		
    	        		Date date= new Date();
    	        		long time = date.getTime();
    	        		Timestamp ts = new Timestamp(time);
    	        		
    	        		String priceSeats = "SELECT * FROM seat_details where field_id = " + stadiumId + " and seat_category = \""+ seatType+"\"";
    	        		System.out.println(priceSeats);
    	        		java.sql.ResultSet rsSeat = stmt.executeQuery(priceSeats);
    	        		
    	        		Long amount = (long) 0;
    	        		int seatId = 0;
    	        		if(rsSeat.next()) {
    	        			amount = rsSeat.getInt("adult_price") * adult + rsSeat.getLong("child_price") * child ;
    	        			seatId = rsSeat.getInt("seat_id");
    	        		}
    	        		System.out.println(amount);
    	        		
    	              //insert into transaction
    	        		insertTransaction.setLong(1, amount);
    	        		insertTransaction.setLong(2, card);
    	        		insertTransaction.setString(3,ts.toString());
    	        		insertTransaction.setLong(4, userId);
    	        		insertTransaction.executeUpdate();
    	        		
    	        		String transEntry = "SELECT id FROM transactions order by id desc limit 1";
    	        		java.sql.ResultSet transSet = stmt.executeQuery(transEntry);
    	        		
    	        		int transId = 0;
    	        		if(transSet.next()) 
    	        			transId  = transSet.getInt("id");
    	        		System.out.println(transId);
    	        		
    	        	  //insert into ticket
    	        		insertTicket.setLong(1, eventId);
    	        		insertTicket.setLong(2, userId);
    	        		insertTicket.setLong(3, transId);
    	        		insertTicket.setLong(4, seatId);
    	        		insertTicket.setLong(5, adult);
    	        		insertTicket.setLong(6, child);
    	        		insertTicket.setString(7, ts.toString());
    	        		insertTicket.executeUpdate();
    	        
    	        		
    	              //update seat count
    	        		if(seatType.equals("Regular")) {
    	        			updateSeats = con.prepareStatement(updateRegularSeatsString);
    	        			updateSeats.setLong(1, adult+child);
    	        			updateSeats.setLong(2, eventId);
    	        		}else {
    	        			updateSeats = con.prepareStatement(updateVipSeatsString);
    	        			updateSeats.setLong(1, adult+child);
    	        			updateSeats.setLong(2, eventId);
    	        		}
    	        	}
    	        }else {
    	        	JSONObject errRes = new JSONObject();
    	        	JSONObject err = new JSONObject();
    	        	err.put("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    	        	err.put("message", "Cant book! Sold out!");
    	        	errRes.put("errors", "No seats remaining");
    	        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    	        	response.getWriter().println(JSON.toString(errRes));
    	        	System.out.println("Seats full, cant book anymore");
    	        }
                
    	        
    	        

    	        
    	        con.commit(); //transaction block end
    	} catch (SQLException e ) {
    		JSONObject errRes = new JSONObject();
        	JSONObject err = new JSONObject();
        	err.put("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	err.put("message", e.getMessage());
        	errRes.put("errors", err);
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	response.getWriter().println(JSON.toString(errRes));
        	System.out.println(e);
            if (con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch(SQLException excep) {
                	System.err.print("Transaction failed to roll back");
                }
            }
        } catch(Exception e){
        	
        	JSONObject errRes = new JSONObject();
        	JSONObject err = new JSONObject();
        	err.put("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	err.put("message", e.getMessage());
        	errRes.put("errors", err);
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	response.getWriter().println(JSON.toString(errRes));
        	System.out.println(e);
        }
        	
        	finally {
        		try {
            if (insertTransaction != null) {
            	insertTransaction.close();
            }
            if (insertTicket != null) {
            	insertTicket.close();
            }
            if (updateSeats != null) {
            	updateSeats.close();
            }
            con.setAutoCommit(true);
        		}catch (Exception ex){System.out.println(ex); }
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