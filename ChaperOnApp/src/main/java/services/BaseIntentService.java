package services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import utils.ChaperOnBus;

/**
 * Created by ceino on 26/8/15.
 */
public class BaseIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BaseIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
    void postEventOnMainThread(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ChaperOnBus.getInstance().post(event);
                // bus.post(event);
            }
        });

    }
    void postEventOnMainThread(final String event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ChaperOnBus.getInstance().post(event);
                // bus.post(event);
            }
        });

    }

}
