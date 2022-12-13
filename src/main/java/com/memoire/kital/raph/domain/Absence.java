package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.memoire.kital.raph.restClient.EleveClient;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Absence.
 */
@Entity
@Table(name = "absences")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Absence implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "id_eleve", length = 255, nullable = false)
    private String idEleve;

    @Transient
    private EleveClient eleveClient;
    @Lob
    @Column(name = "motif")
    private String motif;

    @NotNull
    @Column(name = "etat", nullable = false)
    private Boolean etat;

    @ManyToOne
    @JsonIgnoreProperties(value = "absences", allowSetters = true)
    private Cours cours;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEleve() {
        return idEleve;
    }

    public Absence idEleve(String idEleve) {
        this.idEleve = idEleve;
        return this;
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

    public Absence motif(String motif) {
        this.motif = motif;
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Boolean isEtat() {
        return etat;
    }

    public Absence etat(Boolean etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Cours getCours() {
        return cours;
    }

    public Absence cours(Cours cours) {
        this.cours = cours;
        return this;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Absence)) {
            return false;
        }
        return id != null && id.equals(((Absence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Absence{" +
            "id=" + getId() +
            ", idEleve='" + getIdEleve() + "'" +
            ", motif='" + getMotif() + "'" +
            ", etat='" + isEtat() + "'" +
            "}";
    }
}
