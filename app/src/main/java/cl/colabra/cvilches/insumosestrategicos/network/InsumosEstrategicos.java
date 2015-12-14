package cl.colabra.cvilches.insumosestrategicos.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import cl.colabra.cvilches.insumosestrategicos.utils.NukeSSLCerts;

public class InsumosEstrategicos {
    private static InsumosEstrategicos mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private InsumosEstrategicos(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        // This instruction is for accepting all certificates
        NukeSSLCerts.nuke();
    }

    public static synchronized InsumosEstrategicos getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InsumosEstrategicos(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}