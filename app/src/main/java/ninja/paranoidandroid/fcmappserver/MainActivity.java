package ninja.paranoidandroid.fcmappserver;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button mRegTokenButton;
    private String insertUrl = "http://192.168.0.103/fcm_appServer/fcm_insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initUI();
        setmRegTokenButtonOnClickListener();

    }

    private void initUI(){

        mRegTokenButton = (Button) findViewById(R.id.b_activity_main_reg);

    }

    private void setmRegTokenButtonOnClickListener(){

        mRegTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.fcm_pref), MODE_APPEND);
                final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                final String fcmToken = sharedPreferences.getString(getString(R.string.fcm_token), "");
                StringRequest stringRequest = new StringRequest(Request.Method.POST, insertUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("MainActivity", "onResponce(), result is: " + response);
                                requestQueue.stop();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Log.i("MainActivity", "getParams(), token is: " );
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("fcm_token", fcmToken);

                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });

    }

}
