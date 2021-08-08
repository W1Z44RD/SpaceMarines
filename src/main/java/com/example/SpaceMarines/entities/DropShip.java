package com.example.SpaceMarines.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DropShip {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shipId;
    @Column
    private String callsign;
    @Column
    private int age;
    @Column
    private int capacity;
    @OneToMany(mappedBy = "dropship")
    private List<Marine> marine;

    public void addMarine(Marine marine){
        this.marine.add(marine);
    }

    public List<Marine> getMarine() {
        return marine;
    }
}
