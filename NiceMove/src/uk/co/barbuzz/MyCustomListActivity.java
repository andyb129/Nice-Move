/**
 * 
 */
package uk.co.barbuzz;

import android.app.ListActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author SAB
 *
 * Need to somehow put the custom title bar code into its own class
 * by extending the Activity class so its automatically included in all activities.
 * 
 * TODO finish this class for custom title bar re-use
 */
public class MyCustomListActivity extends ListActivity {

	public void createTitlebar() {
		
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        ((TextView)findViewById(R.id.title)).setText("Results");
                       
        findViewById(R.id.back).setOnClickListener(new OnClickListener() {

                public void onClick(View v) {

                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));

                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

                }

        });
		
	}
	
}
