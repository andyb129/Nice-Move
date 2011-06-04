/**
 * 
 */
package uk.co.barbuzz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.TreeMap;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * @author Andy
 *
 */
public class Search extends Activity {
	
	private static final String MSG = "MSG";
	private NMApplication application;  
	private TreeMap<String, String> searchData;
	
	private List<Message> messages;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);                               
        setContentView(R.layout.search);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_search);     
        
        //setup the location spinner
        Spinner spinnerlocation = (Spinner) findViewById(R.id.SpinnerLocation);        
        
        ArrayAdapter<CharSequence> adapterLocation = ArrayAdapter.createFromResource(
                this, R.array.location_array, R.layout.spinneritemlayout);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerlocation.setAdapter(adapterLocation);
        
     	//setup the property type spinner
        Spinner spinnerpropertytype = (Spinner) findViewById(R.id.SpinnerPropertyType);        
        
        ArrayAdapter<CharSequence> adapterPropertyType = ArrayAdapter.createFromResource(
                this, R.array.propertytype_array, R.layout.spinneritemlayout);
        adapterPropertyType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpropertytype.setAdapter(adapterPropertyType);
        
        //setup the radius spinner
        Spinner spinnerradius = (Spinner) findViewById(R.id.SpinnerRadius);        
        
        ArrayAdapter<CharSequence> adapterRadius = ArrayAdapter.createFromResource(
                this, R.array.radius_array, R.layout.spinneritemlayout);
        adapterRadius.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerradius.setAdapter(adapterRadius);
        
        //setup the max price spinner
        Spinner spinnermaxprice = (Spinner) findViewById(R.id.SpinnerMaxPrice);
        
        ArrayAdapter<CharSequence> adapterMaxPrice = ArrayAdapter.createFromResource(
                this, R.array.maxprice_array, R.layout.spinneritemlayout);
        adapterMaxPrice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnermaxprice.setAdapter(adapterMaxPrice);

        
    }
    
    
    public void searchData(View button) {
    	
    	// collect the data from the form view and process here    	
    	collectUserData();    	    
    	
    	//loadFeed("list");  
    	new LoadFeedTask().execute("list");     	 
    	
    }
  

	private void collectUserData() {
		
		//final EditText locationField = (EditText) findViewById(R.id.EditTextLocation);  
    	//String location = locationField.getText().toString();
    	
    	//had to replace location with a spinner as unable to send location string to RSS feed URL!
    	final Spinner locationField = (Spinner) findViewById(R.id.SpinnerLocation);  
    	String location = locationField.getSelectedItem().toString();     	
    	
    	final Spinner propertytypeField = (Spinner) findViewById(R.id.SpinnerPropertyType);  
    	String propertytype = propertytypeField.getSelectedItem().toString(); 
    	
    	final Spinner radiusField = (Spinner) findViewById(R.id.SpinnerRadius);  
    	String radius = radiusField.getSelectedItem().toString(); 
    	
    	final Spinner maxpriceField = (Spinner) findViewById(R.id.SpinnerMaxPrice);  
    	String maxprice = maxpriceField.getSelectedItem().toString(); 
    	
    	searchData = new TreeMap<String, String>();
    	searchData.put("location", location);
    	searchData.put("radius", radius);
    	searchData.put("maxprice", maxprice);  
    	searchData.put("propertytype", propertytype);
		
	}

	public void showMap(View button) {
		
    	// collect the data from the form view and process here    	
		collectUserData();
    	
    	//loadFeed("map");   
    	new LoadFeedTask().execute("map");
    	
    }
	
	public void takePic(View button) {
		
		startActivity(new Intent("uk.co.barbuzz.HouseTabs"));
    	
    }
    
	/**
	 * Tried to write a class here to implement a TimerTask so I could prevent app crashing when 
	 * retrieval of RSS took too long.....needs more testing
	 * @author Andy
	 *
	 */
	public class LoadFeedTaskTime extends TimerTask {
		
		private String type;

		public LoadFeedTaskTime(String type) { this.type = type; }
		
		public void run() {
			Search.this.loadFeed(type);
		}
	}
	
	private class LoadFeedTask extends AsyncTask<String, Void, Void> {
      private final ProgressDialog dialog = new ProgressDialog(Search.this);

      // can use UI thread here
      protected void onPreExecute() {
         this.dialog.setMessage("Nice Move! \n\nSearching for properties...");
         this.dialog.show();
      }

      // automatically done on worker thread (separate from UI thread)
      protected Void doInBackground(final String... args) {
    	  
    	 /*
    	 //add timer here to account for timeout if internet connection slow 
    	 Timer t = new Timer();
    	 t.scheduleAtFixedRate(new LoadFeedTaskTime(args[0]), 0, 60000);
    	 t.cancel();*/
    	  
         Search.this.loadFeed(args[0]);
         return null;
      }

      // can use UI thread here
      protected void onPostExecute(final Void unused) {
         if (this.dialog.isShowing()) {
            this.dialog.dismiss();
         }
         
      }
	}

	
	private void loadFeed(String activityDisp) {
		// load the xml feed based on the data captured    	
    	FeedParser parser = FeedParserFactory.getParser(searchData);    	
    	
    	try {
			messages = parser.parse();
			
			//get the image URL and house address here
	    	setAdditionalMsgInfo();    	
	    	
	    	//get geo locations here
	    	setGeoLocations();
	    	
	    	//set in app context for other activities to access    	
	    	this.application = (NMApplication) this.getApplication();
	    	application.setAppMessages(messages);
	    	
	    	//choose which activity to display
	    	if (activityDisp=="list") {
	    		//start the message list activity
		    	startActivity(new Intent("uk.co.barbuzz.MessageList"));
	    	} else {
	    		//start the google map activity
	        	startActivity(new Intent("uk.co.barbuzz.GoogleMap"));
	    	}	    	
			
		} catch (IOException e) {
			//show message if there parse cannot connect to the website to retrieve RSS
			Toast.makeText(getBaseContext(), "No internet connection, unable to retrieve results", Toast.LENGTH_LONG).show();
			//startActivity(new Intent("uk.co.barbuzz.Search"));		
			//e.printStackTrace();
		} catch (SAXException e) {			
			Toast.makeText(getBaseContext(), "No internet connection, unable to retrieve results", Toast.LENGTH_LONG).show();
			//startActivity(new Intent("uk.co.barbuzz.Search"));
			//e.printStackTrace();
		}    	    
    	
	}
	
	private void setAdditionalMsgInfo() {
		List<Message> newMessages = new ArrayList<Message>();		
		Iterator it = messages.iterator();
		int count = 0;
		while (it.hasNext()) {
			Message msg = (Message) it.next();
			count += 1;			
			//extract the address from the title
			String title = msg.getTitle();
			//Log.i("NiceMove", count + " - " + title);
			if (title.indexOf(":")!=-1) {
				
				String address = title.substring(title.indexOf(":")+1);
				address = address.substring(address.indexOf(":")+1).trim();
				msg.setAddress(address);
				
				//extract the image URL from the description
				String desc = msg.getDescription();
				String imgURL = desc.substring(desc.indexOf("src=")+5);
				imgURL = imgURL.substring(0, imgURL.indexOf("\""));
				msg.setImageURL(imgURL);			
				
				//set short description
				String descText = msg.getDescription();
		        descText = descText.substring(descText.indexOf("</a>")+4);
		        descText = descText.substring(0, descText.indexOf("<BR>")).trim();
		        msg.setDescriptionshort(descText);
				
				//add to new message queue
				newMessages.add(msg);
			}
		}		
		messages = newMessages;
	}
	
	
	private void setGeoLocations() {
		
		// get geo locations for all addresses in messages				
		List<Message> newMessages = new ArrayList<Message>();		
		Iterator it = messages.iterator();
		while (it.hasNext()) {
			Message msg = (Message) it.next();
			String [] geoCodes = GeocodeHandler.getGeocode(msg.getAddress());
			msg.setLatitude(geoCodes[0]);
			msg.setLongitude(geoCodes[1]);
			newMessages.add(msg);
		}
		messages = newMessages;
		
	}

}
