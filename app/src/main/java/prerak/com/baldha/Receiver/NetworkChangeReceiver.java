package prerak.com.baldha.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import prerak.com.baldha.service.ServiceManager;

/**
 * Created by AMD21 on 17/4/17.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if(checkInternet(context))
        {
            Toast.makeText(context, "Network Available Do operations",Toast.LENGTH_LONG).show();
        }

    }


    boolean checkInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        if (serviceManager.isNetworkAvailable()) {
            return true;
        } else {
            return false;
        }
    }

}