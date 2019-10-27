package com.example.pierreleveille.chat;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

public class MessageObj implements Serializable {
        private String nomUsager;
        private String message;
        private boolean prive;

        public MessageObj(String nom, String mess, boolean priv) {
            this.nomUsager=nom;
            this.message=mess;
            this.prive=priv;
        }

        public String getMessage() {
            return message;
        }

        public String getNom() {
            return nomUsager;
        }

        public boolean getPrive() {
            return prive;
        }
}


