package com.memoire.kital.raph.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import com.memoire.kital.raph.openFeign.IAnneRestClient;
import com.memoire.kital.raph.openFeign.IClasseRestClient;
import com.memoire.kital.raph.openFeign.IMatiereRestClient;
import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.MatiereClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.memoire.kital.raph.domain.Cours;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.CoursRepository;
import com.memoire.kital.raph.service.dto.CoursCriteria;
import com.memoire.kital.raph.service.dto.CoursDTO;
import com.memoire.kital.raph.service.mapper.CoursMapper;

/**
 * Service for executing complex queries for {@link Cours} entities in the database.
 * The main input is a {@link CoursCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CoursDTO} or a {@link Page} of {@link CoursDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CoursQueryService extends QueryService<Cours> {

    private final Logger log = LoggerFactory.getLogger(CoursQueryService.class);

    private final CoursRepository coursRepository;

    private final CoursMapper coursMapper;

    private final IMatiereRestClient iMatiereRestClient;
    private final IClasseRestClient iClasseRestClient;
    private final IAnneRestClient iAnneRestClient;

    public CoursQueryService(CoursRepository coursRepository, CoursMapper coursMapper, IMatiereRestClient iMatiereRestClient, IClasseRestClient iClasseRestClient, IAnneRestClient iAnneRestClient) {
        this.coursRepository = coursRepository;
        this.coursMapper = coursMapper;
        this.iMatiereRestClient = iMatiereRestClient;
        this.iClasseRestClient = iClasseRestClient;
        this.iAnneRestClient = iAnneRestClient;
    }
    @Transactional(readOnly = true)
    public List<CoursDTO> findByCriteria(CoursCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cours> specification = createSpecification(criteria);
        return coursMapper.toDto(coursRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<CoursDTO> findByCriteria(CoursCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cours> specification = createSpecification(criteria);
        Page<Cours> coursPage=coursRepository.findAll(specification, page);
        for (Cours c : coursPage.getContent()){
            ClasseClient classeClient= iClasseRestClient.getClasse(c.getIdClasse()).getBody();
            MatiereClient matiereClient=iMatiereRestClient.getMatiere(c.getIdMatiere()).getBody();
            AnneeClient anneeClient = iAnneRestClient.getAnnee(c.getIdAnnee()).getBody();
            c.setClasseClient(classeClient);
            c.setMatiereClient(matiereClient);
            c.setAnneeClient(anneeClient);
        }
        return coursPage
            .map(coursMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CoursCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cours> specification = createSpecification(criteria);
        return coursRepository.count(specification);
    }

    /**
     * Function to convert {@link CoursCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cours> createSpecification(CoursCriteria criteria) {
        Specification<Cours> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Cours_.id));
            }
            if (criteria.getIdMatiere() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdMatiere(), Cours_.idMatiere));
            }
            if (criteria.getIdClasse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdClasse(), Cours_.idClasse));
            }
            if (criteria.getIdAnnee() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdAnnee(), Cours_.idAnnee));
            }
            if (criteria.getAbsenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAbsenceId(),
                    root -> root.join(Cours_.absences, JoinType.LEFT).get(Absence_.id)));
            }
            if (criteria.getEnseignantId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnseignantId(),
                    root -> root.join(Cours_.enseignant, JoinType.LEFT).get(Enseignant_.id)));
            }
            if (criteria.getHoraireId() != null) {
                specification = specification.and(buildSpecification(criteria.getHoraireId(),
                    root -> root.join(Cours_.horaire, JoinType.LEFT).get(Horaire_.id)));
            }
        }
        return specification;
    }
}
