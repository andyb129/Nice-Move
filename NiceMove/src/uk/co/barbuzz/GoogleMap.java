/**
 * 
 */
package uk.co.barbuzz;

import java.util.Iterator;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * @author Andy
 *
 */
public class GoogleMap extends MapActivity {
	
	private MapView mapView;
	private MapController mc;
	private List<Message> messages;
	private NMApplication application;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);       
        setContentView(R.layout.map);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);        
        ((TextView)findViewById(R.id.title)).setText("Map");
        
        findViewById(R.id.back).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                }
        });	    	   
	    
	    mapView = (MapView) findViewById(R.id.mapview1);    
	    mapView.setBuiltInZoomControls(true);
	    mc = mapView.getController();
	    
	    // add map overlays
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.nicemovemarker);
	    MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, this);
	    
	    //get messages from app context
		application = (NMApplication) this.getApplication();
		messages = application.getAppMessages();	    
	    
	    GeoPoint point = null;
	    Iterator it = messages.iterator();
		while (it.hasNext()) {		
			Message msg = (Message) it.next();
	        double lat = Double.parseDouble(msg.getLatitude());
	        double lng = Double.parseDouble(msg.getLongitude());     
	        point = new GeoPoint(
	            (int) (lat * 1E6), 
	            (int) (lng * 1E6));
		    OverlayItem overlayitem = new OverlayItem(point, msg.getTitle(), msg.getDescriptionshort());
		    itemizedoverlay.addOverlay(overlayitem);
		}	    	  
		
	    mapOverlays.add(itemizedoverlay);	    
	    
	    mc.animateTo(point);
        mc.setZoom(13);
	    	   
	}

	@Override
	protected boolean isRouteDisplayed() {		
		return false;
	}
	
}
