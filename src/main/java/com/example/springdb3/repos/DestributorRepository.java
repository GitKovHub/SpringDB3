package com.example.springdb3.repos;

import com.example.springdb3.entity.Distributor;
import com.example.springdb3.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestributorRepository extends JpaRepository<Distributor, Integer> {
}
