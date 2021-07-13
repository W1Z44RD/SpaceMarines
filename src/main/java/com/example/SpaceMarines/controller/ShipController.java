package com.example.SpaceMarines.controller;

import com.example.SpaceMarines.constants.Constant;
import com.example.SpaceMarines.entities.DropShip;
import com.example.SpaceMarines.service.EntityShipsInserter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(path = "ship")
public class ShipController {
    @Autowired
    private EntityShipsInserter entityShipsInserter;

    @GetMapping("/get/all")
    public ResponseEntity<Collection<DropShip>> getShips(){
        return new ResponseEntity<>(entityShipsInserter.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getShipById(@PathVariable Long id){
        return new ResponseEntity(entityShipsInserter.findById(id), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity addShip(@RequestBody DropShip dropShip) {
        try {
            entityShipsInserter.save(dropShip);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity(e.toString(),HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws Exception {
        DropShip dropShip = entityShipsInserter.findById(id).orElseThrow(() -> new Exception(Constant.NOT_FOUND));
        entityShipsInserter.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
