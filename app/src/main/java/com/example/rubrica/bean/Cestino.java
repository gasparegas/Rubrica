package com.example.rubrica.bean;

import java.util.ArrayList;

public class Cestino {

    private static ArrayList<Contatto> contattiEliminati=new ArrayList<>();

    public static ArrayList<Contatto> getContattiEliminati() {
        return contattiEliminati;
    }

    public static void aggiungiContatto(Contatto c) {
        contattiEliminati.add(c);
    }

    public static Contatto recuperaContatto () {
        if (!contattiEliminati.isEmpty()) {
            Contatto c;
            c = contattiEliminati.get(contattiEliminati.size()-1);
            contattiEliminati.remove(c);
            return c;

        } else {
            return null;
        }

    }
}
