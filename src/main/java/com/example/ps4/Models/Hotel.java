package com.example.ps4.Models;

import com.google.firebase.firestore.DocumentId;

public class Hotel extends Ville {
    @DocumentId
    private String document_id;
    private String nom_hotel ;
    private String description ;
    private String imageURI ;
    private int prix ;
    private String adresse ;

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Hotel() {}
    public Hotel(String nom_hotel , String imageURI) {
        this.nom_hotel = nom_hotel;

        this.imageURI = imageURI;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentId() {
        return document_id;
    }
    public void setDocumentId(String documentId) {
        this.document_id = documentId;
    }

    public String getNom_hotel() {
        return nom_hotel;
    }

    public void setNom_hotel(String nom_hotel) {
        this.nom_hotel = nom_hotel;
    }



    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }



    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}

