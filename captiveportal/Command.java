package captiveportal;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Command {
	public boolean Grand (String ip) {
		String command = "sudo /bin/bash /portal/rules.sh grand " +ip;
			try {
    	    Process process;
    	    process = Runtime.getRuntime().exec(command);
    	    String line;
    	    BufferedReader is = new BufferedReader(new InputStreamReader(process.getInputStream()));
    	    while((line = is.readLine()) != null){
    	        if(line.equals("SUCCES"))
    	        	{
    	        	return true;
    	        	}
    	    }
    	    return false;
			} catch ( Exception err ) {
				err.printStackTrace();
				return false;
			}

		}
	}
