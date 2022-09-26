package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.MatiereClient;
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
 * A Cours.
 */
@Entity
@Table(name = "cours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cours implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "id_matiere", length = 255, nullable = false)
    private String idMatiere;

    @Transient
    private MatiereClient matiereClient;
    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "id_classe", length = 255, nullable = false)
    private String idClasse;

    @Transient
    private ClasseClient classeClient;

    public MatiereClient getMatiereClient() {
        return matiereClient;
    }

    public void setMatiereClient(MatiereClient matiereClient) {
        this.matiereClient = matiereClient;
    }

    public ClasseClient getClasseClient() {
        return classeClient;
    }

    public void setClasseClient(ClasseClient classeClient) {
        this.classeClient = classeClient;
    }

    public AnneeClient getAnneeClient() {
        return anneeClient;
    }

    public void setAnneeClient(AnneeClient anneeClient) {
        this.anneeClient = anneeClient;
    }

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "id_annee", length = 255, nullable = false)
    private String idAnnee;

    @Transient
    private AnneeClient anneeClient;

    @OneToMany(mappedBy = "cours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Absence> absences = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "cours", allowSetters = true)
    private Enseignant enseignant;

    @ManyToOne
    @JsonIgnoreProperties(value = "cours", allowSetters = true)
    private Horaire horaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMatiere() {
        return idMatiere;
    }

    public Cours idMatiere(String idMatiere) {
        this.idMatiere = idMatiere;
        return this;
    }

    public void setIdMatiere(String idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getIdClasse() {
        return idClasse;
    }

    public Cours idClasse(String idClasse) {
        this.idClasse = idClasse;
        return this;
    }

    public void setIdClasse(String idClasse) {
        this.idClasse = idClasse;
    }

    public String getIdAnnee() {
        return idAnnee;
    }

    public Cours idAnnee(String idAnnee) {
        this.idAnnee = idAnnee;
        return this;
    }

    public void setIdAnnee(String idAnnee) {
        this.idAnnee = idAnnee;
    }

    public Set<Absence> getAbsences() {
        return absences;
    }

    public Cours absences(Set<Absence> absences) {
        this.absences = absences;
        return this;
    }

    public Cours addAbsence(Absence absence) {
        this.absences.add(absence);
        absence.setCours(this);
        return this;
    }

    public Cours removeAbsence(Absence absence) {
        this.absences.remove(absence);
        absence.setCours(null);
        return this;
    }

    public void setAbsences(Set<Absence> absences) {
        this.absences = absences;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public Cours enseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        return this;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Horaire getHoraire() {
        return horaire;
    }

    public Cours horaire(Horaire horaire) {
        this.horaire = horaire;
        return this;
    }

    public void setHoraire(Horaire horaire) {
        this.horaire = horaire;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cours)) {
            return false;
        }
        return id != null && id.equals(((Cours) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cours{" +
            "id=" + getId() +
            ", idMatiere='" + getIdMatiere() + "'" +
            ", idClasse='" + getIdClasse() + "'" +
            ", idAnnee='" + getIdAnnee() + "'" +
            "}";
    }
}
