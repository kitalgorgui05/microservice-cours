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

import com.memoire.kital.raph.domain.Horaire;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.HoraireRepository;
import com.memoire.kital.raph.service.dto.HoraireCriteria;
import com.memoire.kital.raph.service.dto.HoraireDTO;
import com.memoire.kital.raph.service.mapper.HoraireMapper;

/**
 * Service for executing complex queries for {@link Horaire} entities in the database.
 * The main input is a {@link HoraireCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HoraireDTO} or a {@link Page} of {@link HoraireDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HoraireQueryService extends QueryService<Horaire> {

    private final Logger log = LoggerFactory.getLogger(HoraireQueryService.class);

    private final HoraireRepository horaireRepository;

    private final HoraireMapper horaireMapper;

    public HoraireQueryService(HoraireRepository horaireRepository, HoraireMapper horaireMapper) {
        this.horaireRepository = horaireRepository;
        this.horaireMapper = horaireMapper;
    }

    /**
     * Return a {@link List} of {@link HoraireDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HoraireDTO> findByCriteria(HoraireCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Horaire> specification = createSpecification(criteria);
        return horaireMapper.toDto(horaireRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HoraireDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HoraireDTO> findByCriteria(HoraireCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Horaire> specification = createSpecification(criteria);
        return horaireRepository.findAll(specification, page)
            .map(horaireMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HoraireCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Horaire> specification = createSpecification(criteria);
        return horaireRepository.count(specification);
    }

    /**
     * Function to convert {@link HoraireCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Horaire> createSpecification(HoraireCriteria criteria) {
        Specification<Horaire> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Horaire_.id));
            }
            if (criteria.getHoraire() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoraire(), Horaire_.horaire));
            }
            if (criteria.getCoursId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoursId(),
                    root -> root.join(Horaire_.cours, JoinType.LEFT).get(Cours_.id)));
            }
        }
        return specification;
    }
}
