package com.example.springdb3;

import com.example.springdb3.entity.Distributor;
import com.example.springdb3.entity.Genre;
import com.example.springdb3.entity.Movie;
import com.example.springdb3.repos.DestributorRepository;
import com.example.springdb3.repos.MovieRepository;
import com.example.springdb3.utils.Utils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringDb3ApplicationTests {

/*    @Autowired
    protected EntityManagerFactory entityManagerFactory;*/
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private DestributorRepository destributorRepository;

    private List<Movie> movies;
    private EntityManager em;

/*    @Test
    void contextLoads() {
        Distributor d =  Utils.getRandomDistributor();
        System.out.println(d);
        destributorRepository.save(d);
        Movie m = Utils.getRandomMovie(d);
        System.out.println(m);
        movieRepository.save(m);
        for (Movie mov:
        movieRepository.findAll()) {
            System.out.println(mov);
        }
    }*/

    @Test
    void TestFindByProducer() {
        List<Movie> mv = movieRepository.findMovieBySingleProducer(1);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }

    @Test
    void TestFindByOneOfManyDirectors() {
        List<Integer> l = new ArrayList<Integer>();
        l.add(1);l.add(2);
        List<Movie> mv = movieRepository.findMovieByOneOfManyDirectors(l);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }

    @Test
    void TestFindMovieByManyDirectors() {
        List<Integer> l = new ArrayList<Integer>();
        l.add(1);l.add(2);
        List<Movie> mv = movieRepository.findMovieByManyDirectors(l,l.size());
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }

    @Test
    void TestFindByListGenresAndBoxOffice() {
        List<String> l = new ArrayList<String>();
        l.add(Genre.HORROR.toString());l.add(Genre.COMEDY.toString());
        List<Movie> mv = movieRepository.findMovieByManyGenresAndRangeBoxOffice(400000000,700000000,l,l.size());
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }

    @Test
    void TestFindByLowerAge() {
        List<Movie> mv = movieRepository.findMovieLowerAge(50);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }

    @Test
    void TestFindByRangeAge() {
        List<Movie> mv = movieRepository.findMovieRangeAge(30,50);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }

    @Test
    void TestFindByAny() {
        List<Movie> mv = movieRepository.findMovieByAnySearchMatch("producer1");
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }
}
