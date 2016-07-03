package me.fallblank.managingdependencies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.button) Button mButton;
    @Bind(R.id.text) TextView mTextView;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
    }


    @OnClick(R.id.button) void onButtonClicked() {
        StringRequest request = new StringRequest(
            "http://www.baidu.com",
            new Response.Listener<String>() {
                @Override public void onResponse(String s) {
                    String subString = s.substring(0, 200);
                    mTextView.setText(subString);
                }
            },
            new Response.ErrorListener() {

            @Override public void onErrorResponse(VolleyError volleyError) {
                mTextView.setText("Can't access to baidu");
            }
        });
        queue.add(request);
    }

}
