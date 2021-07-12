package com.example.SpaceMarines.service;

import com.example.SpaceMarines.entities.DropShip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityShipsInserter extends JpaRepository<DropShip, Long> {
}
