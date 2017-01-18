package ninja.paranoidandroid.fcmappserver;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anton on 16.01.17.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {



    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        // Get updated InstanceID token.
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FIIS", "Refreshed token: " + refreshedToken);

        //here you have to send registration token to appServer.
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.fcm_pref), MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.fcm_token), refreshedToken) ;
        editor.commit();



    }
}
