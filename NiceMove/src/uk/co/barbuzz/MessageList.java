package uk.co.barbuzz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MessageList extends Activity {	

	private List<Message> messages;
	private NMApplication application;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //maybe add this to androidmanifest.xml as a property ?
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);       
        setContentView(R.layout.messagelist);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);        
        ((TextView)findViewById(R.id.title)).setText("Results");
        
        findViewById(R.id.back).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                }
        });
        
        //loadFeed(ParserType.ANDROID_SAX);
        buildView();
    }
    
    
    
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, ParserType.ANDROID_SAX.ordinal(), 
				ParserType.ANDROID_SAX.ordinal(), R.string.android_sax);
		menu.add(Menu.NONE, ParserType.SAX.ordinal(), ParserType.SAX.ordinal(),
				R.string.sax);
		menu.add(Menu.NONE, ParserType.DOM.ordinal(), ParserType.DOM.ordinal(), 
				R.string.dom);
		menu.add(Menu.NONE, ParserType.XML_PULL.ordinal(), 
				ParserType.XML_PULL.ordinal(), R.string.pull);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		ParserType type = ParserType.values()[item.getItemId()];
		ArrayAdapter<String> adapter =
			(ArrayAdapter<String>) this.getListAdapter();
		if (adapter.getCount() > 0){
			adapter.clear();
		}
		this.loadFeed(type);
		return true;
	}
	*/
    
    
    private void buildView(){
    	try{    		
    		//get messages from app context
    		application = (NMApplication) this.getApplication();
    		messages = application.getAppMessages();
	    	
	    	List<String> titles = new ArrayList<String>(messages.size());
	    	List<String> imageurls = new ArrayList<String>(messages.size());
	    	for (Message msg : messages){
	    		titles.add(msg.getTitle());
	    		imageurls.add(msg.getImageURL());
	    	}
	    	String [] imgURLs = new String[messages.size()];
	    	imgURLs = imageurls.toArray(imgURLs);
	    	String [] titleStrs = new String[messages.size()];
	    	titleStrs =	titles.toArray(titleStrs);
	    	ListView list = (ListView)findViewById(R.id.list);
	    		    	
	    	list.setOnItemClickListener(new MyOnItemClickListener());
	    	list.setOnItemLongClickListener(new MyItemLongClickListener(this));
	    	
	        LazyAdapter adapter = new LazyAdapter(this, imgURLs, titleStrs);
	        list.setAdapter(adapter);	 
	        
	        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    	
    	} catch (Throwable t){
    		//Log.e("Nice Move",t.getMessage(),t);
    	}
    }
    
    public class MyOnItemClickListener implements OnItemClickListener {
    	
    	public void onItemClick(AdapterView<?> parent, View v, int pos, long row) {
    		application.setSelectedMessage(messages.get(pos));
    		//Log.i("MyOnItemClickListener", "pos="+pos);
    		startActivity(new Intent("uk.co.barbuzz.MessageDetail"));
    	}

    }
    
    public class MyItemLongClickListener implements OnItemLongClickListener {
    	
    	private Context mContext;

		public MyItemLongClickListener(Context context) {
    		super();
    		this.mContext = context;
    	}
    	
		public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long row) {
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);  
			dialog.setTitle(messages.get(pos).getTitle());  
			dialog.setMessage(messages.get(pos).getDescriptionshort());  
			dialog.show();  			
			return false;
		}

	}

	
}