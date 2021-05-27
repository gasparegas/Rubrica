package com.example.rubrica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rubrica.DB.DBManager;
import com.example.rubrica.bean.Contatto;

public class ModificaContattoActivity extends AppCompatActivity {
    ImageView imageViewFotoContatto, imageViewBack, imageViewSave;
    EditText editTextNome, editTextCognome, editTextMobile, editTextCasa, editTextEmail, editTextSkype, editTextNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifica_contatto);
        allBindings();
        allTexting();
        allListeners();
    }

    private void allBindings() {
        imageViewFotoContatto = (ImageView) findViewById(R.id.imageViewFotoContatto);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        imageViewSave = (ImageView) findViewById(R.id.imageViewSave);
        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextCognome = (EditText) findViewById(R.id.editTextCognome);
        editTextMobile = (EditText) findViewById(R.id.editTextMobile);
        editTextCasa = (EditText) findViewById(R.id.editTextCasa);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSkype = (EditText) findViewById(R.id.editTextSkype);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
    }

    private void allTexting() {
        Intent intent = getIntent();
        int idCon = intent.getIntExtra("id",-1);
        DBManager dbManager = DBManager.getInstance(getApplicationContext());
        Contatto contatto = dbManager.getContattoDAO().getContatto(idCon);
        editTextNome.setText(contatto.getNome());
        editTextCognome.setText(contatto.getCognome());
        editTextMobile.setText(contatto.getNumeroCellulare());
        editTextCasa.setText(contatto.getNumeroCasa());
        editTextEmail.setText(contatto.getIndirizzoMail());
        editTextSkype.setText(contatto.getSkype());
        editTextNote.setText(contatto.getNote());
    }

    private void allListeners() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(ModificaContattoActivity.this,RubricaActivity.class));
               finish();
            }
        });

        imageViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                int idCon = intent.getIntExtra("id",-1);
                DBManager db = DBManager.getInstance(getApplicationContext());
                Contatto contatto = new Contatto();
                contatto.setIdContatto(idCon);
                contatto.setNome(editTextNome.getText().toString());
                contatto.setCognome(editTextCognome.getText().toString());
                contatto.setNumeroCellulare(editTextMobile.getText().toString());
                contatto.setNumeroCasa(editTextCasa.getText().toString());
                contatto.setIndirizzoMail(editTextEmail.getText().toString());
                contatto.setSkype(editTextSkype.getText().toString());
                contatto.setNote(editTextNote.getText().toString());
                db.getContattoDAO().insertContatto(contatto);
                Toast.makeText(getApplicationContext(),"Dati aggiornati",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ModificaContattoActivity.this, RubricaActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // vallo a sapere
    }
}
