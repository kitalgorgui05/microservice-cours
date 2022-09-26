package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.CoursApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Absence;
import com.memoire.kital.raph.domain.Cours;
import com.memoire.kital.raph.repository.AbsenceRepository;
import com.memoire.kital.raph.service.AbsenceService;
import com.memoire.kital.raph.service.dto.AbsenceDTO;
import com.memoire.kital.raph.service.mapper.AbsenceMapper;
import com.memoire.kital.raph.service.dto.AbsenceCriteria;
import com.memoire.kital.raph.service.AbsenceQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AbsenceResource} REST controller.
 */
@SpringBootTest(classes = { CoursApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AbsenceResourceIT {

    private static final String DEFAULT_ID_ELEVE = "AAAAAAAAAA";
    private static final String UPDATED_ID_ELEVE = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIF = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ETAT = false;
    private static final Boolean UPDATED_ETAT = true;

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private AbsenceMapper absenceMapper;

    @Autowired
    private AbsenceService absenceService;

    @Autowired
    private AbsenceQueryService absenceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAbsenceMockMvc;

    private Absence absence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Absence createEntity(EntityManager em) {
        Absence absence = new Absence()
            .idEleve(DEFAULT_ID_ELEVE)
            .motif(DEFAULT_MOTIF)
            .etat(DEFAULT_ETAT);
        return absence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Absence createUpdatedEntity(EntityManager em) {
        Absence absence = new Absence()
            .idEleve(UPDATED_ID_ELEVE)
            .motif(UPDATED_MOTIF)
            .etat(UPDATED_ETAT);
        return absence;
    }

    @BeforeEach
    public void initTest() {
        absence = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbsence() throws Exception {
        int databaseSizeBeforeCreate = absenceRepository.findAll().size();
        // Create the Absence
        AbsenceDTO absenceDTO = absenceMapper.toDto(absence);
        restAbsenceMockMvc.perform(post("/api/absences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeCreate + 1);
        Absence testAbsence = absenceList.get(absenceList.size() - 1);
        assertThat(testAbsence.getIdEleve()).isEqualTo(DEFAULT_ID_ELEVE);
        assertThat(testAbsence.getMotif()).isEqualTo(DEFAULT_MOTIF);
        assertThat(testAbsence.isEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void createAbsenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = absenceRepository.findAll().size();

        // Create the Absence with an existing ID
        absence.setId(null);
        AbsenceDTO absenceDTO = absenceMapper.toDto(absence);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbsenceMockMvc.perform(post("/api/absences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdEleveIsRequired() throws Exception {
        int databaseSizeBeforeTest = absenceRepository.findAll().size();
        // set the field null
        absence.setIdEleve(null);

        // Create the Absence, which fails.
        AbsenceDTO absenceDTO = absenceMapper.toDto(absence);


        restAbsenceMockMvc.perform(post("/api/absences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absenceDTO)))
            .andExpect(status().isBadRequest());

        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = absenceRepository.findAll().size();
        // set the field null
        absence.setEtat(null);

        // Create the Absence, which fails.
        AbsenceDTO absenceDTO = absenceMapper.toDto(absence);


        restAbsenceMockMvc.perform(post("/api/absences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absenceDTO)))
            .andExpect(status().isBadRequest());

        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAbsences() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList
        restAbsenceMockMvc.perform(get("/api/absences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(absence.getId())))
            .andExpect(jsonPath("$.[*].idEleve").value(hasItem(DEFAULT_ID_ELEVE)))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())));
    }

    @Test
    @Transactional
    public void getAbsence() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get the absence
        restAbsenceMockMvc.perform(get("/api/absences/{id}", absence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(absence.getId()))
            .andExpect(jsonPath("$.idEleve").value(DEFAULT_ID_ELEVE))
            .andExpect(jsonPath("$.motif").value(DEFAULT_MOTIF.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.booleanValue()));
    }


    @Test
    @Transactional
    public void getAbsencesByIdFiltering() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        String id = absence.getId();

        defaultAbsenceShouldBeFound("id.equals=" + id);
        defaultAbsenceShouldNotBeFound("id.notEquals=" + id);

        defaultAbsenceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAbsenceShouldNotBeFound("id.greaterThan=" + id);

        defaultAbsenceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAbsenceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAbsencesByIdEleveIsEqualToSomething() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList where idEleve equals to DEFAULT_ID_ELEVE
        defaultAbsenceShouldBeFound("idEleve.equals=" + DEFAULT_ID_ELEVE);

        // Get all the absenceList where idEleve equals to UPDATED_ID_ELEVE
        defaultAbsenceShouldNotBeFound("idEleve.equals=" + UPDATED_ID_ELEVE);
    }

    @Test
    @Transactional
    public void getAllAbsencesByIdEleveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList where idEleve not equals to DEFAULT_ID_ELEVE
        defaultAbsenceShouldNotBeFound("idEleve.notEquals=" + DEFAULT_ID_ELEVE);

        // Get all the absenceList where idEleve not equals to UPDATED_ID_ELEVE
        defaultAbsenceShouldBeFound("idEleve.notEquals=" + UPDATED_ID_ELEVE);
    }

    @Test
    @Transactional
    public void getAllAbsencesByIdEleveIsInShouldWork() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList where idEleve in DEFAULT_ID_ELEVE or UPDATED_ID_ELEVE
        defaultAbsenceShouldBeFound("idEleve.in=" + DEFAULT_ID_ELEVE + "," + UPDATED_ID_ELEVE);

        // Get all the absenceList where idEleve equals to UPDATED_ID_ELEVE
        defaultAbsenceShouldNotBeFound("idEleve.in=" + UPDATED_ID_ELEVE);
    }

    @Test
    @Transactional
    public void getAllAbsencesByIdEleveIsNullOrNotNull() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList where idEleve is not null
        defaultAbsenceShouldBeFound("idEleve.specified=true");

        // Get all the absenceList where idEleve is null
        defaultAbsenceShouldNotBeFound("idEleve.specified=false");
    }
                @Test
    @Transactional
    public void getAllAbsencesByIdEleveContainsSomething() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList where idEleve contains DEFAULT_ID_ELEVE
        defaultAbsenceShouldBeFound("idEleve.contains=" + DEFAULT_ID_ELEVE);

        // Get all the absenceList where idEleve contains UPDATED_ID_ELEVE
        defaultAbsenceShouldNotBeFound("idEleve.contains=" + UPDATED_ID_ELEVE);
    }

    @Test
    @Transactional
    public void getAllAbsencesByIdEleveNotContainsSomething() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList where idEleve does not contain DEFAULT_ID_ELEVE
        defaultAbsenceShouldNotBeFound("idEleve.doesNotContain=" + DEFAULT_ID_ELEVE);

        // Get all the absenceList where idEleve does not contain UPDATED_ID_ELEVE
        defaultAbsenceShouldBeFound("idEleve.doesNotContain=" + UPDATED_ID_ELEVE);
    }


    @Test
    @Transactional
    public void getAllAbsencesByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList where etat equals to DEFAULT_ETAT
        defaultAbsenceShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the absenceList where etat equals to UPDATED_ETAT
        defaultAbsenceShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllAbsencesByEtatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList where etat not equals to DEFAULT_ETAT
        defaultAbsenceShouldNotBeFound("etat.notEquals=" + DEFAULT_ETAT);

        // Get all the absenceList where etat not equals to UPDATED_ETAT
        defaultAbsenceShouldBeFound("etat.notEquals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllAbsencesByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultAbsenceShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the absenceList where etat equals to UPDATED_ETAT
        defaultAbsenceShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllAbsencesByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList where etat is not null
        defaultAbsenceShouldBeFound("etat.specified=true");

        // Get all the absenceList where etat is null
        defaultAbsenceShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllAbsencesByCoursIsEqualToSomething() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);
        Cours cours = CoursResourceIT.createEntity(em);
        em.persist(cours);
        em.flush();
        absence.setCours(cours);
        absenceRepository.saveAndFlush(absence);
        String coursId = cours.getId();

        // Get all the absenceList where cours equals to coursId
        defaultAbsenceShouldBeFound("coursId.equals=" + coursId);

        // Get all the absenceList where cours equals to coursId + 1
        defaultAbsenceShouldNotBeFound("coursId.equals=" + (coursId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAbsenceShouldBeFound(String filter) throws Exception {
        restAbsenceMockMvc.perform(get("/api/absences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(absence.getId())))
            .andExpect(jsonPath("$.[*].idEleve").value(hasItem(DEFAULT_ID_ELEVE)))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())));

        // Check, that the count call also returns 1
        restAbsenceMockMvc.perform(get("/api/absences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAbsenceShouldNotBeFound(String filter) throws Exception {
        restAbsenceMockMvc.perform(get("/api/absences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAbsenceMockMvc.perform(get("/api/absences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAbsence() throws Exception {
        // Get the absence
        restAbsenceMockMvc.perform(get("/api/absences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbsence() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        int databaseSizeBeforeUpdate = absenceRepository.findAll().size();

        // Update the absence
        Absence updatedAbsence = absenceRepository.findById(absence.getId()).get();
        // Disconnect from session so that the updates on updatedAbsence are not directly saved in db
        em.detach(updatedAbsence);
        updatedAbsence
            .idEleve(UPDATED_ID_ELEVE)
            .motif(UPDATED_MOTIF)
            .etat(UPDATED_ETAT);
        AbsenceDTO absenceDTO = absenceMapper.toDto(updatedAbsence);

        restAbsenceMockMvc.perform(put("/api/absences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absenceDTO)))
            .andExpect(status().isOk());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeUpdate);
        Absence testAbsence = absenceList.get(absenceList.size() - 1);
        assertThat(testAbsence.getIdEleve()).isEqualTo(UPDATED_ID_ELEVE);
        assertThat(testAbsence.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testAbsence.isEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void updateNonExistingAbsence() throws Exception {
        int databaseSizeBeforeUpdate = absenceRepository.findAll().size();

        // Create the Absence
        AbsenceDTO absenceDTO = absenceMapper.toDto(absence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbsenceMockMvc.perform(put("/api/absences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAbsence() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        int databaseSizeBeforeDelete = absenceRepository.findAll().size();

        // Delete the absence
        restAbsenceMockMvc.perform(delete("/api/absences/{id}", absence.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
