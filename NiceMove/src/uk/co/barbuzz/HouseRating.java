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
public class HouseRating extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the House Rating tab");
        setContentView(textview);
    }
}
