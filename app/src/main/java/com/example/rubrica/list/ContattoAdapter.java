package com.example.rubrica.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rubrica.R;
import com.example.rubrica.bean.Contatto;
import com.example.rubrica.list.viewholder.ContattoViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ContattoAdapter extends RecyclerView.Adapter<ContattoViewHolder> {

    private List<Contatto> contattos;
    //private List<Contatto> mContattosFiltered;

    public ContattoAdapter(List<Contatto> contattos) {
        this.contattos = contattos;
    }

    @NonNull
    @Override
    public ContattoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContattoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_contatto,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContattoViewHolder holder, int position) {
        Contatto c = contattos.get(position);
        holder.setContatto(c);
    }

    @Override
    public int getItemCount() {
        return contattos.size();
    }

}
