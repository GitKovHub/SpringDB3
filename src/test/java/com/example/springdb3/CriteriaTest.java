package com.example.springdb3;

import com.example.springdb3.entity.Genre;
import com.example.springdb3.entity.Movie;
import com.example.springdb3.sevice.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class CriteriaTest {

    @Autowired
    MovieService movieService;

    //отримати всі фільми конкретного продюсера
    @Test
    void TestFindByProducer() {
        List<Movie> mv = movieService.findMovieBySingleProducer(1);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }

    //всі фільми групи режисерів
    @Test
    void TestFindByAllDirectors() {
        List<Integer> l = new ArrayList<Integer>();
        l.add(1);l.add(2);
        List<Movie> mv = movieService.findMovieByManyDirectors(l);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }

    //всі фільми де вказаний хоч один з вказаних режисерів
    @Test
    void TestFindByOneDir() {
        List<Integer> l = new ArrayList<Integer>();
        l.add(1);l.add(2);
        List<Movie> mv = movieService.findMovieByOneDirectors(l);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }






    //всі фільми з мінімальним віком менше заданого
    @Test
    void TestFindByLowerAge() {
        List<Movie> mv = movieService.findMovieLowerAge(50);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }

    //всі фільми з мінімальним віком у діапазоні
    @Test
    void TestFindByRangeAge() {
        List<Movie> mv = movieService.findMovieRangeAge(30,50);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }

    //всі фільми з мінімальним віком у діапазоні
    @Test
    void TestFindByGenres() {
        List<Genre> l = new ArrayList<Genre>();
        l.add(Genre.COMEDY);l.add(Genre.HORROR);
        List<Movie> mv = movieService.allGenresAndBox(0,900000000,l);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());
    }




}
