package com.example.springdb3.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "distributor")
public class Distributor {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "distributor")
    private Set<Movie> moviesDistributed;
}