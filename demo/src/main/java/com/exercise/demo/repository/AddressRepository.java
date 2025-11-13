package com.exercise.demo.repository;

import com.exercise.demo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;


//Handles database operations for the Address entity

public interface AddressRepository extends JpaRepository<Address, Long> {
}
