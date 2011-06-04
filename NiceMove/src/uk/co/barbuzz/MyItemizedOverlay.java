/**
 * 
 */
package uk.co.barbuzz;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * @author SAB
 *
 */
public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private Context mContext;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Object application;
	private ArrayList<OverlayItem> messages;

	public MyItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		this.mContext = context;
	}
	
	/*public MyItemizedOverlay(Drawable defaultMarker, Context context) {
		super(defaultMarker);  
		mContext = context;
	}*/
	
	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);    
		populate();
	}	
	
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}
	
	@Override
	public int size() {
		return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {  
		OverlayItem item = mOverlays.get(index);  
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);  
		dialog.setTitle(item.getTitle());  
		dialog.setMessage(item.getSnippet());  
		dialog.show(); 					
		return true;
	}
	
	
}
