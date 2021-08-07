package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String url="https://run.mocky.io/v3/0e97da07-7e56-40f4-ad58-77a4d860062d";
    RecyclerView recyclerView;
    private ArrayList<Article> productList;
    private ArticlesAdaptor adaptor;
    ArrayList<Article> articles;
    EditText searchProductEt;
    ImageButton filterProductBtn;
    TextView filteredProductsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchProductEt=findViewById(R.id.searchProductEt);
        filterProductBtn=findViewById(R.id.filterProductBtn);
        filteredProductsTv=findViewById(R.id.filteredProductsTv);

        adaptor=new ArticlesAdaptor();
        recyclerView.setAdapter(adaptor);
        articles=new ArrayList<>();
        getData();

        searchProductEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
                    adaptor.getFilter().filter(s);
                   // getData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getData1(CharSequence s) {

    }

    public void getData(){
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Article article = new Article();

                        article.setTitle(jsonObject.getString("title"));
                        article.setBody(jsonObject.getString("retailPrice"));
                        article.setImage(jsonObject.getString("imageUrl"));
                        articles.add(article);
                    }
                }
                catch (JSONException e){
                    Toast.makeText(MainActivity.this, "JSON is not valid"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                adaptor.setData(articles);
                adaptor.notifyDataSetChanged();

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error occurred"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}