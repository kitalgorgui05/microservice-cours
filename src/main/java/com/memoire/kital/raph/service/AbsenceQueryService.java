package com.memoire.kital.raph.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import com.memoire.kital.raph.openFeign.IAnneRestClient;
import com.memoire.kital.raph.openFeign.IClasseRestClient;
import com.memoire.kital.raph.openFeign.IEleveRestClient;
import com.memoire.kital.raph.openFeign.IMatiereRestClient;
import com.memoire.kital.raph.repository.CoursRepository;
import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.EleveClient;
import com.memoire.kital.raph.restClient.MatiereClient;
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

@Service
@Transactional(readOnly = true)
public class AbsenceQueryService extends QueryService<Absence> {

    private final Logger log = LoggerFactory.getLogger(AbsenceQueryService.class);

    private final AbsenceRepository absenceRepository;

    private final AbsenceMapper absenceMapper;

    private final IEleveRestClient iEleveRestClient;
    private final CoursRepository coursRepository;
    private final IMatiereRestClient iMatiereRestClient;
    private final IClasseRestClient iClasseRestClient;
    private final IAnneRestClient iAnneRestClient;

    public AbsenceQueryService(AbsenceRepository absenceRepository, AbsenceMapper absenceMapper, IEleveRestClient iEleveRestClient, CoursRepository coursRepository, IMatiereRestClient iMatiereRestClient, IClasseRestClient iClasseRestClient, IAnneRestClient iAnneRestClient) {
        this.absenceRepository = absenceRepository;
        this.absenceMapper = absenceMapper;
        this.iEleveRestClient = iEleveRestClient;
        this.coursRepository = coursRepository;
        this.iMatiereRestClient = iMatiereRestClient;
        this.iClasseRestClient = iClasseRestClient;
        this.iAnneRestClient = iAnneRestClient;
    }
    @Transactional(readOnly = true)
    public List<AbsenceDTO> findByCriteria(AbsenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Absence> specification = createSpecification(criteria);
        return absenceMapper.toDto(absenceRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<AbsenceDTO> findByCriteria(AbsenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Absence> specification = createSpecification(criteria);
        Page<Absence> absencePage=absenceRepository.findAll(specification, page);
        for (Absence a :absencePage.getContent()){
            EleveClient eleveClient=iEleveRestClient.getEleve(a.getIdEleve()).getBody();
            a.setEleveClient(eleveClient);
            Cours cours=coursRepository.findById(a.getCours().getId()).get();
            MatiereClient matiereClient=iMatiereRestClient.getMatiere(cours.getIdMatiere()).getBody();
            ClasseClient classeClient=iClasseRestClient.getClasse(cours.getIdClasse()).getBody();
            AnneeClient anneeClient=iAnneRestClient.getAnnee(cours.getIdAnnee()).getBody();
            cours.setMatiereClient(matiereClient);
            cours.setClasseClient(classeClient);
            cours.setAnneeClient(anneeClient);
            a.setEleveClient(eleveClient);
            a.setCours(cours);
        }
        return absencePage.map(absenceMapper::toDto);
    }
    @Transactional(readOnly = true)
    public long countByCriteria(AbsenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Absence> specification = createSpecification(criteria);
        return absenceRepository.count(specification);
    }
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
