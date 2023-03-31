package com.example.springdb3.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "distributor")
public class Distributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "distributor_name")
    private String distributorName;
    @OneToMany(mappedBy = "distributor")
    private Set<Movie> moviesDistributed;
}