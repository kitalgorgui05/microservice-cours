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

import com.memoire.kital.raph.domain.Enseignant;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.EnseignantRepository;
import com.memoire.kital.raph.service.dto.EnseignantCriteria;
import com.memoire.kital.raph.service.dto.EnseignantDTO;
import com.memoire.kital.raph.service.mapper.EnseignantMapper;

/**
 * Service for executing complex queries for {@link Enseignant} entities in the database.
 * The main input is a {@link EnseignantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnseignantDTO} or a {@link Page} of {@link EnseignantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnseignantQueryService extends QueryService<Enseignant> {

    private final Logger log = LoggerFactory.getLogger(EnseignantQueryService.class);

    private final EnseignantRepository enseignantRepository;

    private final EnseignantMapper enseignantMapper;

    public EnseignantQueryService(EnseignantRepository enseignantRepository, EnseignantMapper enseignantMapper) {
        this.enseignantRepository = enseignantRepository;
        this.enseignantMapper = enseignantMapper;
    }

    /**
     * Return a {@link List} of {@link EnseignantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnseignantDTO> findByCriteria(EnseignantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Enseignant> specification = createSpecification(criteria);
        return enseignantMapper.toDto(enseignantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnseignantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnseignantDTO> findByCriteria(EnseignantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Enseignant> specification = createSpecification(criteria);
        return enseignantRepository.findAll(specification, page)
            .map(enseignantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnseignantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Enseignant> specification = createSpecification(criteria);
        return enseignantRepository.count(specification);
    }

    /**
     * Function to convert {@link EnseignantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Enseignant> createSpecification(EnseignantCriteria criteria) {
        Specification<Enseignant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Enseignant_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Enseignant_.nom));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Enseignant_.prenom));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), Enseignant_.telephone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Enseignant_.email));
            }
            if (criteria.getCoursId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoursId(),
                    root -> root.join(Enseignant_.cours, JoinType.LEFT).get(Cours_.id)));
            }
        }
        return specification;
    }
}
