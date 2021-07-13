package com.example.SpaceMarines.controller;

import com.example.SpaceMarines.constants.Constant;
import com.example.SpaceMarines.entities.DropShip;
import com.example.SpaceMarines.entities.Marine;
import com.example.SpaceMarines.service.EntityMarinesInserter;
import com.example.SpaceMarines.service.EntityShipsInserter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "marine")
public class MarineController {
    @Autowired
    private EntityMarinesInserter entityMarinesInserter;
    @Autowired
    private EntityShipsInserter entityShipsInserter;

    @GetMapping("/test")
    public ResponseEntity test(){
        return new ResponseEntity(Constant.OK, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity getMarines(){
        return new ResponseEntity(entityMarinesInserter.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return new ResponseEntity(entityMarinesInserter.findById(id), HttpStatus.OK);
    }

    @GetMapping("/get/name/{name}")
    public ResponseEntity<Marine> getByName(@PathVariable String name){
        return new ResponseEntity<>(entityMarinesInserter.findByName(name), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addMarine(@RequestBody Marine marine) {
        try {
            marine.lowerCase();
            marine.Capitalise();
            log.info(String.valueOf(marine));
            entityMarinesInserter.save(marine);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity(e.toString(),HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) throws Exception {
        Marine marine = entityMarinesInserter.findById(id).orElseThrow(() -> new Exception(Constant.NOT_FOUND));
        entityMarinesInserter.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/patch/rank/{id}")
    public ResponseEntity updateRank(@PathVariable Long id, @RequestBody String rank) throws Exception {
        Marine marine = entityMarinesInserter.findById(id).orElseThrow(() -> new Exception(Constant.NOT_FOUND));
        marine.setRank(rank);
        return new ResponseEntity(Constant.OK, HttpStatus.OK);
    }

    @PutMapping("/put/ship/{id}/{shipId}")
    public ResponseEntity updateShip(@PathVariable Long id, @PathVariable Long shipId) throws Exception {
        Marine marine = entityMarinesInserter.findById(id).orElseThrow(() -> new Exception(Constant.NOT_FOUND));
        DropShip ship = entityShipsInserter.findById(shipId).orElseThrow(() -> new Exception(Constant.NOT_FOUND));
        marine.setDropship(ship);
        ship.addMarine(marine);
        entityMarinesInserter.save(marine);
        entityShipsInserter.saveAndFlush(ship);
        return new ResponseEntity(Constant.OK, HttpStatus.OK);
    }
}
