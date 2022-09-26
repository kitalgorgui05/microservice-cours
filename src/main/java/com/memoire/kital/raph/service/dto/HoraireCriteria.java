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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.memoire.kital.raph.domain.Horaire} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.HoraireResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /horaires?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HoraireCriteria implements Serializable, Criteria {

    private StringFilter id;

    private InstantFilter horaire;

    private StringFilter coursId;

    public HoraireCriteria() {
    }

    public HoraireCriteria(HoraireCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.horaire = other.horaire == null ? null : other.horaire.copy();
        this.coursId = other.coursId == null ? null : other.coursId.copy();
    }

    @Override
    public HoraireCriteria copy() {
        return new HoraireCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public InstantFilter getHoraire() {
        return horaire;
    }

    public void setHoraire(InstantFilter horaire) {
        this.horaire = horaire;
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
        final HoraireCriteria that = (HoraireCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(horaire, that.horaire) &&
            Objects.equals(coursId, that.coursId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        horaire,
        coursId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoraireCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (horaire != null ? "horaire=" + horaire + ", " : "") +
                (coursId != null ? "coursId=" + coursId + ", " : "") +
            "}";
    }

}
