package cl.colabra.cvilches.insumosestrategicos.network;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;

import cl.colabra.cvilches.insumosestrategicos.utils.NukeSSLCerts;

public class InsumosEstrategicos extends Application {
    private static InsumosEstrategicos mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    @Override
    public void onCreate() {
        super.onCreate();

        mCtx = getApplicationContext();

        mRequestQueue = getRequestQueue();

        // Add this line in order to accept all certificates
        NukeSSLCerts.nuke();

        // Add Cookie Manager
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);
    }

    public static synchronized InsumosEstrategicos getInstance() {
        if (mInstance == null) {
            mInstance = new InsumosEstrategicos();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}