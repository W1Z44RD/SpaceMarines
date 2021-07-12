package com.example.SpaceMarines.service;


import com.example.SpaceMarines.entities.Marine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EntityMarinesInserter extends CrudRepository<Marine, Long> {
    Marine findByName(String name);
}
