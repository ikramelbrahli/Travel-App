package com.example.ps4.Models;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class Reservation_form {

    // TODO: 2.1 Defining the Data Model

    @DocumentId
    private String documentId;
    private Date date_debut;
    private Date date_fin;
    private String heure_debut;
    private String heure_fin;
    private int prix;

    public Reservation_form() {}
    public Reservation_form(Date date_debut, Date date_fin, String heure_debut, String heure_fin, int Prix) {
        this.date_debut=date_debut;
        this.date_fin=date_fin;
        this.heure_debut=heure_debut;
        this.heure_fin=heure_fin;
        this.prix=prix;
    }

    public Reservation_form(int i, int i1, String heure_debut, String heure_fin, int prix) {
    }


    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }

    public String getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(String heure_fin) {
        this.heure_fin = heure_fin;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}
