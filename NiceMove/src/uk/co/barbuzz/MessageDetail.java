/**
 * 
 */
package uk.co.barbuzz;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author SAB
 *
 */
public class MessageDetail extends Activity {
	
	private NMApplication application;
	private Message message;
	private ImageLoader imageLoader;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);       
        setContentView(R.layout.messagedetail);                      
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);        
        ((TextView)findViewById(R.id.title)).setText("House Detail");
        
        findViewById(R.id.back).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                }
        });               
        
        application = (NMApplication) this.getApplication();
		message = application.getSelectedMessage();
        
        ImageView houseImage = (ImageView) findViewById(R.id.ImageViewHouse);
        TextView titleView = (TextView) findViewById(R.id.TextViewTitle);
        TextView descView = (TextView) findViewById(R.id.TextViewDesc);
        TextView datepubView = (TextView) findViewById(R.id.TextViewPubDate);
        
        //rendered url here into bitmap - re-use imageloader
        imageLoader=new ImageLoader(this.getApplicationContext());
        houseImage.setImageBitmap(imageLoader.getBitmap(message.getImageURL()));
        
        titleView.setText(message.getTitle());              
        descView.setText(message.getDescriptionshort());
        datepubView.setText(message.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		datepubView.setText(sdf.format(new Date(message.getDate())));	
        
	}
	
	public void viewWebPage(View button) {
    	// launch selected houses web page
    	
		Intent viewMessage = new Intent(Intent.ACTION_VIEW, 
				Uri.parse(message.getLink().toExternalForm()));
		this.startActivity(viewMessage);
    }
	
	
}
