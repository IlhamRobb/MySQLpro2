package com.tanjung.mysqlpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tanjung.mysqlpro.adapter.TemanAdapter;
import com.tanjung.mysqlpro.database.Teman;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private TemanAdapter adapter;
    private ArrayList<Teman> temenArrayList = new ArrayList<>() ;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static String url_select = "http://10.0.2.2/umyTI/bacateman.php";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_TELPON = "telpon";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.floatingBtn);
        BacaData();
        adapter = new TemanAdapter(temenArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TambahTeman.class);
                startActivity(intent);
            }
        });
    }

        public void BacaData(){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest jArr = new JsonArrayRequest(url_select,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, response.toString());

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONArray obj = response.getJSONArray(i);
                            Teman item = new Teman();
                            item.setId(obj.getString(TAG_ID));
                            item.setNama(obj.getString(TAG_NAMA));
                            item.setTelpon(obj.getString(TAG_TELPON));

                            temenArrayList.add(item);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrosResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(MainActivity.this,"gagal",Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jArr);
        }
    }
