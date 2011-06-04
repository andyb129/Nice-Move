/**
 * 
 */
package uk.co.barbuzz;

import java.util.List;

import android.app.Application;
import android.util.Log;

/**
 * @author SAB
 *
 */
public class NMApplication extends Application {

   public static final String APP_NAME = "NiceMove";  
   private List<Message> appMessages;
   private Message selectedMessage;
   
   //private DataHelper dataHelper;   
   
   @Override
   public void onCreate() {
      super.onCreate();
      //Log.d(APP_NAME, "APPLICATION onCreate");	     
   }
   
   @Override
   public void onTerminate() {
      //Log.d(APP_NAME, "APPLICATION onTerminate");      
      super.onTerminate();      
   }

	public void setAppMessages(List<Message> appMessages) {
		this.appMessages = appMessages;
	}

	public List<Message> getAppMessages() {
		return appMessages;
	}

	public void setSelectedMessage(Message selectedMessage) {
		this.selectedMessage = selectedMessage;
	}

	public Message getSelectedMessage() {
		return selectedMessage;
	}
	   	 
	   
	}
