package com.memoire.kital.raph.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Horaire.
 */
@Entity
@Table(name = "horaires")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Horaire implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Column(name = "horaire", nullable = false)
    private Instant horaire;

    @OneToMany(mappedBy = "horaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Cours> cours = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getHoraire() {
        return horaire;
    }

    public Horaire horaire(Instant horaire) {
        this.horaire = horaire;
        return this;
    }

    public void setHoraire(Instant horaire) {
        this.horaire = horaire;
    }

    public Set<Cours> getCours() {
        return cours;
    }

    public Horaire cours(Set<Cours> cours) {
        this.cours = cours;
        return this;
    }

    public Horaire addCours(Cours cours) {
        this.cours.add(cours);
        cours.setHoraire(this);
        return this;
    }

    public Horaire removeCours(Cours cours) {
        this.cours.remove(cours);
        cours.setHoraire(null);
        return this;
    }

    public void setCours(Set<Cours> cours) {
        this.cours = cours;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Horaire)) {
            return false;
        }
        return id != null && id.equals(((Horaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Horaire{" +
            "id=" + getId() +
            ", horaire='" + getHoraire() + "'" +
            "}";
    }
}
