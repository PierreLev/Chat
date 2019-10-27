package com.example.pierreleveille.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.security.MessageDigest;

public class MenuActivity extends AppCompatActivity {
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Connexion au serveur
       // connexionAPI();

        Button button = (Button) findViewById(R.id.enrButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), EnrActivity.class);
                startActivity(myIntent);
            }
        });
        Button button1 = (Button) findViewById(R.id.connButton);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ConnActivity.class);
                startActivity(myIntent);
            }
        });
        Button button2 = (Button) findViewById(R.id.decButton);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deconnexionServeur();
            }
        });

    }
/*
    void connexionAPI(){
        String serviceName = "login";


        JSONObject obj = new JSONObject();

// Les paramètres
        try {
            obj.put("username", "guest");
            obj.put("password", toMD5("guest"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://apps-de-cours.com/web-chat/server/api/" +
                serviceName +
                "?data=" +
                obj.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String key = response.replace("\"", "");
                        Log.i("App", key); // Le retour
                        pref = getSharedPreferences("session", Context.MODE_PRIVATE);
                        pref.edit().putString("sessionkey",key).commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("App", "Erreur...");
                    }
                }
        );
        queue.add(stringRequest);
    }

    private String toMD5(String word) {
        String result = "ERR";

        try {
            byte[] bytesOfMessage = word.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            StringBuffer sb = new StringBuffer();
            for (byte b : thedigest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            result = sb.toString();
        }
        catch (Exception e) {}

        return result;
    }
*/
    void deconnexionServeur(){
        String serviceName = "logout";


        JSONObject obj = new JSONObject();

        pref = getSharedPreferences("session", Context.MODE_PRIVATE);
        String key = pref.getString("sessionkey", null);
// Les paramètres
        try {
            obj.put("key", key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://apps-de-cours.com/web-chat/server/api/" +
                serviceName +
                "?data=" +
                obj.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String key = response.replace("\"", "");
                        Log.i("App", key); // Le retour
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("App", "Erreur...");
                    }
                }
        );
        queue.add(stringRequest);
    }
}
