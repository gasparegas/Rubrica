package com.example.rubrica.list.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rubrica.VisualizzaContattoActivity;
import com.example.rubrica.bean.Contatto;
import com.example.rubrica.R;

public class ContattoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView nomeCognome;
    Contatto c;

    public ContattoViewHolder(@NonNull View itemView) {
        super(itemView);
        nomeCognome = itemView.findViewById(R.id.nome_cognome);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), VisualizzaContattoActivity.class);
        intent.putExtra("id",c.getIdContatto());
        view.getContext().startActivity(intent);
    }

    public void setContatto(Contatto c) {
        this.c = c;
        nomeCognome.setText(this.c.getNome() + " " + this.c.getCognome());
    }
}
