package com.example.ps4.Models;

import com.google.firebase.firestore.DocumentId;


public class Reservation {

    // TODO: 2.1 Defining the Data Model

@DocumentId
    private String documentId;
    private String date_debut;
    private String endroitID ;
    private String date_fin;
    private String heure_debut;
    private String heure_fin;
    private int prix;
    private String voyageID ;
    private String destination ;
    private String hotel_name ;
    private String imageURI ;


    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getEndroitID() {
        return endroitID;
    }

    public void setEndroitID(String endroitID) {
        this.endroitID = endroitID;
    }

    public String getVoyageID() {
        return voyageID;
    }

    public void setVoyageID(String voyageID) {
        this.voyageID = voyageID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public Reservation() {}

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
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
