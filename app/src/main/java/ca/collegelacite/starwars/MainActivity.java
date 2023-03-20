package ca.collegelacite.starwars;


import static ca.collegelacite.starwars.Personnage.lireFichier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // ListView <---> adaptateur <---> listeDePersonnages
    private ArrayAdapter<Personnage> adapter;
    // Source de données pour l'adaptateur du ListView
    private ListView listView;
    private ImageView imageView;
    private TextView nom;
    private TextView description;
    private ArrayList<Personnage> listeDePersonnages;
    private int resourceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Données source de l'adaptateur
        listeDePersonnages = lireFichier("personnages.json", this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        listView = findViewById(R.id.listView);
        imageView = findViewById(R.id.imageView);
        nom = findViewById(R.id.nom);
        description = findViewById(R.id.description);
        resourceId = getResources().getIdentifier(listeDePersonnages.get(0).getPhoto(), "drawable", getPackageName());
        Picasso.get().load(resourceId).into(imageView);
        nom.setText(listeDePersonnages.get(0).getNom());
        description.setText(listeDePersonnages.get(0).getDescriptif());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listeDePersonnages);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                resourceId = getResources().getIdentifier(listeDePersonnages.get(i).getPhoto(), "drawable", getPackageName());
                Picasso.get().load(resourceId).into(imageView);
                nom.setText(listeDePersonnages.get(i).getPhoto());
                description.setText(listeDePersonnages.get(i).getDescriptif());
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(listeDePersonnages.get(position).getWikiUrl());

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
                return true;
            }
        });

    }
}