package com.exercise.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.demo.entity.Country;


//Manages the Country entity
public interface CountryRepository extends JpaRepository<Country, String> {


}
