package com.example.springdb3.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Random;


@Data
@Entity
@Table(name = "genre")
public class Genre{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "kind_of_genre")
    private String kindOfGenre;
}

/*    ACTION,
    ADVENTURE,
    COMEDY,
    DRAMA,
    HORROR,
    ROMANCE,
    SCI_FI,
    THRILLER;*/