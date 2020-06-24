package com.example.cinecritiqueadriengrampone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Database myDb;
    EditText editTextTitre,editTextDateHeure,editTextScenar,editTextReal,editTextMusique,editTextCrit,editTextId;
    Button buttonV;
    Button buttonVoirDonnees;
    Button buttonUp;
    Button buttonSupp;
    Button buttonAjouter;
    Button buttonSupprimer;
     boolean testClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new Database(this);

        editTextTitre=(EditText)findViewById(R.id.editTextTitre);
        editTextDateHeure=(EditText)findViewById(R.id.editTextDateHeure);
        editTextScenar=(EditText)findViewById(R.id.editTextScenar);
        editTextReal=(EditText)findViewById(R.id.editTextReal);
        editTextMusique=(EditText)findViewById(R.id.editTextMusique);
        editTextCrit=(EditText)findViewById(R.id.editTextCrit);
        editTextId=(EditText)findViewById(R.id.editTextId);
      //  buttonV=(Button)findViewById(R.id.buttonV);
        buttonVoirDonnees=(Button)findViewById((R.id.buttonVoirDonnees));
        buttonUp=(Button)findViewById(R.id.buttonUp);
        buttonSupp=(Button)findViewById(R.id.buttonDelete);
        buttonAjouter=(Button)findViewById(R.id.buttonAjouterDonnees);

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent (MainActivity.this,ajoutDonnees.class);
                startActivity(i);
            }
        });



        //AjouterDonnees();
        voirDonnees();
        ModifierDonnees();
        SupprimerDonnee();


    }



    public void SupprimerDonnee()
    {
        buttonSupp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows=myDb.SupprimerDonnee(editTextId.getText().toString());
                        if(deletedRows > 0)
                        {
                            Toast.makeText(MainActivity.this,"Donnée supprimée",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Donnée non supprimée",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void ModifierDonnees()
    {
        buttonUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean isUpdate=myDb.ModifierDonnees(editTextId.getText().toString(),
                        editTextTitre.getText().toString(),
                        editTextDateHeure.getText().toString(),
                        editTextScenar.getText().toString(),
                        editTextReal.getText().toString(),
                        editTextMusique.getText().toString(),
                        editTextCrit.getText().toString());
                if(isUpdate==true)
                {
                    Toast.makeText(MainActivity.this,"Données modifiées",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Données non modifiées",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

 /*   public void AjouterDonnees()
    {
        buttonV.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                       boolean isInserted = myDb.insertData(editTextTitre.getText().toString(),editTextDateHeure.getText().toString(),editTextScenar.getText().toString(),editTextReal.getText().toString(),editTextMusique.getText().toString(),editTextCrit.getText().toString());
                       if(isInserted==true)
                       {
                           Toast.makeText(MainActivity.this,"Données insérées",Toast.LENGTH_LONG).show();
                       }
                       else
                       {
                           Toast.makeText(MainActivity.this,"Données non insérées",Toast.LENGTH_LONG).show();
                       }
                    }
                }
        );
    }
*/
    public void voirDonnees()
    {
        buttonVoirDonnees.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res =myDb.getAllData();
                        if(res.getCount()==0)
                        {
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext())
                        {
                            buffer.append("Id :"+res.getString(0)+"\n");
                            buffer.append("Titre :"+res.getString(1)+"\n");
                            buffer.append("Date et Heure :"+res.getString(2)+"\n");
                            buffer.append("Note scénario :"+res.getString(3)+"\n");
                            buffer.append("Note réalisation :"+res.getString(4)+"\n");
                            buffer.append("Note musique :"+res.getString(5)+"\n");
                            buffer.append("Description :"+res.getString(6)+"\n");
                        }
                        showMessage("Data",buffer.toString());

                    }
                }
        );
    }

    public void showMessage(String title, String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
