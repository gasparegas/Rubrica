package com.example.rubrica.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Contatto {
    @PrimaryKey(autoGenerate = true)
    @JsonIgnore
    private int idContatto;
    private String nome, cognome, numeroCellulare, numeroCasa, indirizzoMail, skype, note;

    public Contatto() {}

    public int getIdContatto() {
        return idContatto;
    }

    public void setIdContatto(int id) {
        this.idContatto = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNumeroCellulare() {
        return numeroCellulare;
    }

    public void setNumeroCellulare(String numeroCellulare) {
        this.numeroCellulare = numeroCellulare;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getIndirizzoMail() {
        return indirizzoMail;
    }

    public void setIndirizzoMail(String indirizzoMail) {
        this.indirizzoMail = indirizzoMail;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Contatto{" +
                "id=" + idContatto +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", numeroCellulare='" + numeroCellulare + '\'' +
                ", numeroCasa='" + numeroCasa + '\'' +
                ", indirizzoMail='" + indirizzoMail + '\'' +
                ", skype='" + skype + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
