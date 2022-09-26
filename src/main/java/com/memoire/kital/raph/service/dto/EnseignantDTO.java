package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Enseignant} entity.
 */
public class EnseignantDTO implements Serializable {

    private String id;

    @NotNull
    @Size(min = 3, max = 10)
    private String nom;

    @NotNull
    @Size(min = 3, max = 20)
    private String prenom;


    @Lob
    private String adresse;

    @NotNull
    @Size(min = 7, max = 9)
    private String telephone;

    @NotNull
    private String email;


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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnseignantDTO)) {
            return false;
        }

        return id != null && id.equals(((EnseignantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnseignantDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
