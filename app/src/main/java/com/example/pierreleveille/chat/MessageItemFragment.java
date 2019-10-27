package com.example.pierreleveille.chat;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MessageItemFragment extends android.support.v4.app.Fragment {
        private MessageObj mess;
        public MessageItemFragment() {

        }

        public static MessageItemFragment newInstance(MessageObj mess) {
            MessageItemFragment fragment = new MessageItemFragment();

            // Pour associer/ajouter le mail au fragment, on ne peut pas faire this.mail = mail...
            Bundle args = new Bundle();
            args.putSerializable("MessageObj", mess);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // On va rechercher notre courriel, celui qui a été associé dans newInstance
            this.mess = (MessageObj)getArguments().getSerializable("MessageObj");
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.activity_message,container,false);

            // Modifier la vue en fonction du courriel associé
            ((TextView)view.findViewById(R.id.message)).setText(this.mess.getMessage());
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                        Intent intent = new Intent(inflater.getContext(), ChatActivity.class);
                        intent.putExtra("Message", mess); // Ajoute le courriel à l'intention, pour l'activité MailActivity
                        startActivity(intent);

                }
            });

            return view;

        }

    }


