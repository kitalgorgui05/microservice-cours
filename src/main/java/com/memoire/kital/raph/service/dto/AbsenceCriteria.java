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
 * Criteria class for the {@link com.memoire.kital.raph.domain.Absence} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.AbsenceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /absences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AbsenceCriteria implements Serializable, Criteria {

    private StringFilter id;

    private StringFilter idEleve;

    private BooleanFilter etat;

    private StringFilter coursId;

    public AbsenceCriteria() {
    }

    public AbsenceCriteria(AbsenceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idEleve = other.idEleve == null ? null : other.idEleve.copy();
        this.etat = other.etat == null ? null : other.etat.copy();
        this.coursId = other.coursId == null ? null : other.coursId.copy();
    }

    @Override
    public AbsenceCriteria copy() {
        return new AbsenceCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(StringFilter idEleve) {
        this.idEleve = idEleve;
    }

    public BooleanFilter getEtat() {
        return etat;
    }

    public void setEtat(BooleanFilter etat) {
        this.etat = etat;
    }

    public StringFilter getCoursId() {
        return coursId;
    }

    public void setCoursId(StringFilter coursId) {
        this.coursId = coursId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AbsenceCriteria that = (AbsenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idEleve, that.idEleve) &&
            Objects.equals(etat, that.etat) &&
            Objects.equals(coursId, that.coursId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idEleve,
        etat,
        coursId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbsenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idEleve != null ? "idEleve=" + idEleve + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (coursId != null ? "coursId=" + coursId + ", " : "") +
            "}";
    }

}
