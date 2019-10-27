package com.example.pierreleveille.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MembreItemFragment extends Fragment {
private String membre;

public MembreItemFragment(){

}

public static MembreItemFragment newInstance(String membre) {
    MembreItemFragment fragment = new MembreItemFragment();

    // Pour associer/ajouter le mail au fragment, on ne peut pas faire this.mail = mail...
    Bundle args = new Bundle();
    args.putSerializable("Membre", membre);
    fragment.setArguments(args);

    return fragment;
}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On va rechercher notre courriel, celui qui a été associé dans newInstance
        this.membre = (String)getArguments().getSerializable("Membre");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_membre,container,false);

        // Modifier la vue en fonction du courriel associé
        ((TextView)view.findViewById(R.id.membre)).setText(this.membre);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(inflater.getContext(), ChatActivity.class);
                intent.putExtra("Membre", membre); // Ajoute le courriel à l'intention, pour l'activité MailActivity
                startActivity(intent);

            }
        });

        return view;

    }

}