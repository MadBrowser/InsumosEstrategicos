package cl.colabra.cvilches.insumosestrategicos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

import cl.colabra.cvilches.insumosestrategicos.network.InsumosEstrategicos;
import cl.colabra.cvilches.insumosestrategicos.utils.Config;
import cl.colabra.cvilches.insumosestrategicos.utils.NetworkUtilities;
import cl.colabra.cvilches.insumosestrategicos.utils.SOAPUtils;
import cl.colabra.cvilches.insumosestrategicos.utils.SessionManager;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "SGIE_Login";

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    // Session Manager
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        CardView mSignInButton = (CardView) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        // Sets up Session Manager
        sessionManager = new SessionManager(this);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) cancel = false;
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            /*mAuthTask = new UserLoginTask(username, password);*/
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Define username client validations
        return username.length() > 1;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Define password client validations
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /* Volley request for getting the stores list */
    private void getStoresList() {

        /*CookieManager defaultManager = (CookieManager) CookieHandler.getDefault();
        defaultManager.getCookieStore();*/

        // Set Url
        String mUrl = Uri.parse(Config.getServerUrl())
                .buildUpon()
                .path(Config.getStoresListUrl())
                .build().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrl,
                successListener(), errorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Cookie", sessionManager.getSessionCookie());
                headers.put("Accept", Config.getContentJson());
                return headers;
            }
        };

        // Set tag for Login requests
        stringRequest.setTag(TAG);
        // With the request created, simply add it to our Application's RequestQueue
        InsumosEstrategicos.getInstance(this).getRequestQueue().add(stringRequest);
    }

    private Response.Listener<String> successListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // TODO: Parse response and create DB elements
                Log.d(TAG, response);
            }
        };
    }


    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        };
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        /*UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }*/

        UserLoginTask() {
            mUsername = "fba";
            mPassword = "Colabra5900+";
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Allows the use of not thread safe deprecated apache HTTP library
            // Sadly this is necessary in order to make SOAP calls
            // TODO: Find another way to get the authentication cookie
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // Set http params
            HttpParams httpParams = new BasicHttpParams();
            httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            int timeoutConnection = Config.getDefaultTimeout();
            HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
            int timeoutSocket = Config.getDefaultTimeout();
            HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);

            // Set http request
            String loginUrl = Uri.parse(Config.getServerUrl())
                    .buildUpon()
                    .path(SOAPUtils.getLoginUrl())
                    .build()
                    .toString();
            HttpPost httpPost = new HttpPost(loginUrl);
            httpPost.addHeader("SOAPAction", SOAPUtils.getActionLogin());
            httpPost.addHeader("Content-Type", Config.getContentXml());

            try {
                // Set the SOAP Entity
                String soapEnvelope = SOAPUtils.getLoginEnvelope(this.mUsername, this.mPassword);
                httpPost.setEntity(new StringEntity(soapEnvelope, Config.getCharsetUtf8()));

                // Execute the request
                httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Creates cookie in HttpCookieHandler for use in Volley request
            // TODO: Validate error request so that doesn't return true
            HttpCookie javaCookie = NetworkUtilities.getHttpCookie(
                    httpClient.getCookieStore().getCookies().get(0));
            java.net.URI javaUri = java.net.URI.create(Config.getServerUrl());
            // Add Cookie Manager
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            CookieManager defaultManager = (CookieManager) CookieHandler.getDefault();
            defaultManager.getCookieStore().add(javaUri, javaCookie);

            // Saves Cookie in SessionManager
            sessionManager.saveSessionCookie(javaCookie);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                getStoresList();
            } else {
                // TODO: Get the error from the SOAP request and show that message
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}

