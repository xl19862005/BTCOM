package xandy.btcom;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ReadThread extends Thread {
	BluetoothSocket mBTSocket = null;
	Handler mLinkHandler = null;
	ChatActivity mChatActivity = null;
	 
    public ReadThread(ChatActivity chatActivity, BluetoothSocket socket, Handler linkDetectedHandler) {
		// TODO Auto-generated constructor stub
    	mBTSocket = socket;
    	mLinkHandler = linkDetectedHandler;
    	mChatActivity = chatActivity;
	}

	public void run() {
        byte[] buffer = new byte[1024];
        int bytes;
        InputStream mmInStream = null;
        
		try {
			mmInStream = mBTSocket.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
        while (true) {
            try {
                // Read from the InputStream
                if( (bytes = mmInStream.read(buffer)) > 0 )
                {
                    byte[] buf_data = new byte[bytes];
			    	for(int i=0; i<bytes; i++)
			    	{
			    		buf_data[i] = buffer[i];
			    		Log.d("Xandy","Xandy->"+buf_data[i]);
			    	}
			    	
		    		String s = new String(buf_data);
			    	
			    	if(mChatActivity.mAsciiSendOn == false){
			    		s = mChatActivity.byte2HexStr(buf_data, buf_data.length);
			    	}			    	

					Message msg = new Message();
					msg.obj = s;
					msg.what = 1;
					mLinkHandler.sendMessage(msg);
                }
            } catch (IOException e) {
            	try {
					mmInStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                break;
            }
        }
    }
}
