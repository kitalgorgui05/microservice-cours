package com.memoire.kital.raph.openFeign;

import com.memoire.kital.raph.restClient.AnneeClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${jhipster.clientApp.name}" , url = "http://localhost:8888")
public interface IAnneRestClient {
    @GetMapping("/api/annees")
    public ResponseEntity<List<AnneeClient>> getAllAnnees();
    @GetMapping("/api/annees/{id}")
    public ResponseEntity<AnneeClient> getAnnee(@PathVariable(name = "id") String id);
}
