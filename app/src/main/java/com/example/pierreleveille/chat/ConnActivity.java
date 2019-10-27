package com.example.pierreleveille.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.security.MessageDigest;

public class ConnActivity extends AppCompatActivity {

    EditText eID;
    EditText eMDP;
    String connexionkey;

    @Override
    protected void onPause() {

        // hide the keyboard in order to avoid getTextBeforeCursor on inactive InputConnection
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(eID.getWindowToken(), 0);

        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn);
        final SharedPreferences pref = getSharedPreferences("session", Context.MODE_PRIVATE);

        eID = (EditText) findViewById(R.id.idTxt);
        eMDP = (EditText) findViewById(R.id.mdpTxt);

        Button button3 = (Button) findViewById(R.id.connButton);

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String id = eID.getText().toString();
                String mdp = eMDP.getText().toString();
                Log.i("Button",id);
                Log.i("Button",mdp);


                if(id=="" || mdp=="")
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Champs vides, veuillez les remplir!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else {
                    connexion(id,mdp);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent myIntent = new Intent(view.getContext(), ChatActivity.class);
                    startActivity(myIntent);
                    Log.i("Preference bouton",pref.getString("connkey",""));

                    /*
                    else{
                        Context context = getApplicationContext();
                        CharSequence text = "Mauvais identifiant ou mot de passe, veuillez réessayer!";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } */

                }
            }
        });


    }

    protected void connexion(String eID,String eMDP){
        Log.i("Connexion", "dans la fonction connexion");
        String serviceName = "login";
        JSONObject obj = new JSONObject();
        Log.i("Button",eID);
        Log.i("Button",eMDP);

// Les paramètres
        try {

            obj.put("username", eID);
            obj.put("password", toMD5(eMDP));

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
                        Log.i("Enregistrement", response); // Le retour

                        String key = response.replace("\"", "");
                        //connexionkey=response;
                        SharedPreferences pref = getSharedPreferences("session", Context.MODE_PRIVATE);
                        pref.edit().putString("connkey", key).commit();
                        Log.i("Preferences connexion",pref.getString("connkey", null));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Enregistrement", "Erreur...");
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
}

