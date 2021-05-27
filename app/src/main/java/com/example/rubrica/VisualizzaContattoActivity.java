package com.example.rubrica;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import com.example.rubrica.DB.DBManager;
import com.example.rubrica.bean.Cestino;
import com.example.rubrica.bean.Contatto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaContattoActivity extends AppCompatActivity {
    ImageView imageViewFotoContatto, imageViewMobile, imageViewCasa, imageViewEmail,
            imageViewSkype, imageViewBack, imageViewEdit, imageViewDelete;
    TextView textViewNome, textViewCognome, textViewMobile, textViewCasa, textViewEmail,
            textViewSkype, textViewNote;
    CoordinatorLayout coordinator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizza_contatto);
        allBindings();
        allListeners();
        allSettings();
    }

    private void allSettings(){
        Intent intent = getIntent();
        int idCon = intent.getIntExtra("id",-1);
        DBManager dbManager = DBManager.getInstance(getApplicationContext());
        Contatto contatto = dbManager.getContattoDAO().getContatto(idCon);

        ObjectMapper mapper = new ObjectMapper();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix;
        try{
            String contattoJson = mapper.writeValueAsString(contatto);
            bitMatrix = multiFormatWriter.encode(contattoJson, BarcodeFormat.QR_CODE, 300,300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewFotoContatto.setImageBitmap(bitmap);
            Log.d("TestQR", contattoJson);
        }catch (Exception e){
            Log.e("TestQR", e.getMessage());
        }
        //------------------------------------------------------------------------------------------
        if( idCon > 0 ) {
            textViewNome.setText(contatto.getNome());
            textViewCognome.setText(contatto.getCognome());
            textViewMobile.setText(contatto.getNumeroCellulare());
            textViewCasa.setText(contatto.getNumeroCasa());
            textViewEmail.setText(contatto.getIndirizzoMail());
            textViewSkype.setText(contatto.getSkype());
            textViewNote.setText(contatto.getNote());
        }
    }//

    private void allBindings() {
        imageViewFotoContatto = findViewById(R.id.imageViewFotoContatto);
        imageViewMobile = findViewById(R.id.imageViewMobile);
        imageViewCasa = findViewById(R.id.imageViewCasa);
        imageViewEmail = findViewById(R.id.imageViewEmail);
        imageViewSkype = findViewById(R.id.imageViewSkype);
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewEdit = findViewById(R.id.imageViewEdit);
        imageViewDelete = findViewById(R.id.imageViewDelete);
        registerForContextMenu(imageViewDelete);
        textViewNome = findViewById(R.id.textNome);
        textViewCognome = findViewById(R.id.textCognome);
        textViewMobile = findViewById(R.id.textMobile);
        textViewCasa = findViewById(R.id.textCasa);
        textViewEmail = findViewById(R.id.textEmail);
        textViewSkype = findViewById(R.id.textSkype);
        textViewNote = findViewById(R.id.textNote);
    }

    private void allListeners() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VisualizzaContattoActivity.this,RubricaActivity.class));
                finish();
            }
        });

        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                int idCon = intent.getIntExtra("id",-1);
                Intent intent1 = new Intent(VisualizzaContattoActivity.this, ModificaContattoActivity.class);
                intent1.putExtra("id",idCon);
                startActivity(intent1);
                finish();
            }
        });

        imageViewMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
                }
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textViewMobile.getText())));
                finish();
            }
        });

        imageViewCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                       != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
                }
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + textViewCasa.getText())));
                finish();
            }
        });

        imageViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"email"});
                startActivity(Intent.createChooser(intent,"invia email"));
                finish();
            }
        });

        imageViewSkype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/".concat(textViewSkype.getText().toString()));
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/".concat(textViewSkype.getText().toString()))));
                }
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_option, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case R.id.confermaDelete: {
                new AlertDialog.Builder(VisualizzaContattoActivity.this,R.style.ThemeOverlay_AppCompat_Dialog_Alert)
                .setTitle("Eliminazione Contatto")
                        .setMessage("Sei sicuro di voler procedere all'eliminazione?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = getIntent();
                                int idCon = intent.getIntExtra("id",-1);
                                Log.i("myDebug","" + idCon);
                                DBManager dbManager = DBManager.getInstance(getApplicationContext());
                                Contatto contatto =  dbManager.getContattoDAO().getContatto(idCon);
                                Cestino.aggiungiContatto(contatto);
                                Log.i("Tarantino","Contatti eliminati: " + Cestino.getContattiEliminati());
                                dbManager.getContattoDAO().deleteContatto(idCon);

                                Toast.makeText(getApplicationContext(),"Contatto rimosso",Toast.LENGTH_LONG).show();
                                Intent intentEliminato = new Intent(VisualizzaContattoActivity.this,RubricaActivity.class);
                                startActivity(intentEliminato);
                                finish();

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            case R.id.annullaDelete: {
                Toast.makeText(getApplicationContext(),"Operazione annullata",Toast.LENGTH_LONG).show();
                break;
            }
        }
        return true;
    }

}

