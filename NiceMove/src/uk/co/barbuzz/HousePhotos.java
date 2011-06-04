/**
 * 
 */
package uk.co.barbuzz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Andy
 *
 */
public class HousePhotos extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the House Photos tab");
        setContentView(textview);
    }
}
