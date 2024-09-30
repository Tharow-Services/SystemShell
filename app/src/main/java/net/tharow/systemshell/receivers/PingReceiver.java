package net.tharow.systemshell.receivers;

import static net.tharow.systemshell.api.SystemShellAPI.ACTION_API_READY;
import static net.tharow.systemshell.api.SystemShellAPI.EXTRA_CALLBACK_PKG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;

public class PingReceiver extends BroadcastReceiver {
    private static final String TAG = "SysPingRecv";
    @Override
    public void onReceive(Context context, Intent intent) {
        IntentSender sender = intent.getParcelableExtra(EXTRA_CALLBACK_PKG);
        if (sender == null) {
            Log.e(TAG, "no sender specified; will not receive results");
        } else {
            String pkg = sender.getCreatorPackage();
            context.sendBroadcast(new Intent(ACTION_API_READY).setPackage(pkg));
        }
    }
}
