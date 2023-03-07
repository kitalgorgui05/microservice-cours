package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.restClient.EleveClient;

import java.io.Serializable;


public class AbsenceDTO implements Serializable {
    private String id;
    private String idEleve;
    private EleveClient eleveClient;
    private String motif;
    private Boolean etat;
    private CoursDTO1 cours;
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

    public CoursDTO1 getCours() {
        return cours;
    }

    public void setCours(CoursDTO1 cours) {
        this.cours = cours;
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

    public Boolean getEtat() {
        return etat;
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
            ", coursId=" +getCours() +
            "}";
    }
}
