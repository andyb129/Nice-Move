/**
 * 
 */
package uk.co.barbuzz;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Andy
 *
 */
public class PictureCapture extends Activity {

	protected Button _button;
	protected ImageView _image;
	protected TextView _field;
	protected String _path;
	protected boolean _taken;

	protected static final String PHOTO_TAKEN = "photo_taken";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.pictures);

	    _image = ( ImageView ) findViewById( R.id.image );
	    _field = ( TextView ) findViewById( R.id.field );
	    _button = ( Button ) findViewById( R.id.button );
	    _button.setOnClickListener( new ButtonClickHandler() );

	     _path = Environment.getExternalStorageDirectory() + "/make_machine_example.jpg";
	     
	}    
	     
     public class ButtonClickHandler implements View.OnClickListener {
         public void onClick( View view ){
         	startCameraActivity();
         }
     }
     
     protected void startCameraActivity() {
         File file = new File( _path );
         Uri outputFileUri = Uri.fromFile( file );

         Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
         intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );

         startActivityForResult( intent, 0 );
     }
     
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         Log.i( "MakeMachine", "resultCode: " + resultCode );
         switch( resultCode )
         {
         	case 0:
         		Log.i( "MakeMachine", "User cancelled" );
         		break;

         	case -1:
         		onPhotoTaken();
         		break;
         }
     }
     
     protected void onPhotoTaken()
     {
         _taken = true;

         BitmapFactory.Options options = new BitmapFactory.Options();
         options.inSampleSize = 4;

         Bitmap bitmap = BitmapFactory.decodeFile( _path, options );
         _image.setImageBitmap(bitmap);

         _field.setVisibility( View.GONE );
     }
     
     
     @Override
     protected void onSaveInstanceState( Bundle outState ) {
         outState.putBoolean( PictureCapture.PHOTO_TAKEN, _taken );
     }
     @Override
     protected void onRestoreInstanceState( Bundle savedInstanceState)
     {
         Log.i( "MakeMachine", "onRestoreInstanceState()");
         if( savedInstanceState.getBoolean( PictureCapture.PHOTO_TAKEN ) ) {
         	onPhotoTaken();
         }
     }
	
}