package com.memoire.kital.raph.openFeign;

import com.memoire.kital.raph.restClient.AnneeClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "inscription" , url = "http://localhost:9081/")
public interface AnneRestClient {
    @GetMapping("/api/annees")
    public ResponseEntity<List<AnneeClient>> getAllAnnees();
    @GetMapping("/api/annees/{id}")
    public ResponseEntity<AnneeClient> getAnnee(@PathVariable("id") String id);
}
