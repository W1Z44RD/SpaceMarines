package com.example.SpaceMarines.entities;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String rank;
    @Column
    private int age;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DropShip")
    private DropShip ship;
    @Override
    public String toString(){
        return "The marines name is: " + name + ", his rank is: " + rank + " and he is: "+ age +".";
    }
    public void lowerCase(){
        this.setName(this.getName().toLowerCase());
        this.setRank(this.getRank().toLowerCase());
    }

    public void Capitalise(){
        this.setName(this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1));
    }
}
