package cl.colabra.cvilches.insumosestrategicos.network;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import cl.colabra.cvilches.insumosestrategicos.utils.NukeSSLCerts;

public class InsumosEstrategicos extends Application {

    private static InsumosEstrategicos sInstance;

    private RequestQueue mRequestQueue;

    public synchronized static InsumosEstrategicos getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // This instruction makes the App accept all SSL certificates
        NukeSSLCerts.nuke();

        mRequestQueue = Volley.newRequestQueue(this);

        sInstance = this;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}