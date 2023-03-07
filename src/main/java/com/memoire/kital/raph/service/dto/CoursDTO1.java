package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.MatiereClient;

import java.io.Serializable;

public class CoursDTO1 implements Serializable {
    private String id;
    private MatiereClient matiereClient;
    private ClasseClient classeClient;
    private AnneeClient anneeClient;
    private EnseignantDTO1 enseignant;
    private HoraireDTO horaire;

    public CoursDTO1(String id, MatiereClient matiereClient, ClasseClient classeClient, AnneeClient anneeClient, EnseignantDTO1 enseignant, HoraireDTO horaire) {
        this.id = id;
        this.matiereClient = matiereClient;
        this.classeClient = classeClient;
        this.anneeClient = anneeClient;
        this.enseignant = enseignant;
        this.horaire = horaire;
    }

    public CoursDTO1(String id) {
        this.id = id;
    }

    public CoursDTO1() {
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(o instanceof CoursDTO1)) {
            return false;
        }

        return id != null && id.equals(((CoursDTO1) o).id);
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
            ", matiereClient=" + getMatiereClient()+"'" +
            ", classeClient=" + getClasseClient()+"'" +
            ", AnneeClient=" + getAnneeClient()+"'" +
            ", enseignant=" + getEnseignant()+"'" +
            ", horaire=" + getHoraire() +
            "}";
    }
}
