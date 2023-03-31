package com.example.springdb3;

import com.example.springdb3.entity.Genre;
import com.example.springdb3.entity.Movie;
import com.example.springdb3.sevice.MovieService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        List<Integer> l = new ArrayList<Integer>();
        l.add(3);l.add(2);
        List<Movie> mv = movieService.allGenresAndBox(0,900000000,l);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());

        List<Movie> mv1 = movieService.allGenresAndBox(900000000,900000000,l);
        if(mv1.isEmpty())System.out.println("No result!");
        else {
            for (Movie mov :
                    mv1) {
                System.out.println(mov);
            }
        }
        assertTrue(mv1.isEmpty());
    }

    @Test
    void TestFindByAll() {
        List<Movie> mv = movieService.findByAll("Actor1");
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());

        List<Movie> mv1 = movieService.findByAll("Producer1");
        for (Movie mov:
                mv1) {
            System.out.println(mov);
        }
        assertFalse(mv1.isEmpty());

        List<Movie> mv2 = movieService.findByAll("4A8SB00L2SVC7E3JQRBV33UVWDZ29X3BUAM5HN2K");
        for (Movie mov:
                mv2) {
            System.out.println(mov);
        }
        assertFalse(mv2.isEmpty());
    }

    @Test
    void TestFindByParameterObject() {
        JSONObject j = new JSONObject();
        j.put("fieldName1", "description"); j.put("value1", "USLU1BSYZNCIY9331F67DOXQFP0GGOAJVJM1CZY6");
        j.put("fieldName2", "title"); j.put("value2", "Y8D8WFPUFF");
        j.put("operator", "OR");

        List<Movie> mv = movieService.findByParameters(j);
        for (Movie mov:
                mv) {
            System.out.println(mov);
        }
        assertFalse(mv.isEmpty());

        JSONObject j1 = new JSONObject();
        j1.put("fieldName1", "description"); j1.put("value1", "USLU1BSYZNCIY9331F67DOXQFP0GGOAJVJM1CZY6");
        j1.put("fieldName2", "title"); j1.put("value2", "Y8D8WFPUFF");
        j1.put("operator", "AND");

        List<Movie> mv1 = movieService.findByParameters(j1);
        for (Movie mov:
                mv1) {
            System.out.println(mov);
        }
        assertTrue(mv1.isEmpty());
    }

}
