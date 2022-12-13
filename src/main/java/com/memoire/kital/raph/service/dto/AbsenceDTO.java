package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.restClient.EleveClient;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Absence} entity.
 */
public class AbsenceDTO implements Serializable {

    private String id;

    @NotNull
    @Size(min = 2, max = 255)
    private String idEleve;

    private EleveClient eleveClient;

    @Lob
    private String motif;

    @NotNull
    private Boolean etat;


    private String coursId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(String idEleve) {
        this.idEleve = idEleve;
    }


    public EleveClient getEleveClient() {
        return eleveClient;
    }

    public void setEleveClient(EleveClient eleveClient) {
        this.eleveClient = eleveClient;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Boolean isEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public String getCoursId() {
        return coursId;
    }

    public void setCoursId(String coursId) {
        this.coursId = coursId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbsenceDTO)) {
            return false;
        }

        return id != null && id.equals(((AbsenceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbsenceDTO{" +
            "id=" + getId() +
            ", idEleve='" + getIdEleve() + "'" +
            ", motif='" + getMotif() + "'" +
            ", etat='" + isEtat() + "'" +
            ", coursId=" + getCoursId() +
            "}";
    }
}
