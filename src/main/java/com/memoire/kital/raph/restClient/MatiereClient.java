package com.memoire.kital.raph.restClient;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class MatiereClient implements Serializable {

    private String id;

    private String nom;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatiereClient)) {
            return false;
        }

        return id != null && id.equals(((MatiereClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MatiereClient{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
