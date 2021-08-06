package com.example.SpaceMarines.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Marine {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long marineId;
    @Column
    private String name;
    @Column
    private String rank;
    @Column
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id")
    private DropShip dropship;

    public void lowerCase(){
        this.setName(this.getName().toLowerCase());
        this.setRank(this.getRank().toLowerCase());
    }

    public void capitalise(){
        this.setName(this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1));
    }
}
