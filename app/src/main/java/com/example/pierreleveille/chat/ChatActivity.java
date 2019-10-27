package com.example.pierreleveille.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {
    ArrayList<MessageObj> listeMess;
    ArrayList<String> listeMembres;
    String key;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        pref = getSharedPreferences("session", Context.MODE_PRIVATE);
        key = pref.getString("connkey", null);
        Log.i("Preferences chat", key);
        recupMessages();
        recupMembres();
/*
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();

        for (MessageObj message : listeMess){
            MessageItemFragment fragment = MessageItemFragment.newInstance(message);

            transaction.add(R.id.fenetreChat,fragment);
        }
        transaction.commit();

        for (String membre : listeMembres){
            MembreItemFragment fragment1 = MembreItemFragment.newInstance(membre);

            transaction.add(R.id.membres,fragment1);
        }
        transaction.commit(); */


    }

    public ArrayList<MessageObj> recupMessages(){

        String serviceName = "read-messages";
        JSONObject obj = new JSONObject();

        try {

            obj.put("key", key);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.i("clé de session", key);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://apps-de-cours.com/web-chat/server/api/" +
                serviceName +
                "?data=" +
                obj.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("App read-messages", response); // Le retour

                        JSONArray jsonArr = null;
                        try {
                            jsonArr = new JSONArray(response);

                            for (int i = 0; i < jsonArr.length(); i++)
                            {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);
                                String nom = jsonObj.getString("nomUsager");
                                String message= jsonObj.getString("message");
                                String prive= jsonObj.getString("prive");
                                MessageObj obj= new MessageObj(nom,message,Boolean.parseBoolean(prive));
                                listeMess.add(obj);
                                System.out.println(jsonObj);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



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

        return listeMess;
    }

    public ArrayList<String> recupMembres(){

        String serviceName = "read-members";
        JSONObject obj = new JSONObject();

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

                        Log.i("App read-membres", response); // Le retour

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

        return listeMembres;
    }


}

