package com.exercise.demo.web.controller;


import com.exercise.demo.entity.Country;
import com.exercise.demo.entity.Employee;
import com.exercise.demo.repository.CountryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


//allows the system to create and retrieve countries

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryRepository countryRepo;

    public CountryController(CountryRepository countryRepo) {
        this.countryRepo = countryRepo;
    }


    //get all countries
    @GetMapping
    public List<Country> all() {
        return countryRepo.findAll();
    }


    //get a country
    @GetMapping("/{name}")
    public ResponseEntity<Country> getOne(@PathVariable String name) {
        return countryRepo.findById(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //post a new country
    @PostMapping
    public ResponseEntity<Country> create(@RequestBody Country country) {

        Country saved = countryRepo.save(country);
        return ResponseEntity.created(URI.create("/api/countries/" + saved.getName())).body(saved);

    }
}
