package com.memoire.kital.raph.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.memoire.kital.raph.domain.Absence;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.AbsenceRepository;
import com.memoire.kital.raph.service.dto.AbsenceCriteria;
import com.memoire.kital.raph.service.dto.AbsenceDTO;
import com.memoire.kital.raph.service.mapper.AbsenceMapper;

/**
 * Service for executing complex queries for {@link Absence} entities in the database.
 * The main input is a {@link AbsenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AbsenceDTO} or a {@link Page} of {@link AbsenceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AbsenceQueryService extends QueryService<Absence> {

    private final Logger log = LoggerFactory.getLogger(AbsenceQueryService.class);

    private final AbsenceRepository absenceRepository;

    private final AbsenceMapper absenceMapper;

    public AbsenceQueryService(AbsenceRepository absenceRepository, AbsenceMapper absenceMapper) {
        this.absenceRepository = absenceRepository;
        this.absenceMapper = absenceMapper;
    }

    /**
     * Return a {@link List} of {@link AbsenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AbsenceDTO> findByCriteria(AbsenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Absence> specification = createSpecification(criteria);
        return absenceMapper.toDto(absenceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AbsenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AbsenceDTO> findByCriteria(AbsenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Absence> specification = createSpecification(criteria);
        return absenceRepository.findAll(specification, page)
            .map(absenceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AbsenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Absence> specification = createSpecification(criteria);
        return absenceRepository.count(specification);
    }

    /**
     * Function to convert {@link AbsenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Absence> createSpecification(AbsenceCriteria criteria) {
        Specification<Absence> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Absence_.id));
            }
            if (criteria.getIdEleve() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdEleve(), Absence_.idEleve));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildSpecification(criteria.getEtat(), Absence_.etat));
            }
            if (criteria.getCoursId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoursId(),
                    root -> root.join(Absence_.cours, JoinType.LEFT).get(Cours_.id)));
            }
        }
        return specification;
    }
}
