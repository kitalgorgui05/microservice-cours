package com.memoire.kital.raph.restClient;

import java.io.Serializable;

public class EleveClient implements Serializable {
    private String id;
    private String prenom;
    private String nom;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EleveClient)) {
            return false;
        }

        return id != null && id.equals(((EleveClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EleveClient{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() +
            "}";
    }
}
