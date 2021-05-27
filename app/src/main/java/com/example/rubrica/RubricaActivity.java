package com.example.rubrica;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rubrica.DB.DBManager;
import com.example.rubrica.bean.Cestino;
import com.example.rubrica.bean.Contatto;
import com.example.rubrica.list.ContattoAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class RubricaActivity extends AppCompatActivity implements SensorEventListener {
    ImageView imageViewBack, imageViewAggiungiContatto;
    RecyclerView recyclerViewRubrica;
    SearchView searchViewContatti;
    float last_x,last_y,last_z;
    CoordinatorLayout coordinator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rubrica);

        //------------------------SENSORE ACCELEROMETRO--------------------------------------------
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        //----------------------------------------------------------------------------------------
        allBindings();
        allListeners();
        DBManager db = DBManager.getInstance(getApplicationContext());
        ContattoAdapter adp = new ContattoAdapter(db.getContattoDAO().getContatti());
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewRubrica.setAdapter(adp);
        recyclerViewRubrica.setLayoutManager(llm);

        //---------------------------------------SEARCH VIEW---------------------------------------
    }

    private void allBindings() {
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewAggiungiContatto = findViewById(R.id.imageViewAggiungiContatto);
        recyclerViewRubrica =  findViewById(R.id.recyclerViewRubrica);
        searchViewContatti = findViewById(R.id.searchViewContatti);
        coordinator = findViewById(R.id.coordinator);
    }

    private void allListeners(){
        imageViewAggiungiContatto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RubricaActivity.this,CreaContattoActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        aggiornaListaContatti();
    }

    private void aggiornaListaContatti() {
        DBManager db = DBManager.getInstance(getApplicationContext());
        List<Contatto> contatti = db.getContattoDAO().getContatti();
        ContattoAdapter adapter = new ContattoAdapter(contatti);
        recyclerViewRubrica.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewRubrica.setLayoutManager(manager);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        Log.e("MyLogNew", "x: " + x + " y: " + y + "z: " + z);
        if (last_y == 0.0F && last_z == 0.0f && last_x == 0.0f) {
            last_x = x;
            last_y = y;
            last_z = z;
            return;
        }
        float resY = y > last_y ? y - last_y : last_y - y;
        float resX = x > last_x ? x - last_x : last_x - x;
        float resZ = z > last_z ? z - last_z : last_z - z;
        Log.e("MyLogC", "resY: " + resY + "resX: " + resX + "resZ: " + resZ);
        float resTot = resX + resY + resZ;
        try {
            if (!Cestino.getContattiEliminati().isEmpty()) {
                if (resTot > 10 || resTot < -10) {
                    AlertDialog dialog = new AlertDialog.Builder(this, R.style.ThemeOverlay_AppCompat_Dialog_Alert)
                            .setTitle("Recupero Contatto")
                            .setMessage("Vuoi recuperare l'ultimo contatto eliminato?")
                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ArrayList<Contatto> contattiEliminati = Cestino.getContattiEliminati();
                                    Log.e("MyLogBasta", "I contatti eliminati sono: " + contattiEliminati);
                                    DBManager dbManager = DBManager.getInstance(getApplicationContext());
                                    dbManager.getContattoDAO().insertContatto(Cestino.recuperaContatto());
                                    //Toast.makeText(getApplicationContext(), "Contatto recuperato", Toast.LENGTH_LONG).show();
                                    mostraSnackbar("Contatto recuperato!");
                                    finish();
                                }
                            }).create();
                    dialog.show();

                }
            }
        } catch(Exception e){
            Log.e("Eccezione", e.getMessage());
        }

        last_x = x;
        last_y = y;
        last_z = z;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void mostraSnackbar(String messaggio) {
        final Snackbar bar = Snackbar.make(coordinator, messaggio, Snackbar.LENGTH_INDEFINITE);

        TextView tv = bar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        //tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setAllCaps(true);
        tv.setTextColor(Color.YELLOW);

        bar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.dismiss();
            }
        });

        bar.show();
    }

}
