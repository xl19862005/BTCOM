package xandy.btcom;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BluetoothAPStateReceiver extends BroadcastReceiver {
	/* 取得默认的蓝牙适配器 */
	private BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		if(intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")){
			Log.d("Xandy", "Xandy bluetooth AP state changed!");
			//开启后台服务
			Intent i = new Intent(context,BTSocketService.class); 
			context.startService(i);
		}else if(intent.getAction().equals("android.bluetooth.device.action.ACL_DISCONNECTED")){
			Log.d("Xandy", "Xandy bluetooth device disconnected!");
			
		}else if("com.xandy.btcom.socket.ACTION".equals(intent.getAction())){
			Toast.makeText(context, "收到消息： " + intent.getStringExtra("btcom.socket.msg"), Toast.LENGTH_SHORT).show();
		}
	}

}
