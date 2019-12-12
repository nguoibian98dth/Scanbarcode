package com.example.scanbarcode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;


public class ResultActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;

    private String ipAddress, maLoai;
    Integer tilegiaban;
    Double giaNhapLK;
    TextView barCodeTV;
    TextView tenlkTV, namsxlkTV, nsxlkTV, gialkTV, xxlkTV, baohanhlkTV, sltonlkTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        barCodeTV = findViewById(R.id.barCode);

        tenlkTV = findViewById(R.id.tenlkTV);
        namsxlkTV = findViewById(R.id.namsxlkTV);
        nsxlkTV = findViewById(R.id.nsxlkTV);
        xxlkTV = findViewById(R.id.xxlkTV);
        baohanhlkTV = findViewById(R.id.bhlkTV);
        gialkTV = findViewById(R.id.gialkTV);
        sltonlkTV = findViewById(R.id.sltonlkTV);

        Intent intent = getIntent();
        ipAddress = intent.getStringExtra("ipAddress");
        barCodeTV.setText(intent.getStringExtra("code"));

        mRequestQueue = Volley.newRequestQueue(this);


        parseJSON(barCodeTV.getText().toString().trim());
    }

    private void parseJSON(String barcode) {


        String url = "http://"+ipAddress+"/api/linhkiens/"+barcode;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject object = new JSONObject(response.toString());

                            tenlkTV.setText(object.getString("tenLk"));
                            nsxlkTV.setText(object.getString("nhaSx"));
                            namsxlkTV.setText(object.getString("namSx"));
                            xxlkTV.setText(object.getString("xuatXu"));
                            sltonlkTV.setText(object.getString("soluong"));

                            maLoai = object.getString("loaiLk");
                            parseJSON2(maLoai);

                            giaNhapLK = object.getDouble("giaNhap")+(object.getDouble("giaNhap")*0.25);

                            NumberFormat formatter = new DecimalFormat("#,###");
                            String formattedNumber = formatter.format(giaNhapLK);
                            gialkTV.setText(formattedNumber+" VNĐ");
                            //parseJSON3();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    private void parseJSON2(String maloai) {


        String url = "http://"+ipAddress+"/api/loailks/"+maloai;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject object = new JSONObject(response.toString());
                            baohanhlkTV.setText(object.getString("thangBh"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    private void parseJSON3() {


        String url = "http://"+ipAddress+"/api/loailks/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayList al = new ArrayList();
                            for(int i=0;i<jsonArray.length();++i)
                            {
                                al.add(jsonArray.getString(i));
                            }

                            Toast.makeText(ResultActivity.this,al.toString(),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }


}
