package com.memoire.kital.raph.service.dto;

import java.time.Instant;
import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;

public class HoraireDTO implements Serializable {

    private String id;
    private Instant heurDedut;

    private Instant heurFin;

    public HoraireDTO(String id, Instant heurDedut, Instant heurFin) {
        this.id = id;
        this.heurDedut = heurDedut;
        this.heurFin = heurFin;
    }

    public HoraireDTO(String id) {
        this.id = id;
    }

    public HoraireDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getHeurDedut() {
        return heurDedut;
    }

    public void setHeurDedut(Instant heurDedut) {
        this.heurDedut = heurDedut;
    }

    public Instant getHeurFin() {
        return heurFin;
    }

    public void setHeurFin(Instant heurFin) {
        this.heurFin = heurFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoraireDTO)) {
            return false;
        }

        return id != null && id.equals(((HoraireDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoraireDTO{" +
            "id=" + getId() +
            ", heurDebut='" + getHeurDedut() + "'" +
            ", heurFin='" + getHeurFin() + "'" +
            "}";
    }
}
