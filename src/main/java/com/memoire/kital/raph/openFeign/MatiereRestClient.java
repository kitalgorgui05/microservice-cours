package com.memoire.kital.raph.openFeign;

import com.memoire.kital.raph.restClient.MatiereClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "classe")
public interface MatiereRestClient {

    @PostMapping(value = "/api/matieres")
    ResponseEntity<MatiereClient> createMatiere(@Valid @RequestBody MatiereClient matiereDTO);

    @PutMapping(value = "/api/matieres")
    ResponseEntity<MatiereClient> updateMatiere(@Valid @RequestBody MatiereClient matiereDTO);

    @GetMapping(value = "/api/matieres")
    ResponseEntity<List<MatiereClient>> getAllMatieres();

    @GetMapping(value = "/api/matieres/{id}")
    ResponseEntity<MatiereClient> getMatiere(@PathVariable String id);
}
