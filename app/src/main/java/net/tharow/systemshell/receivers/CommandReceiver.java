package net.tharow.systemshell.receivers;

import static net.tharow.systemshell.Util.streamToString;
import static net.tharow.systemshell.api.SystemShellAPI.ACTION_SHELL_RESULT;
import static net.tharow.systemshell.api.SystemShellAPI.EXTRA_CALLBACK_PKG;
import static net.tharow.systemshell.api.SystemShellAPI.EXTRA_COMMAND;
import static net.tharow.systemshell.api.SystemShellAPI.EXTRA_EXIT_CODE;
import static net.tharow.systemshell.api.SystemShellAPI.EXTRA_REQUEST_ID;
import static net.tharow.systemshell.api.SystemShellAPI.EXTRA_STDERR;
import static net.tharow.systemshell.api.SystemShellAPI.EXTRA_STDOUT;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class CommandReceiver extends BroadcastReceiver {
    private static final String TAG = "SYSCOMReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "received intent: " + intent.toUri(Intent.URI_INTENT_SCHEME));

        String cmd = intent.getStringExtra(EXTRA_COMMAND);
        int id = intent.getIntExtra(EXTRA_REQUEST_ID, -1);
        IntentSender sender = intent.getParcelableExtra(EXTRA_CALLBACK_PKG);

        if (cmd == null) {
            Log.e(TAG, "no command specified");
            return;
        }

        if (sender == null) {
            Log.w(TAG, "no sender specified; will not receive results");
        }

        // execute the command in a new thread
        AsyncTask.execute(() -> {
            try {
                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec(cmd);

                // send results to caller
                if (sender != null) {
                    String stdout = streamToString(p.getInputStream());
                    String stderr = streamToString(p.getErrorStream());
                    int exitCode = p.waitFor();
                    Log.i(TAG, String.format("command complete (id: %d)", id));

                    String pkg = sender.getCreatorPackage();
                    Intent callback = new Intent(ACTION_SHELL_RESULT);
                    callback.putExtra(EXTRA_REQUEST_ID, id);
                    callback.putExtra(EXTRA_STDOUT, stdout);
                    callback.putExtra(EXTRA_STDERR, stderr);
                    callback.putExtra(EXTRA_EXIT_CODE, exitCode);
                    callback.setPackage(pkg);
                    context.sendBroadcast(callback);
                    Log.i(TAG, String.format("callback sent (id: %d)", id));
                }
            } catch (IOException | InterruptedException e) {
                Log.e(TAG, String.format("failed to run command (id: %d): %s", id, cmd));
            }
        });
    }
}

