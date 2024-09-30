package net.tharow.systemshell;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class SystemService extends IntentService {
    /**
     * @param name
     * @deprecated
     */
    public SystemService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
