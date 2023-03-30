package com.example.springdb3.entity.ppl;

import com.example.springdb3.entity.Movie;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "director")
public class Director{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToMany(mappedBy = "directors")
    Set<Movie> directedMovies;
}