package com.example.rubrica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rubrica.DB.DBManager;
import com.example.rubrica.bean.Contatto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;

import java.util.List;

public class QrActivity extends AppCompatActivity implements BarcodeCallback {
    private BarcodeView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        preview = findViewById(R.id.preview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        preview.decodeContinuous(this);
        preview.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        preview.pause();
        preview.stopDecoding();
    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        preview.pause();
        preview.stopDecoding();
        // avrò letto il qr
        String risultato = result.toString();

        ObjectMapper mapper = new ObjectMapper();
        try {
            Contatto contatto = mapper.readValue(risultato, Contatto.class);

            DBManager db = DBManager.getInstance(getApplicationContext());
            Log.d("TestQR",risultato);
            db.getContattoDAO().insertContatto(contatto);

            Toast.makeText(getApplicationContext(),"Contatto aggiunto",Toast.LENGTH_LONG).show();

            Log.d("TestQR   ", contatto.toString());

            Log.i("myDebug","contatto aggiunto");
        } catch (JsonParseException e) {
            Toast.makeText(getApplicationContext(), "Non posso leggere quel QR", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Errore generico", Toast.LENGTH_LONG).show();
        }

        Log.d("QRLetto", risultato);

        // voglio fare un intent
        Toast.makeText(getApplicationContext(), risultato, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }
}