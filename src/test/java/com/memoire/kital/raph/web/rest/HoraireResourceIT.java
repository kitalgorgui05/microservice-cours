package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.CoursApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Horaire;
import com.memoire.kital.raph.domain.Cours;
import com.memoire.kital.raph.repository.HoraireRepository;
import com.memoire.kital.raph.service.HoraireService;
import com.memoire.kital.raph.service.dto.HoraireDTO;
import com.memoire.kital.raph.service.mapper.HoraireMapper;
import com.memoire.kital.raph.service.dto.HoraireCriteria;
import com.memoire.kital.raph.service.HoraireQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HoraireResource} REST controller.
 */
@SpringBootTest(classes = { CoursApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class HoraireResourceIT {

    private static final Instant DEFAULT_HORAIRE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORAIRE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private HoraireRepository horaireRepository;

    @Autowired
    private HoraireMapper horaireMapper;

    @Autowired
    private HoraireService horaireService;

    @Autowired
    private HoraireQueryService horaireQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoraireMockMvc;

    private Horaire horaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horaire createEntity(EntityManager em) {
        Horaire horaire = new Horaire()
            .horaire(DEFAULT_HORAIRE);
        return horaire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horaire createUpdatedEntity(EntityManager em) {
        Horaire horaire = new Horaire()
            .horaire(UPDATED_HORAIRE);
        return horaire;
    }

    @BeforeEach
    public void initTest() {
        horaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createHoraire() throws Exception {
        int databaseSizeBeforeCreate = horaireRepository.findAll().size();
        // Create the Horaire
        HoraireDTO horaireDTO = horaireMapper.toDto(horaire);
        restHoraireMockMvc.perform(post("/api/horaires").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaireDTO)))
            .andExpect(status().isCreated());

        // Validate the Horaire in the database
        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeCreate + 1);
        Horaire testHoraire = horaireList.get(horaireList.size() - 1);
        assertThat(testHoraire.getHoraire()).isEqualTo(DEFAULT_HORAIRE);
    }

    @Test
    @Transactional
    public void createHoraireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horaireRepository.findAll().size();

        // Create the Horaire with an existing ID
        horaire.setId(null);
        HoraireDTO horaireDTO = horaireMapper.toDto(horaire);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoraireMockMvc.perform(post("/api/horaires").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Horaire in the database
        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHoraireIsRequired() throws Exception {
        int databaseSizeBeforeTest = horaireRepository.findAll().size();
        // set the field null
        horaire.setHoraire(null);

        // Create the Horaire, which fails.
        HoraireDTO horaireDTO = horaireMapper.toDto(horaire);


        restHoraireMockMvc.perform(post("/api/horaires").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaireDTO)))
            .andExpect(status().isBadRequest());

        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHoraires() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        // Get all the horaireList
        restHoraireMockMvc.perform(get("/api/horaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horaire.getId())))
            .andExpect(jsonPath("$.[*].horaire").value(hasItem(DEFAULT_HORAIRE.toString())));
    }

    @Test
    @Transactional
    public void getHoraire() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        // Get the horaire
        restHoraireMockMvc.perform(get("/api/horaires/{id}", horaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(horaire.getId()))
            .andExpect(jsonPath("$.horaire").value(DEFAULT_HORAIRE.toString()));
    }


    @Test
    @Transactional
    public void getHorairesByIdFiltering() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        String id = horaire.getId();

        defaultHoraireShouldBeFound("id.equals=" + id);
        defaultHoraireShouldNotBeFound("id.notEquals=" + id);

        defaultHoraireShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHoraireShouldNotBeFound("id.greaterThan=" + id);

        defaultHoraireShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHoraireShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHorairesByHoraireIsEqualToSomething() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        // Get all the horaireList where horaire equals to DEFAULT_HORAIRE
        defaultHoraireShouldBeFound("horaire.equals=" + DEFAULT_HORAIRE);

        // Get all the horaireList where horaire equals to UPDATED_HORAIRE
        defaultHoraireShouldNotBeFound("horaire.equals=" + UPDATED_HORAIRE);
    }

    @Test
    @Transactional
    public void getAllHorairesByHoraireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        // Get all the horaireList where horaire not equals to DEFAULT_HORAIRE
        defaultHoraireShouldNotBeFound("horaire.notEquals=" + DEFAULT_HORAIRE);

        // Get all the horaireList where horaire not equals to UPDATED_HORAIRE
        defaultHoraireShouldBeFound("horaire.notEquals=" + UPDATED_HORAIRE);
    }

    @Test
    @Transactional
    public void getAllHorairesByHoraireIsInShouldWork() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        // Get all the horaireList where horaire in DEFAULT_HORAIRE or UPDATED_HORAIRE
        defaultHoraireShouldBeFound("horaire.in=" + DEFAULT_HORAIRE + "," + UPDATED_HORAIRE);

        // Get all the horaireList where horaire equals to UPDATED_HORAIRE
        defaultHoraireShouldNotBeFound("horaire.in=" + UPDATED_HORAIRE);
    }

    @Test
    @Transactional
    public void getAllHorairesByHoraireIsNullOrNotNull() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        // Get all the horaireList where horaire is not null
        defaultHoraireShouldBeFound("horaire.specified=true");

        // Get all the horaireList where horaire is null
        defaultHoraireShouldNotBeFound("horaire.specified=false");
    }

    @Test
    @Transactional
    public void getAllHorairesByCoursIsEqualToSomething() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);
        Cours cours = CoursResourceIT.createEntity(em);
        em.persist(cours);
        em.flush();
        horaire.addCours(cours);
        horaireRepository.saveAndFlush(horaire);
        String coursId = cours.getId();

        // Get all the horaireList where cours equals to coursId
        defaultHoraireShouldBeFound("coursId.equals=" + coursId);

        // Get all the horaireList where cours equals to coursId + 1
        defaultHoraireShouldNotBeFound("coursId.equals=" + (coursId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHoraireShouldBeFound(String filter) throws Exception {
        restHoraireMockMvc.perform(get("/api/horaires?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horaire.getId())))
            .andExpect(jsonPath("$.[*].horaire").value(hasItem(DEFAULT_HORAIRE.toString())));

        // Check, that the count call also returns 1
        restHoraireMockMvc.perform(get("/api/horaires/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHoraireShouldNotBeFound(String filter) throws Exception {
        restHoraireMockMvc.perform(get("/api/horaires?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHoraireMockMvc.perform(get("/api/horaires/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingHoraire() throws Exception {
        // Get the horaire
        restHoraireMockMvc.perform(get("/api/horaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoraire() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        int databaseSizeBeforeUpdate = horaireRepository.findAll().size();

        // Update the horaire
        Horaire updatedHoraire = horaireRepository.findById(horaire.getId()).get();
        // Disconnect from session so that the updates on updatedHoraire are not directly saved in db
        em.detach(updatedHoraire);
        updatedHoraire
            .horaire(UPDATED_HORAIRE);
        HoraireDTO horaireDTO = horaireMapper.toDto(updatedHoraire);

        restHoraireMockMvc.perform(put("/api/horaires").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaireDTO)))
            .andExpect(status().isOk());

        // Validate the Horaire in the database
        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeUpdate);
        Horaire testHoraire = horaireList.get(horaireList.size() - 1);
        assertThat(testHoraire.getHoraire()).isEqualTo(UPDATED_HORAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingHoraire() throws Exception {
        int databaseSizeBeforeUpdate = horaireRepository.findAll().size();

        // Create the Horaire
        HoraireDTO horaireDTO = horaireMapper.toDto(horaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoraireMockMvc.perform(put("/api/horaires").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Horaire in the database
        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHoraire() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        int databaseSizeBeforeDelete = horaireRepository.findAll().size();

        // Delete the horaire
        restHoraireMockMvc.perform(delete("/api/horaires/{id}", horaire.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
