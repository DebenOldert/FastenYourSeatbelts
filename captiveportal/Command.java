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
    	        System.out.println(line);
    	    }
    	    if((line = is.readLine()) == "SUCCES") {
    	    	return true;
    	    }
    	    else {
    	    return true;	
    	    }
			} catch ( Exception err ) {
				err.printStackTrace();
				return false;
			}

		}
	}

