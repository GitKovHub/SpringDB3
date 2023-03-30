package com.example.springdb3.utils;

import com.example.springdb3.entity.Distributor;
import com.example.springdb3.entity.Genre;
import com.example.springdb3.entity.Movie;
import com.example.springdb3.entity.ppl.Actor;
import com.example.springdb3.entity.ppl.Director;
import com.example.springdb3.entity.ppl.Producer;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static Movie getRandomMovie(Distributor distributor) {
        Movie movie = new Movie();
        movie.setId(Long.valueOf(getRandomIntInRange(1, 1000000)));
        movie.setTitle(getRandomString(10));
        movie.setDescription(getRandomString(40));
        movie.setMinimumAge(getRandomIntInRange(1, 100));
        movie.setReleaseYear(getRandomIntInRange(1895,2050));
        movie.setBoxOffice(getRandomIntInRange(2000, 999999999));
        Set<Genre> genres = Set.of(Genre.randomGenre());
        int countOfGenres = getRandomIntInRange(1,4);
        for(int i = 0;i<countOfGenres;i++){
            Genre g = Genre.randomGenre();
            if(!genres.contains(g))genres.add(g);
        }
        movie.setGenres(genres);
        movie.setDistributor(distributor);
        return movie;
    }

    public static Distributor getRandomDistributor() {
        Distributor distributor = new Distributor();
        distributor.setId(Long.valueOf(getRandomIntInRange(1, 1000000)));
        return distributor;
    }

    public static String getRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder res = new StringBuilder();
        Random rnd = new Random();
        while (res.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            res.append(SALTCHARS.charAt(index));
        }
        return res.toString();
    }

    public static Integer getRandomIntInRange(int from, int till) {
        return ThreadLocalRandom.current().nextInt(from, till + 1);
    }

}
