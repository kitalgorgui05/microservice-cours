package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Horaire;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Horaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoraireRepository extends JpaRepository<Horaire, String>, JpaSpecificationExecutor<Horaire> {
}
