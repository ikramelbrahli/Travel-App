package com.example.ps4.Models;

import android.net.Uri;

import com.google.firebase.firestore.DocumentId;

public class Ville {
    @DocumentId
    private String documentId;
    private String nom_ville ;
    private String nom_pays ;
    private String imageURI ;

    public Ville(String nom_ville, String nom_pays, String imageURI) {
        this.nom_ville = nom_ville;
        this.nom_pays = nom_pays;
        this.imageURI = imageURI;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURL) {
        this.imageURI = imageURL;
    }

    public Ville(String nom_ville, String nom_pays) {
        this.nom_ville = nom_ville;
        this.nom_pays = nom_pays;
    }
    public Ville(){

    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getNom_ville() {
        return nom_ville;
    }

    public void setNom_ville(String nom_ville) {
        this.nom_ville = nom_ville;
    }

    public String getNom_pays() {
        return nom_pays;
    }

    public void setNom_pays(String nom_pays) {
        this.nom_pays = nom_pays;
    }

    @Override
    public String toString() {
        return this.nom_pays + " " + this.nom_ville + " " + this.imageURI;
    }

    public String toStringImage(){return this.imageURI + " ";}
}
