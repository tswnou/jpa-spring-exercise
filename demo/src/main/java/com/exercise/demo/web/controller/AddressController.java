package com.exercise.demo.web.controller;

import com.exercise.demo.entity.Address;
import com.exercise.demo.entity.Country;
import com.exercise.demo.repository.AddressRepository;
import com.exercise.demo.repository.CountryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressRepository addressRepo;
    private final CountryRepository countryRepo;



    //injects the two repositories automatically
    public AddressController(AddressRepository addressRepo, CountryRepository countryRepo) {
        this.addressRepo = addressRepo;
        this.countryRepo = countryRepo;
    }


    //get all addresses in db
    @GetMapping
    public List<Address> all() {
        return addressRepo.findAll(); // standard CRUD operation
    }



    //Looks up an Address using the path ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getOne(@PathVariable Long id) {
        return addressRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //post a new address
    @PostMapping
    public ResponseEntity<Address> create(@RequestBody Address address) {

        //checks if the country already exists
        if (address.getCountry() != null && address.getCountry().getName() != null) {
            Country existing = countryRepo.findById(address.getCountry().getName()).orElse(null);
            if (existing != null) {
                address.setCountry(existing);
            }
        }

        //Saves the new address

        Address saved = addressRepo.save(address);
        return ResponseEntity
                .created(URI.create("/api/addresses/" + saved.getId()))
                .body(saved);

    }
}
