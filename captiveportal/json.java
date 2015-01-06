package captiveportal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class json {
	private final String teamKey = "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX";
	private final String teamId = "INXXX-X";
	private String[] listTickets(String url, String json) throws Exception {
		 
		//The url to the JSON listener
		URL http = new URL(url);
		//Make the connection to the listener
		HttpURLConnection con = (HttpURLConnection) http.openConnection();
 
		//add reuqest headers
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		// Send post request
		//Do some fancy shizzle
		con.setDoOutput(true);
		OutputStreamWriter data= new OutputStreamWriter(con.getOutputStream());
		//Send the JSON request
		data.write(json);
		data.flush();
		//Get the JSON response
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		//Put the response in a string (not in the underpants)
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//Parse to JSON
		JSONParser parser=new JSONParser();
		  //Make it a string
	      String s = response.toString();
	      //Just give it a shot
	      try{
	    	 //Create JSON object
	         Object obj = parser.parse(s);
	         //Get the ticket variable from the full JSON object
	         JSONObject tickets = (JSONObject)obj;
	         //Make from the value of tickets an array (because we can and it simply is)
	         JSONArray array = (JSONArray)tickets.get("tickets");
	         //Create an array from every ticketNumber in the array (with maximum as amount if tickets)
	         String[] ticketNumbers = new String[(int) array.size()];
	         //Let loop it
	         for(int i = 0; i < (int) array.size(); i++)
	         	{
	        	 //Get the ticket number from each ticket
	        	 JSONObject ticketNumber = (JSONObject)array.get(i);
	        	 //Make from the number a string and put in our newly created array
	        	 ticketNumbers[i] = (String) ticketNumber.get("ticketNumber");
	        	 //Not at the end? Lets do it again!
	         	}
	         //Now the purpose of this function Return all the ticket number in a singel array
	         return ticketNumbers;
	         //If you found le wild Charizard catch it.
	      }catch(ParseException pe){
	         System.out.println("position: " + pe.getPosition());
	         System.out.println(pe);
	         /*
	          	I wanna be the very best,
				Like no one ever was.
				To catch them is my real test,
				To train them is my cause.
	          */
	      }
	      //If error return it
	     String[] returnArray = {"ERROR", "BOEHOEHOE"};
		return returnArray;
	      
 
	}
	private String grandAccessRequest(String json) throws Exception {
		 
		//The url to the JSON listener
		URL http = new URL("http://webapi.implementation.computerscience.international:9323/CFIS/Ticket");
		//Make the connection to the listener
		HttpURLConnection con = (HttpURLConnection) http.openConnection();
 
		//add reuqest headers
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		// Send post request
		con.setDoOutput(true);
		OutputStreamWriter data= new OutputStreamWriter(con.getOutputStream());
		data.write(json);
		//Send JSON request
		data.flush();
		//Read JSON response
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		//Get all the output, and place it in one string
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//Parse result to JSON
		JSONParser parser=new JSONParser();
		  //Make from a raw JSON object an string
	      String s = response.toString();
	      //Create JSON object
	      Object obj = parser.parse(s);
	      //Get the respond code 
	      JSONObject resultCode = (JSONObject)obj;
	      //Make the code a string
	      return (String) resultCode.get("result");  
	}
	@SuppressWarnings("unchecked")
	//Deprecated since version 04-01-2015. So no small talk
	public boolean checkTicket(String ticketNumber) throws Exception
		{
		JSONObject json=new JSONObject();
		Random random = new Random();
		int  requestId = random.nextInt(9999) + 1000;
		json.put("function", "List");
		json.put("teamId", teamId);
		json.put("teamKey", teamKey);
		json.put("requestId", new Integer(requestId));
		StringWriter out = new StringWriter();
		  json.writeJSONString(out);
		  String jsonText = out.toString();
		
		String[] ticketNumbers = listTickets("http://webapi.implementation.computerscience.international:9323/CFIS/Ticket", jsonText);
		for(int i = 0; i < ticketNumbers.length; i++)
			{
			if(new String(ticketNumbers[i]).equals(ticketNumber))
				{
				return true;
				}
			}
		return false;
		}
	@SuppressWarnings("unchecked")
	public boolean grandAccess(String ticketNumber) throws Exception 
		{
		//Create JSON object
		JSONObject json=new JSONObject();
		//Our unique ID will be a timestamp
		//Set the stamp format
		DateFormat TimeStamp = new SimpleDateFormat("MMddyyyyHHmmssSSS");
		//Get the current date
		Date today = Calendar.getInstance().getTime();
		//Make it a string
		String CurrentTimeStamp = TimeStamp.format(today);
		//Put variables in our JSON object
		json.put("function", "RegisterInternetAccess");
		json.put("teamId", teamId);
		json.put("teamKey", teamKey);
		json.put("requestId", CurrentTimeStamp);
		json.put("ticketNumber", ticketNumber);
		//Write it to a string
		StringWriter out = new StringWriter();
		  json.writeJSONString(out);
		  String jsonText = out.toString();
		//Call the function grandAccessRequest
		String grandResult = grandAccessRequest(jsonText);
		//If login succesfull or if user already logged in. Then you may pass
		if(grandResult.equals("0") || grandResult.equals("8"))
		{
			//Congrats. Now you can log in!
			return true;
		}
		//Else..
		else
		{
			//Not
			//Print to the system why not
			System.out.println("Suddenly le wild error code (" +grandResult+ ") appeared!!");
			//Say it fails
			return false;
		}
	}
}
