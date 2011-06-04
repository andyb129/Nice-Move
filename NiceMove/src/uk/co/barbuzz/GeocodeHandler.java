/**
 * 
 */
package uk.co.barbuzz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.TreeMap;

/**
 * @author Andy
 *
 */
public abstract class GeocodeHandler {

    private static String googleURL = "http://maps.google.com/maps/geo?output=csv&oe=utf8&sensor=false&key="; //note csv output requested
    private static String googleKey = "ABQIAAAAzdVFtYn6UCKvqpf6PGckkxQymoj9j-I3BNkNKyc45NT88zQp8xRUcjiljXqB9wAx6sa-4Li-D_o8Sg";
    private static String googleQuery = "&q=";

    //
    //...
    //
    public static String[] getGeocode(String geoTarget) {
    	
    	String [] geoCodes = new String[2];
	    //encode the geoTarget in case there are any non-URL friendly
	    //characters included (such as spaces and quotes)
	    String encodedGeoTarget = null;
	    try {
	    	encodedGeoTarget = URLEncoder.encode(geoTarget, "UTF-8");
	    } catch (UnsupportedEncodingException uee) {
	    	//throw new InfrastructureException(uee);
	    }
	
	    //build the geocoding URL
	    URL googleGeocodeURL = null;
	    try {
	    	googleGeocodeURL = new URL(googleURL + googleKey + googleQuery + encodedGeoTarget);
	    	//log.finer("Complete URL for geocode request : " + googleGeocodeURL.toString());
	    } catch (MalformedURLException mue) {
	    	//do something
	    }
	    	    
	    BufferedReader in = null;
	    try {	    	
    		in = new BufferedReader(new InputStreamReader(googleGeocodeURL.openStream()));
			if (in.ready()) {
			    String response = in.readLine();
			    //log.finer("Google Responded with : " + response);
			    String[] split = response.split(",");
			    //log.finer("split is " + split);
			
			    try {
				    //first check the response code for sign of problems
				    Integer responseCode = Integer.parseInt(split[0]);
				    //log.finer("Response Code: " + responseCode);
				    if (responseCode.equals(620)) {
				    //max number of queries exceeded
				    } else if (responseCode.compareTo(new Integer(201)) > 0) {
					    //Google indicated a problem occurred - the responseCode value will provide more info
					    //see http:code.google.com/apis/maps/documentation/geocoding/index.html#StatusCodes
				    }
				
				    //response code good
				    //parse geocode data from CSV response...				    
				    //latitude = Float.parseFloat(split[2]);
				    geoCodes[0] = split[(split.length-2)];
				    //log.finer("latitude is " + latitude);
				    //longitude = Float.parseFloat(split[3]);
				    geoCodes[1] = split[(split.length-1)];
				    //log.finer("longitude is " + longitude);
				    //float accuracy = Float.parseFloat(split[1]);
				    //log.finer("accuracy is " + accuracy);
			    } catch (NumberFormatException nfe) {
			    	//log.severe("Problem parsing response..." + nfe.getMessage());
			    }

			} else {
				//log.severe("Unable to open URL @ " + googleGeocodeURL.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    } finally {
		    if (in != null) {
		    	try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
	    }
	    
	    return geoCodes;
    }

	
}
