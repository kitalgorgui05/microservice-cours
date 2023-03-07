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


public class HoraireCriteria implements Serializable, Criteria {

    private StringFilter id;

    private InstantFilter heurDebut;
    private InstantFilter heurFin;

    private StringFilter coursId;

    public HoraireCriteria() {
    }

    public HoraireCriteria(HoraireCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.heurDebut = other.heurDebut == null ? null : other.heurDebut.copy();
        this.heurFin = other.heurFin == null ? null : other.heurFin.copy();
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

    public InstantFilter getHeurDebut() {
        return heurDebut;
    }

    public void setHeurDebut(InstantFilter heurDebut) {
        this.heurDebut = heurDebut;
    }

    public InstantFilter getHeurFin() {
        return heurFin;
    }

    public void setHeurFin(InstantFilter heurFin) {
        this.heurFin = heurFin;
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
            Objects.equals(heurDebut, that.heurDebut) &&
            Objects.equals(heurFin, that.heurFin) &&
            Objects.equals(coursId, that.coursId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        heurDebut,
        heurFin,
        coursId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoraireCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (heurDebut != null ? "heurDebut=" + heurDebut + ", " : "") +
                (heurFin != null ? "heurFin=" + heurFin + ", " : "") +
                (coursId != null ? "coursId=" + coursId + ", " : "") +
            "}";
    }

}
