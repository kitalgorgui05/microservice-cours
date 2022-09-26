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
 * Criteria class for the {@link com.memoire.kital.raph.domain.Enseignant} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.EnseignantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enseignants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnseignantCriteria implements Serializable, Criteria {
    private StringFilter id;

    private StringFilter nom;

    private StringFilter prenom;

    private StringFilter telephone;

    private StringFilter email;

    private StringFilter coursId;

    public EnseignantCriteria() {
    }

    public EnseignantCriteria(EnseignantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.coursId = other.coursId == null ? null : other.coursId.copy();
    }

    @Override
    public EnseignantCriteria copy() {
        return new EnseignantCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getPrenom() {
        return prenom;
    }

    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
    }

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
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
        final EnseignantCriteria that = (EnseignantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(coursId, that.coursId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        prenom,
        telephone,
        email,
        coursId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnseignantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (coursId != null ? "coursId=" + coursId + ", " : "") +
            "}";
    }

}
