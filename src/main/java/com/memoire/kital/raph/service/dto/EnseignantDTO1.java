package com.memoire.kital.raph.service.dto;

import java.io.Serializable;

public class EnseignantDTO1 implements Serializable {
    private String id;
    private String nom;
    private String prenom;

    //all constructor


    public EnseignantDTO1(String id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public EnseignantDTO1() {
    }
    public EnseignantDTO1(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnseignantDTO1)) {
            return false;
        }

        return id != null && id.equals(((EnseignantDTO1) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnseignantDTO1{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            "}";
    }
}
