/**
 * 
 */
package uk.co.barbuzz;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * @author Andy
 *
 */
public class HouseTabs extends TabActivity {
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);       
	    setContentView(R.layout.favoritedetails);                    
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);        
        ((TextView)findViewById(R.id.title)).setText("House Detail");
        
        findViewById(R.id.back).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                }
        });	    
	    
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, HouseDetails.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("housedetail").setIndicator("Detail",
	                      res.getDrawable(R.drawable.ic_tab_housedetails))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, HousePhotos.class);
	    spec = tabHost.newTabSpec("photos").setIndicator("Photos",
	                      res.getDrawable(R.drawable.ic_tab_housephotos))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, HouseNotes.class);
	    spec = tabHost.newTabSpec("notes").setIndicator("Notes",
	                      res.getDrawable(R.drawable.ic_tab_housenotes))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, HouseRating.class);
	    spec = tabHost.newTabSpec("rating").setIndicator("Rating",
	                      res.getDrawable(R.drawable.ic_tab_houserating))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
	
}
