package com.memoire.kital.raph.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.memoire.kital.raph.domain.Cours} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.CoursResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cours?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CoursCriteria implements Serializable, Criteria {
    private StringFilter id;

    private StringFilter idMatiere;

    private StringFilter idClasse;

    private StringFilter idAnnee;

    private StringFilter absenceId;

    private StringFilter enseignantId;

    private StringFilter horaireId;

    public CoursCriteria() {
    }

    public CoursCriteria(CoursCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idMatiere = other.idMatiere == null ? null : other.idMatiere.copy();
        this.idClasse = other.idClasse == null ? null : other.idClasse.copy();
        this.idAnnee = other.idAnnee == null ? null : other.idAnnee.copy();
        this.absenceId = other.absenceId == null ? null : other.absenceId.copy();
        this.enseignantId = other.enseignantId == null ? null : other.enseignantId.copy();
        this.horaireId = other.horaireId == null ? null : other.horaireId.copy();
    }

    @Override
    public CoursCriteria copy() {
        return new CoursCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(StringFilter idMatiere) {
        this.idMatiere = idMatiere;
    }

    public StringFilter getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(StringFilter idClasse) {
        this.idClasse = idClasse;
    }

    public StringFilter getIdAnnee() {
        return idAnnee;
    }

    public void setIdAnnee(StringFilter idAnnee) {
        this.idAnnee = idAnnee;
    }

    public StringFilter getAbsenceId() {
        return absenceId;
    }

    public void setAbsenceId(StringFilter absenceId) {
        this.absenceId = absenceId;
    }

    public StringFilter getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(StringFilter enseignantId) {
        this.enseignantId = enseignantId;
    }

    public StringFilter getHoraireId() {
        return horaireId;
    }

    public void setHoraireId(StringFilter horaireId) {
        this.horaireId = horaireId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CoursCriteria that = (CoursCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idMatiere, that.idMatiere) &&
            Objects.equals(idClasse, that.idClasse) &&
            Objects.equals(idAnnee, that.idAnnee) &&
            Objects.equals(absenceId, that.absenceId) &&
            Objects.equals(enseignantId, that.enseignantId) &&
            Objects.equals(horaireId, that.horaireId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idMatiere,
        idClasse,
        idAnnee,
        absenceId,
        enseignantId,
        horaireId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoursCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idMatiere != null ? "idMatiere=" + idMatiere + ", " : "") +
                (idClasse != null ? "idClasse=" + idClasse + ", " : "") +
                (idAnnee != null ? "idAnnee=" + idAnnee + ", " : "") +
                (absenceId != null ? "absenceId=" + absenceId + ", " : "") +
                (enseignantId != null ? "enseignantId=" + enseignantId + ", " : "") +
                (horaireId != null ? "horaireId=" + horaireId + ", " : "") +
            "}";
    }

}
