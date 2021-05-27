package com.example.rubrica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rubrica.DB.DBManager;
import com.example.rubrica.bean.Contatto;

public class CreaContattoActivity extends AppCompatActivity {
    ImageView imageViewFotoContatto, imageViewBack, imageViewSave, imageViewQr;
    EditText editTextNome, editTextCognome, editTextMobile, editTextCasa, editTextEmail, editTextSkype, editTextNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifica_contatto);
        allBindings();
        allListeners();
    }

    private void allBindings() {
        imageViewFotoContatto = findViewById(R.id.imageViewFotoContatto);
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewSave = findViewById(R.id.imageViewSave);
        imageViewQr = findViewById(R.id.imageViewQr);
        editTextNome = findViewById(R.id.editTextNome);
        editTextCognome = findViewById(R.id.editTextCognome);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextCasa = findViewById(R.id.editTextCasa);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSkype = findViewById(R.id.editTextSkype);
        editTextNote = findViewById(R.id.editTextNote);
    }

    private void allListeners() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreaContattoActivity.this,RubricaActivity.class));
                finish();
            }
        });

        imageViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contatto c = new Contatto();
                c.setNome(editTextNome.getText().toString());
                c.setCognome(editTextCognome.getText().toString());
                c.setIndirizzoMail(editTextEmail.getText().toString());
                c.setNumeroCellulare(editTextMobile.getText().toString());
                c.setNumeroCasa(editTextCasa.getText().toString());
                c.setSkype(editTextSkype.getText().toString());
                c.setNote(editTextNote.getText().toString());
                DBManager db = DBManager.getInstance(getApplicationContext());
                db.getContattoDAO().insertContatto(c);
                Toast.makeText(getApplicationContext(),"Contatto aggiunto",Toast.LENGTH_LONG).show();
                Log.i("myDebug","contatto aggiunto");
                startActivity(new Intent(CreaContattoActivity.this, RubricaActivity.class));
            }
        });

        imageViewQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == imageViewQr.getId()) {
                    Intent intent = new Intent(CreaContattoActivity.this, QrActivity.class);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                            ActivityCompat.checkSelfPermission(CreaContattoActivity.this, Manifest.permission.CAMERA) !=
                                    PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                        return;
                    }
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int ris : grantResults) {
            if (ris != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Senza permessi ti attacchi", Toast.LENGTH_SHORT).show();
            }
        }
    }

}