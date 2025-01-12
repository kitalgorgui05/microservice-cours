package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.MatiereClient;

import javax.validation.constraints.*;
import java.io.Serializable;

public class CoursDTO implements Serializable {
    private String id;
    private String idMatiere;
    private MatiereClient matiereClient;
    private String idClasse;
    private ClasseClient classeClient;
    private String idAnnee;
    private AnneeClient anneeClient;

    //private String horaireId;
    private EnseignantDTO1 enseignant;

    private HoraireDTO horaire;

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

    //private String enseignantId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(String idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(String idClasse) {
        this.idClasse = idClasse;
    }

    public String getIdAnnee() {
        return idAnnee;
    }

    public void setIdAnnee(String idAnnee) {
        this.idAnnee = idAnnee;
    }

    public EnseignantDTO1 getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(EnseignantDTO1 enseignant) {
        this.enseignant = enseignant;
    }

    public HoraireDTO getHoraire() {
        return horaire;
    }

    public void setHoraire(HoraireDTO horaire) {
        this.horaire = horaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoursDTO)) {
            return false;
        }

        return id != null && id.equals(((CoursDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoursDTO{" +
            "id=" + getId() +
            ", idMatiere='" + getIdMatiere() + "'" +
            ", matiereClient='" + getMatiereClient() + "'" +
            ", idClasse='" + getIdClasse() + "'" +
            ", classeClient='" + getClasseClient() + "'" +
            ", idAnnee='" + getIdAnnee() + "'" +
            ", anneeClient='" + getAnneeClient() + "'" +
            ", enseignant=" + getEnseignant()+"'" +
            ", horaire=" + getHoraire() +
            "}";
    }
}
