package com.example.pierreleveille.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.util.List;

public class EnrActivity extends AppCompatActivity {
    EditText eID;
    EditText eMDP;
    EditText eNom;
    EditText ePrenom;
    EditText eWelcomeText;
    EditText eMatricule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enr);
        eID = (EditText) findViewById(R.id.idText);
        eMDP = (EditText) findViewById(R.id.mdpText);
        eNom = (EditText) findViewById(R.id.nomText);
        ePrenom = (EditText) findViewById(R.id.prenomText);
        eWelcomeText = (EditText) findViewById(R.id.welcomeText);
        eMatricule = (EditText) findViewById(R.id.matriculeText);


        Button button2 = (Button) findViewById(R.id.inscButton);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String id = eID.getText().toString();
                String mdp = eMDP.getText().toString();
                String nom = eNom.getText().toString();
                String prenom = ePrenom.getText().toString();
                String welcometext = eWelcomeText.getText().toString();
                String matricule = eMatricule.getText().toString();

                if(id=="" || mdp=="" || nom=="" || prenom=="" || welcometext=="" || matricule=="" )
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Champs vides, veuillez les remplir!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else {

                    inscriptionAPI(id,mdp,matricule,nom,prenom,welcometext);
                    //Intent myIntent = new Intent(view.getContext(), ChatActivity.class);
                    //startActivity(myIntent);
                }
            }
        });


    }

    protected void inscriptionAPI(String eID,String eMDP, String eMatricule,String eNom,String ePrenom,String eWelcomeText){
        Log.i("inscriptionAPI","dans la fonction");
        String serviceName = "register";

        JSONObject obj = new JSONObject();

// Les paramètres
        try {
            obj.put("no", eMatricule);
            obj.put("firstName", ePrenom);
            obj.put("lastName", eNom);
            obj.put("username", eID);
            obj.put("password", toMD5(eMDP));
            obj.put("welcomeText", eWelcomeText);
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
