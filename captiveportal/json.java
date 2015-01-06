package captiveportal;

import java.util.Random;
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
		 
		
		URL http = new URL(url);
		HttpURLConnection con = (HttpURLConnection) http.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		// Send post request
		con.setDoOutput(true);
		OutputStreamWriter data= new OutputStreamWriter(con.getOutputStream());
		data.write(json);
		data.flush();
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		JSONParser parser=new JSONParser();
	      String s = response.toString();
	      
	      try{
	         Object obj = parser.parse(s);
	         JSONObject tickets = (JSONObject)obj;
	         JSONArray array = (JSONArray)tickets.get("tickets");
	         String[] ticketNumbers = new String[(int) array.size()];
	         for(int i = 0; i < (int) array.size(); i++)
	         	{
	        	 JSONObject ticketNumber = (JSONObject)array.get(i);
	        	 ticketNumbers[i] = (String) ticketNumber.get("ticketNumber");
	         	}
	         return ticketNumbers;
	      }catch(ParseException pe){
	         System.out.println("position: " + pe.getPosition());
	         System.out.println(pe);
	      }
	     String[] returnArray = {"ERROR", "FOUTMELDING"};
		return returnArray;
	      
 
	}
	private String grandAccessRequest(String json) throws Exception {
		 
		
		URL http = new URL("http://webapi.implementation.computerscience.international:9323/CFIS/Ticket");
		HttpURLConnection con = (HttpURLConnection) http.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		// Send post request
		con.setDoOutput(true);
		OutputStreamWriter data= new OutputStreamWriter(con.getOutputStream());
		data.write(json);
		data.flush();
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		JSONParser parser=new JSONParser();
	      String s = response.toString();
	      Object obj = parser.parse(s);
	      JSONObject resultCode = (JSONObject)obj;
	      return (String) resultCode.get("result");  
	}
	@SuppressWarnings("unchecked")
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
		JSONObject json=new JSONObject();
		Random random = new Random();
		int  requestId = random.nextInt(9999) + 1000;
		json.put("function", "RegisterInternetAccess");
		json.put("teamId", teamId);
		json.put("teamKey", teamKey);
		json.put("requestId", new Integer(requestId));
		json.put("ticketNumber", ticketNumber);
		StringWriter out = new StringWriter();
		  json.writeJSONString(out);
		  String jsonText = out.toString();
		String grandResult = grandAccessRequest(jsonText);
		if(grandResult.equals("0") || grandResult.equals("8"))
		{
			return true;
		}
		else
		{
			System.out.println("Suddenly le wild error code (" +grandResult+ ") appeared!!");
			return false;
		}
	}
}
