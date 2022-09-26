package com.memoire.kital.raph.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Horaire} entity.
 */
public class HoraireDTO implements Serializable {

    private String id;

    @NotNull
    private Instant horaire;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getHoraire() {
        return horaire;
    }

    public void setHoraire(Instant horaire) {
        this.horaire = horaire;
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
            ", horaire='" + getHoraire() + "'" +
            "}";
    }
}
