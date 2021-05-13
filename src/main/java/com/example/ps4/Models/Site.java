package com.example.ps4.Models;

import com.google.firebase.firestore.DocumentId;

public class Site {
    @DocumentId
    private String documentId;
    private String nom_hotel ;
    private String nom_ville ;
    private String imageURI ;

    public Site() {}
    public Site(String nom_hotel, String nom_ville, String imageURI) {
        this.nom_hotel = nom_hotel;
        this.nom_ville = nom_ville;
        this.imageURI = imageURI;
    }
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getNom_hotel() {
        return nom_hotel;
    }

    public void setNom_hotel(String nom_hotel) {
        this.nom_hotel = nom_hotel;
    }

    public String getNom_ville() {
        return nom_ville;
    }

    public void setNom_ville(String nom_ville) {
        this.nom_ville = nom_ville;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}

