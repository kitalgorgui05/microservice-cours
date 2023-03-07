package com.memoire.kital.raph.openFeign;

import com.memoire.kital.raph.restClient.MatiereClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${jhipster.clientApp.name}" , url = "http://localhost:8802")
public interface IMatiereRestClient {
    @GetMapping(value = "/api/matieres")
    ResponseEntity<List<MatiereClient>> getAllMatieres();

    @GetMapping(value = "/api/matieres/{id}")
     ResponseEntity<MatiereClient> getMatiere(@PathVariable("id") String id);
}
