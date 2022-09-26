package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.MatiereClient;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Cours} entity.
 */
public class CoursDTO implements Serializable {

    private String id;

    @NotNull
    @Size(min = 2, max = 255)
    private String idMatiere;
    private MatiereClient matiereClient;
    @NotNull
    @Size(min = 2, max = 255)
    private String idClasse;
    private ClasseClient classeClient;
    @NotNull
    @Size(min = 2, max = 255)
    private String idAnnee;

    private AnneeClient anneeClient;

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

    private String enseignantId;

    private String horaireId;

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

    public String getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(String enseignantId) {
        this.enseignantId = enseignantId;
    }

    public String getHoraireId() {
        return horaireId;
    }

    public void setHoraireId(String horaireId) {
        this.horaireId = horaireId;
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
            ", idClasse='" + getIdClasse() + "'" +
            ", idAnnee='" + getIdAnnee() + "'" +
            ", enseignantId=" + getEnseignantId() +
            ", horaireId=" + getHoraireId() +
            "}";
    }
}
