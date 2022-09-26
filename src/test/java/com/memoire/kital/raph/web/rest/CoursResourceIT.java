package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.CoursApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Cours;
import com.memoire.kital.raph.domain.Absence;
import com.memoire.kital.raph.domain.Enseignant;
import com.memoire.kital.raph.domain.Horaire;
import com.memoire.kital.raph.repository.CoursRepository;
import com.memoire.kital.raph.service.CoursService;
import com.memoire.kital.raph.service.dto.CoursDTO;
import com.memoire.kital.raph.service.mapper.CoursMapper;
import com.memoire.kital.raph.service.dto.CoursCriteria;
import com.memoire.kital.raph.service.CoursQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CoursResource} REST controller.
 */
@SpringBootTest(classes = { CoursApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CoursResourceIT {

    private static final String DEFAULT_ID_MATIERE = "AAAAAAAAAA";
    private static final String UPDATED_ID_MATIERE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_CLASSE = "AAAAAAAAAA";
    private static final String UPDATED_ID_CLASSE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_ANNEE = "AAAAAAAAAA";
    private static final String UPDATED_ID_ANNEE = "BBBBBBBBBB";

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private CoursMapper coursMapper;

    @Autowired
    private CoursService coursService;

    @Autowired
    private CoursQueryService coursQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoursMockMvc;

    private Cours cours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cours createEntity(EntityManager em) {
        Cours cours = new Cours()
            .idMatiere(DEFAULT_ID_MATIERE)
            .idClasse(DEFAULT_ID_CLASSE)
            .idAnnee(DEFAULT_ID_ANNEE);
        return cours;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cours createUpdatedEntity(EntityManager em) {
        Cours cours = new Cours()
            .idMatiere(UPDATED_ID_MATIERE)
            .idClasse(UPDATED_ID_CLASSE)
            .idAnnee(UPDATED_ID_ANNEE);
        return cours;
    }

    @BeforeEach
    public void initTest() {
        cours = createEntity(em);
    }

    @Test
    @Transactional
    public void createCours() throws Exception {
        int databaseSizeBeforeCreate = coursRepository.findAll().size();
        // Create the Cours
        CoursDTO coursDTO = coursMapper.toDto(cours);
        restCoursMockMvc.perform(post("/api/cours").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isCreated());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeCreate + 1);
        Cours testCours = coursList.get(coursList.size() - 1);
        assertThat(testCours.getIdMatiere()).isEqualTo(DEFAULT_ID_MATIERE);
        assertThat(testCours.getIdClasse()).isEqualTo(DEFAULT_ID_CLASSE);
        assertThat(testCours.getIdAnnee()).isEqualTo(DEFAULT_ID_ANNEE);
    }

    @Test
    @Transactional
    public void createCoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coursRepository.findAll().size();

        // Create the Cours with an existing ID
        cours.setId(null);
        CoursDTO coursDTO = coursMapper.toDto(cours);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursMockMvc.perform(post("/api/cours").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdMatiereIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursRepository.findAll().size();
        // set the field null
        cours.setIdMatiere(null);

        // Create the Cours, which fails.
        CoursDTO coursDTO = coursMapper.toDto(cours);


        restCoursMockMvc.perform(post("/api/cours").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isBadRequest());

        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdClasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursRepository.findAll().size();
        // set the field null
        cours.setIdClasse(null);

        // Create the Cours, which fails.
        CoursDTO coursDTO = coursMapper.toDto(cours);


        restCoursMockMvc.perform(post("/api/cours").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isBadRequest());

        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursRepository.findAll().size();
        // set the field null
        cours.setIdAnnee(null);

        // Create the Cours, which fails.
        CoursDTO coursDTO = coursMapper.toDto(cours);


        restCoursMockMvc.perform(post("/api/cours").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isBadRequest());

        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList
        restCoursMockMvc.perform(get("/api/cours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cours.getId())))
            .andExpect(jsonPath("$.[*].idMatiere").value(hasItem(DEFAULT_ID_MATIERE)))
            .andExpect(jsonPath("$.[*].idClasse").value(hasItem(DEFAULT_ID_CLASSE)))
            .andExpect(jsonPath("$.[*].idAnnee").value(hasItem(DEFAULT_ID_ANNEE)));
    }

    @Test
    @Transactional
    public void getCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get the cours
        restCoursMockMvc.perform(get("/api/cours/{id}", cours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cours.getId()))
            .andExpect(jsonPath("$.idMatiere").value(DEFAULT_ID_MATIERE))
            .andExpect(jsonPath("$.idClasse").value(DEFAULT_ID_CLASSE))
            .andExpect(jsonPath("$.idAnnee").value(DEFAULT_ID_ANNEE));
    }


    @Test
    @Transactional
    public void getCoursByIdFiltering() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        String id = cours.getId();

        defaultCoursShouldBeFound("id.equals=" + id);
        defaultCoursShouldNotBeFound("id.notEquals=" + id);

        defaultCoursShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCoursShouldNotBeFound("id.greaterThan=" + id);

        defaultCoursShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCoursShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCoursByIdMatiereIsEqualToSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idMatiere equals to DEFAULT_ID_MATIERE
        defaultCoursShouldBeFound("idMatiere.equals=" + DEFAULT_ID_MATIERE);

        // Get all the coursList where idMatiere equals to UPDATED_ID_MATIERE
        defaultCoursShouldNotBeFound("idMatiere.equals=" + UPDATED_ID_MATIERE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdMatiereIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idMatiere not equals to DEFAULT_ID_MATIERE
        defaultCoursShouldNotBeFound("idMatiere.notEquals=" + DEFAULT_ID_MATIERE);

        // Get all the coursList where idMatiere not equals to UPDATED_ID_MATIERE
        defaultCoursShouldBeFound("idMatiere.notEquals=" + UPDATED_ID_MATIERE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdMatiereIsInShouldWork() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idMatiere in DEFAULT_ID_MATIERE or UPDATED_ID_MATIERE
        defaultCoursShouldBeFound("idMatiere.in=" + DEFAULT_ID_MATIERE + "," + UPDATED_ID_MATIERE);

        // Get all the coursList where idMatiere equals to UPDATED_ID_MATIERE
        defaultCoursShouldNotBeFound("idMatiere.in=" + UPDATED_ID_MATIERE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdMatiereIsNullOrNotNull() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idMatiere is not null
        defaultCoursShouldBeFound("idMatiere.specified=true");

        // Get all the coursList where idMatiere is null
        defaultCoursShouldNotBeFound("idMatiere.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoursByIdMatiereContainsSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idMatiere contains DEFAULT_ID_MATIERE
        defaultCoursShouldBeFound("idMatiere.contains=" + DEFAULT_ID_MATIERE);

        // Get all the coursList where idMatiere contains UPDATED_ID_MATIERE
        defaultCoursShouldNotBeFound("idMatiere.contains=" + UPDATED_ID_MATIERE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdMatiereNotContainsSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idMatiere does not contain DEFAULT_ID_MATIERE
        defaultCoursShouldNotBeFound("idMatiere.doesNotContain=" + DEFAULT_ID_MATIERE);

        // Get all the coursList where idMatiere does not contain UPDATED_ID_MATIERE
        defaultCoursShouldBeFound("idMatiere.doesNotContain=" + UPDATED_ID_MATIERE);
    }


    @Test
    @Transactional
    public void getAllCoursByIdClasseIsEqualToSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idClasse equals to DEFAULT_ID_CLASSE
        defaultCoursShouldBeFound("idClasse.equals=" + DEFAULT_ID_CLASSE);

        // Get all the coursList where idClasse equals to UPDATED_ID_CLASSE
        defaultCoursShouldNotBeFound("idClasse.equals=" + UPDATED_ID_CLASSE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdClasseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idClasse not equals to DEFAULT_ID_CLASSE
        defaultCoursShouldNotBeFound("idClasse.notEquals=" + DEFAULT_ID_CLASSE);

        // Get all the coursList where idClasse not equals to UPDATED_ID_CLASSE
        defaultCoursShouldBeFound("idClasse.notEquals=" + UPDATED_ID_CLASSE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdClasseIsInShouldWork() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idClasse in DEFAULT_ID_CLASSE or UPDATED_ID_CLASSE
        defaultCoursShouldBeFound("idClasse.in=" + DEFAULT_ID_CLASSE + "," + UPDATED_ID_CLASSE);

        // Get all the coursList where idClasse equals to UPDATED_ID_CLASSE
        defaultCoursShouldNotBeFound("idClasse.in=" + UPDATED_ID_CLASSE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdClasseIsNullOrNotNull() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idClasse is not null
        defaultCoursShouldBeFound("idClasse.specified=true");

        // Get all the coursList where idClasse is null
        defaultCoursShouldNotBeFound("idClasse.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoursByIdClasseContainsSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idClasse contains DEFAULT_ID_CLASSE
        defaultCoursShouldBeFound("idClasse.contains=" + DEFAULT_ID_CLASSE);

        // Get all the coursList where idClasse contains UPDATED_ID_CLASSE
        defaultCoursShouldNotBeFound("idClasse.contains=" + UPDATED_ID_CLASSE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdClasseNotContainsSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idClasse does not contain DEFAULT_ID_CLASSE
        defaultCoursShouldNotBeFound("idClasse.doesNotContain=" + DEFAULT_ID_CLASSE);

        // Get all the coursList where idClasse does not contain UPDATED_ID_CLASSE
        defaultCoursShouldBeFound("idClasse.doesNotContain=" + UPDATED_ID_CLASSE);
    }


    @Test
    @Transactional
    public void getAllCoursByIdAnneeIsEqualToSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idAnnee equals to DEFAULT_ID_ANNEE
        defaultCoursShouldBeFound("idAnnee.equals=" + DEFAULT_ID_ANNEE);

        // Get all the coursList where idAnnee equals to UPDATED_ID_ANNEE
        defaultCoursShouldNotBeFound("idAnnee.equals=" + UPDATED_ID_ANNEE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdAnneeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idAnnee not equals to DEFAULT_ID_ANNEE
        defaultCoursShouldNotBeFound("idAnnee.notEquals=" + DEFAULT_ID_ANNEE);

        // Get all the coursList where idAnnee not equals to UPDATED_ID_ANNEE
        defaultCoursShouldBeFound("idAnnee.notEquals=" + UPDATED_ID_ANNEE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdAnneeIsInShouldWork() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idAnnee in DEFAULT_ID_ANNEE or UPDATED_ID_ANNEE
        defaultCoursShouldBeFound("idAnnee.in=" + DEFAULT_ID_ANNEE + "," + UPDATED_ID_ANNEE);

        // Get all the coursList where idAnnee equals to UPDATED_ID_ANNEE
        defaultCoursShouldNotBeFound("idAnnee.in=" + UPDATED_ID_ANNEE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdAnneeIsNullOrNotNull() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idAnnee is not null
        defaultCoursShouldBeFound("idAnnee.specified=true");

        // Get all the coursList where idAnnee is null
        defaultCoursShouldNotBeFound("idAnnee.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoursByIdAnneeContainsSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idAnnee contains DEFAULT_ID_ANNEE
        defaultCoursShouldBeFound("idAnnee.contains=" + DEFAULT_ID_ANNEE);

        // Get all the coursList where idAnnee contains UPDATED_ID_ANNEE
        defaultCoursShouldNotBeFound("idAnnee.contains=" + UPDATED_ID_ANNEE);
    }

    @Test
    @Transactional
    public void getAllCoursByIdAnneeNotContainsSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList where idAnnee does not contain DEFAULT_ID_ANNEE
        defaultCoursShouldNotBeFound("idAnnee.doesNotContain=" + DEFAULT_ID_ANNEE);

        // Get all the coursList where idAnnee does not contain UPDATED_ID_ANNEE
        defaultCoursShouldBeFound("idAnnee.doesNotContain=" + UPDATED_ID_ANNEE);
    }


    @Test
    @Transactional
    public void getAllCoursByAbsenceIsEqualToSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);
        Absence absence = AbsenceResourceIT.createEntity(em);
        em.persist(absence);
        em.flush();
        cours.addAbsence(absence);
        coursRepository.saveAndFlush(cours);
        String absenceId = absence.getId();

        // Get all the coursList where absence equals to absenceId
        defaultCoursShouldBeFound("absenceId.equals=" + absenceId);

        // Get all the coursList where absence equals to absenceId + 1
        defaultCoursShouldNotBeFound("absenceId.equals=" + (absenceId + 1));
    }


    @Test
    @Transactional
    public void getAllCoursByEnseignantIsEqualToSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);
        Enseignant enseignant = EnseignantResourceIT.createEntity(em);
        em.persist(enseignant);
        em.flush();
        cours.setEnseignant(enseignant);
        coursRepository.saveAndFlush(cours);
        String enseignantId = enseignant.getId();

        // Get all the coursList where enseignant equals to enseignantId
        defaultCoursShouldBeFound("enseignantId.equals=" + enseignantId);

        // Get all the coursList where enseignant equals to enseignantId + 1
        defaultCoursShouldNotBeFound("enseignantId.equals=" + (enseignantId + 1));
    }


    @Test
    @Transactional
    public void getAllCoursByHoraireIsEqualToSomething() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);
        Horaire horaire = HoraireResourceIT.createEntity(em);
        em.persist(horaire);
        em.flush();
        cours.setHoraire(horaire);
        coursRepository.saveAndFlush(cours);
        String horaireId = horaire.getId();

        // Get all the coursList where horaire equals to horaireId
        defaultCoursShouldBeFound("horaireId.equals=" + horaireId);

        // Get all the coursList where horaire equals to horaireId + 1
        defaultCoursShouldNotBeFound("horaireId.equals=" + (horaireId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCoursShouldBeFound(String filter) throws Exception {
        restCoursMockMvc.perform(get("/api/cours?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cours.getId())))
            .andExpect(jsonPath("$.[*].idMatiere").value(hasItem(DEFAULT_ID_MATIERE)))
            .andExpect(jsonPath("$.[*].idClasse").value(hasItem(DEFAULT_ID_CLASSE)))
            .andExpect(jsonPath("$.[*].idAnnee").value(hasItem(DEFAULT_ID_ANNEE)));

        // Check, that the count call also returns 1
        restCoursMockMvc.perform(get("/api/cours/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCoursShouldNotBeFound(String filter) throws Exception {
        restCoursMockMvc.perform(get("/api/cours?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCoursMockMvc.perform(get("/api/cours/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCours() throws Exception {
        // Get the cours
        restCoursMockMvc.perform(get("/api/cours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        int databaseSizeBeforeUpdate = coursRepository.findAll().size();

        // Update the cours
        Cours updatedCours = coursRepository.findById(cours.getId()).get();
        // Disconnect from session so that the updates on updatedCours are not directly saved in db
        em.detach(updatedCours);
        updatedCours
            .idMatiere(UPDATED_ID_MATIERE)
            .idClasse(UPDATED_ID_CLASSE)
            .idAnnee(UPDATED_ID_ANNEE);
        CoursDTO coursDTO = coursMapper.toDto(updatedCours);

        restCoursMockMvc.perform(put("/api/cours").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isOk());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeUpdate);
        Cours testCours = coursList.get(coursList.size() - 1);
        assertThat(testCours.getIdMatiere()).isEqualTo(UPDATED_ID_MATIERE);
        assertThat(testCours.getIdClasse()).isEqualTo(UPDATED_ID_CLASSE);
        assertThat(testCours.getIdAnnee()).isEqualTo(UPDATED_ID_ANNEE);
    }

    @Test
    @Transactional
    public void updateNonExistingCours() throws Exception {
        int databaseSizeBeforeUpdate = coursRepository.findAll().size();

        // Create the Cours
        CoursDTO coursDTO = coursMapper.toDto(cours);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoursMockMvc.perform(put("/api/cours").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        int databaseSizeBeforeDelete = coursRepository.findAll().size();

        // Delete the cours
        restCoursMockMvc.perform(delete("/api/cours/{id}", cours.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
