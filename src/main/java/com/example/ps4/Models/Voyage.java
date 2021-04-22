package com.example.ps4.Models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;


public class Voyage {
    @DocumentId
    private String documentId;
    private int budget ;
    private String destination ;
    private String nom_voyage ;
    private String type_voyage ;
    private Date date_debut ;
    private Date date_fin ;

    public Voyage(String documentId, int budget, String destination, String nom_voyage, String type_voyage, Date date_debut, Date date_fin) {
        this.documentId = documentId;
        this.budget = budget;
        this.destination = destination;
        this.nom_voyage = nom_voyage;
        this.type_voyage = type_voyage;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }


    public Voyage(String destination1, String nom_voyage1, String type_voyage1, int budget1) {
        this.budget = budget;
        this.destination = destination;
        this.nom_voyage = nom_voyage;
        this.type_voyage = type_voyage;
    }

    public Voyage(String nom_voyage1, int budget1) {
        this.budget = budget;
        this.nom_voyage = nom_voyage;
    }

    public Voyage() {

    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getNom_voyage() {
        return nom_voyage;
    }

    public void setNom_voyage(String nom_voyage) {
        this.nom_voyage = nom_voyage;
    }

    public String getType_voyage() {
        return type_voyage;
    }

    public void setType_voyage(String type_voyage) {
        this.type_voyage = type_voyage;
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

    @Override
    public String toString() {
        return "Voyage{" +

                ", budget=" + budget +

                ", nom_voyage='" + nom_voyage + '\'' +

                '}';
    }
}

