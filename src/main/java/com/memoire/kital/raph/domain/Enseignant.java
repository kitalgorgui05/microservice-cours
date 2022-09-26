package com.memoire.kital.raph.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Enseignant.
 */
@Entity
@Table(name = "enseignants")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Enseignant implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Size(min = 3, max = 10)
    @Column(name = "nom", length = 10, nullable = false)
    private String nom;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "prenom", length = 20, nullable = false)
    private String prenom;


    @Lob
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Size(min = 7, max = 9)
    @Column(name = "telephone", length = 9, nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "enseignant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Cours> cours = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Enseignant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Enseignant prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Enseignant adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public Enseignant telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Enseignant email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Cours> getCours() {
        return cours;
    }

    public Enseignant cours(Set<Cours> cours) {
        this.cours = cours;
        return this;
    }

    public Enseignant addCours(Cours cours) {
        this.cours.add(cours);
        cours.setEnseignant(this);
        return this;
    }

    public Enseignant removeCours(Cours cours) {
        this.cours.remove(cours);
        cours.setEnseignant(null);
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
        if (!(o instanceof Enseignant)) {
            return false;
        }
        return id != null && id.equals(((Enseignant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enseignant{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
