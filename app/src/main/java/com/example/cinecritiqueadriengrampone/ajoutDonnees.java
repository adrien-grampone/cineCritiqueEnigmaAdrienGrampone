package com.example.cinecritiqueadriengrampone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ajoutDonnees extends AppCompatActivity {
    Database myDb;
    EditText editTextTitre, editTextDateHeure, editTextScenar, editTextReal, editTextMusique, editTextCrit, editTextId,editTextMail;
    Button buttonV;
    Button buttonRetour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_donnees);
        myDb = new Database(this);

        editTextTitre = (EditText) findViewById(R.id.editTextTitre);
        editTextDateHeure = (EditText) findViewById(R.id.editTextDateHeure);
        editTextScenar = (EditText) findViewById(R.id.editTextScenar);
        editTextReal = (EditText) findViewById(R.id.editTextReal);
        editTextMusique = (EditText) findViewById(R.id.editTextMusique);
        editTextCrit = (EditText) findViewById(R.id.editTextCrit);
        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextMail = (EditText) findViewById(R.id.editTextMail);
        buttonV = (Button) findViewById(R.id.buttonV);
        buttonRetour=(Button)findViewById(R.id.buttonRetour);
        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent (ajoutDonnees.this,MainActivity.class);
                startActivity(i);
            }
        });
        AjouterDonnees();


    }

    public void AjouterDonnees() {
        buttonV.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editTextTitre.getText().toString(), editTextDateHeure.getText().toString(), editTextScenar.getText().toString(), editTextReal.getText().toString(), editTextMusique.getText().toString(), editTextCrit.getText().toString());
                        if (isInserted == true) {
                            Toast.makeText(ajoutDonnees.this, "Données insérées", Toast.LENGTH_LONG).show();
                            if(editTextMail.getText().toString() != ""){
                                sendEmail();
                            }
                        } else {
                            Toast.makeText(ajoutDonnees.this, "Données non insérées", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"" + editTextMail.getText().toString()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "test");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Titre : " + editTextTitre.getText().toString() + "\n" + "Date : "  + editTextDateHeure.getText().toString() + "\n" + "Note scénario : " + editTextScenar.getText().toString() + "\n" + "Note réalisation : " + editTextReal.getText().toString() + "\n" + "Note musique : " + editTextMusique.getText().toString() + "\n" + "Critique : " + editTextCrit.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Envoie de l'email"));
            finish();
            Log.i("Email envoyé", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ajoutDonnees.this,
                    "Erreur", Toast.LENGTH_SHORT).show();
        }

    }

}

