package com.memoire.kital.raph.restClient;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class NiveauClient implements Serializable {
    private String id;
    private String nom;

    private Set<MatiereClient> matieres = new HashSet<>();

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

    public Set<MatiereClient> getMatieres() {
        return matieres;
    }

    public void setMatieres(Set<MatiereClient> matieres) {
        this.matieres = matieres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NiveauClient)) {
            return false;
        }

        return id != null && id.equals(((NiveauClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NiveauDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", matieres='" + getMatieres() + "'" +
            "}";
    }
}
