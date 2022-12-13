package com.memoire.kital.raph.restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClasseClient {
    private String id;
    @NotNull
    @Size(min = 4, max = 50)
    private String nom;

    @NotNull
    @Size(min = 5, max = 50)
    private String mensualite;

    //private String niveauId;

    private NiveauClient niveauDTO;

    public NiveauClient getNiveauClient() {
        return niveauDTO;
    }

    public void setNiveauClient(NiveauClient niveauDTO) {
        this.niveauDTO = niveauDTO;
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

    public String getMensualite() {
        return mensualite;
    }

    public void setMensualite(String mensualite) {
        this.mensualite = mensualite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClasseClient)) {
            return false;
        }

        return id != null && id.equals(((ClasseClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasseDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", mensualite='" + getMensualite() + "'" +
            ", niveauClient=" + getNiveauClient() +
            "}";
    }

}
