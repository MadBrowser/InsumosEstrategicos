package cl.colabra.cvilches.insumosestrategicos.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.net.HttpCookie;
import java.util.HashMap;

import cl.colabra.cvilches.insumosestrategicos.LoginActivity;
import cl.colabra.cvilches.insumosestrategicos.R;

public class SessionManager {
    // Shared Preferences
    SharedPreferences mSharedPreferences;

    // Editor for Shared preferences
    SharedPreferences.Editor mEditor;

    // Context
    Activity mActivity;

    // Shared mSharedPreferences mode
    int PRIVATE_MODE = 0;

    private static final String TAG = "SGIE_SessionManager";

    // Shared mSharedPreferences file name
    private static final String PREF_NAME = "SGIEPref";

    // All Shared Preferences Keys
    private static final String IS_LOGGED_IN = "cl.colabra.IS_LOGGED_IN";

    // User name (make variable public to access from outside)
    public static final String KEY_USER_NAME = "cl.colabra.KEY_USER_NAME";

    // Auth value (make variable public to access from outside)
    public static final String KEY_PASSWORD = "cl.colabra.KEY_PASSWORD";

    // Auth cookie
    public static final String FED_AUTH = "cl.colabra.FED_AUTH";
    public static final String FED_AUTH_NAME = "FedAuth";

    // Constructor
    public SessionManager(Activity activity) {
        this.mActivity = activity;
        mSharedPreferences = this.mActivity.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();
    }

    /**
     * Stores login credentials in Shared prefs
     */
    public void createLoginSession(String name, String password) {
        // Storing login value as TRUE
        mEditor.putBoolean(IS_LOGGED_IN, true);

        // Storing name in mSharedPreferences
        mEditor.putString(KEY_USER_NAME, name);

        // Storing auth in mSharedPreferences
        mEditor.putString(KEY_PASSWORD, password);

        // commit changes
        mEditor.commit();
    }

    /**
     * checkLogin method wil check user login status
     * If false it will redirect user to LoginActivity
     * Else won't do anything
     */
    public boolean checkLogin() {
        // Check login status
        return this.isLoggedIn();
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        // User name
        user.put(KEY_USER_NAME, mSharedPreferences.getString(KEY_USER_NAME, null));

        // User auth
        user.put(KEY_PASSWORD, mSharedPreferences.getString(KEY_PASSWORD, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        mEditor.clear();
        mEditor.commit();

        // After logout redirect user to Login Activity
        Intent intent = new Intent(mActivity, LoginActivity.class);

        // Closing all the Activities and start in a new task
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Show success message
        Toast.makeText(mActivity, R.string.message_logout_successfully, Toast.LENGTH_LONG).show();

        // Staring Login Activity
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return mSharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void saveSessionCookie(HttpCookie cookie) {
        mEditor.putString(FED_AUTH, cookie.getValue());
        mEditor.apply();
    }

    public String getSessionCookie() {
        return mSharedPreferences.getString(FED_AUTH, "");
    }
}