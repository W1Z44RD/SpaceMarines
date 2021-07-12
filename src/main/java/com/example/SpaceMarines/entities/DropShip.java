package com.example.SpaceMarines.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DropShip {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String callsign;
    @Column
    private int age;
    @Column
    private int capacity;
    @Column
//    @JsonIgnore
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private List<Marine> crew;

    public void addMarine(Marine marine){
        this.crew.add(marine);
        log.info(this.getCrew().toString());
    }

    public List<Marine> getCrew() {
        return crew;
    }
}
