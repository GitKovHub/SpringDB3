package com.example.springdb3.sevice;

import com.example.springdb3.entity.Genre;
import com.example.springdb3.entity.Movie;
import com.example.springdb3.entity.ppl.Actor;
import com.example.springdb3.entity.ppl.Director;
import com.example.springdb3.entity.ppl.Producer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MovieService {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersiSet");
    public EntityManager entityManager = emf.createEntityManager();
    public CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    public CriteriaQuery<Movie> criteriaQuery = cb.createQuery(Movie.class);


    public List<Movie> findAllMovies() {
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> from = cq.from(Movie.class);
        cq.select(from);
        TypedQuery<Movie> q = entityManager.createQuery(cq);
        List<Movie> allitems = q.getResultList();
        return allitems;
    }

    //знайти книжку за ціновим діапазоном
    public List<Movie> findMovieLowerAge(int max) {
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> from = criteriaQuery.from(Movie.class);
        cq.select(from);
        Predicate maximum = cb.lessThan(from.get("minimumAge"),max);
        criteriaQuery.where(maximum);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    //знайти книжку за ціновим діапазоном
    public List<Movie> findMovieRangeAge(int min, int max) {
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> from = criteriaQuery.from(Movie.class);
        cq.select(from);
        Predicate maximum = cb.lessThanOrEqualTo(from.get("minimumAge"),max);
        Predicate minimum = cb.greaterThanOrEqualTo(from.get("minimumAge"), min);
        Predicate finalPredicate = cb.and(maximum, minimum);
        criteriaQuery.where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Movie> findMovieByManyDirectors(List<Integer> ids) {
        // Initialize the query
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movie = cq.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();

        // Iterate over each courseName we want to match with
        for(Integer id : ids) {
            // Initialize the subquery
            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<Movie> subqueryMovie = subquery.from(Movie.class);
            Join<Director, Movie> subqueryDirector = subqueryMovie.join("directors");

            // Select the Student ID where one of their courses matches
            subquery.select(subqueryMovie.get("id")).where(
                    cb.equal(subqueryDirector.get("id"), id));

            // Filter by Students that match one of the Students found in the subquery
            predicates.add(cb.in(movie.get("id")).value(subquery));
        }

        // Use all predicates above to query
        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Movie> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    public List<Movie> findMovieBySingleProducer(Integer id) {
        // Initialize the query
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movie = cq.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();

            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<Movie> subqueryMovie = subquery.from(Movie.class);
            Join<Producer, Movie> subqueryProducer = subqueryMovie.join("producers");

            // Select the Student ID where one of their courses matches
            subquery.select(subqueryMovie.get("id")).where(
                    cb.equal(subqueryProducer.get("id"), id));

            // Filter by Students that match one of the Students found in the subquery
            predicates.add(cb.in(movie.get("id")).value(subquery));

        // Use all predicates above to query
        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Movie> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    public List<Movie> findMovieByOneDirectors(List<Integer> ids) {
        // Initialize the query
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movie = cq.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();

        // Iterate over each courseName we want to match with
        for(Integer id : ids) {
            // Initialize the subquery
            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<Movie> subqueryMovie = subquery.from(Movie.class);
            Join<Director, Movie> subqueryDirector = subqueryMovie.join("directors");

            // Select the Student ID where one of their courses matches
            subquery.select(subqueryMovie.get("id")).where(
                    cb.equal(subqueryDirector.get("id"), id));

            // Filter by Students that match one of the Students found in the subquery
            predicates.add(cb.in(movie.get("id")).value(subquery));
        }

        // Use all predicates above to query
        cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Movie> query = entityManager.createQuery(cq);

        return query.getResultList();
    }


    public List<Movie> allGenresAndBox(Integer min, Integer max, List<Integer> ids) {
        // Initialize the query
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movie = cq.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();

        // Iterate over each courseName we want to match with
        for(Integer g : ids) {
            // Initialize the subquery
            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<Movie> subqueryMovie = subquery.from(Movie.class);
            Join<Genre, Movie> subqueryGenres = subqueryMovie.join("genres");

            // Select the Student ID where one of their courses matches
            subquery.select(subqueryMovie.get("id")).where(
                    cb.equal(subqueryGenres.get("id"), g));

            // Filter by Students that match one of the Students found in the subquery
            predicates.add(cb.in(movie.get("id")).value(subquery));
        }
        predicates.add(cb.greaterThanOrEqualTo(movie.get("minimumAge"), min));
        predicates.add(cb.lessThanOrEqualTo(movie.get("minimumAge"),max));
        // Use all predicates above to query
        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Movie> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    public List<Movie> findByAll(String str) {
        // Initialize the query
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movie = cq.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();

            // Genres
            Subquery<Long> subqueryGenres = cq.subquery(Long.class);
            Root<Movie> subqueryMovie = subqueryGenres.from(Movie.class);
            Join<Genre, Movie> subqueryGenresMovie = subqueryMovie.join("genres");
            subqueryGenres.select(subqueryMovie.get("id")).where(cb.equal(subqueryGenresMovie.get("kindOfGenre"), str));
            predicates.add(cb.in(movie.get("id")).value(subqueryGenres));


            // Actors
            Subquery<Long> subqueryActors = cq.subquery(Long.class);
            Root<Movie> subqueryMovie1 = subqueryActors.from(Movie.class);
            Join<Actor, Movie> subqueryActorsMovie = subqueryMovie1.join("actors");
            subqueryActors.select(subqueryMovie1.get("id")).where(cb.equal(subqueryActorsMovie.get("firstName"), str));
            subqueryActors.select(subqueryMovie1.get("id")).where(cb.equal(subqueryActorsMovie.get("lastName"), str));
            predicates.add(cb.in(movie.get("id")).value(subqueryActors));

            // Director
            Subquery<Long> subqueryDirectors = cq.subquery(Long.class);
            Root<Movie> subqueryMovie2 = subqueryDirectors.from(Movie.class);
            Join<Director, Movie> subqueryDirectorsMovie = subqueryMovie2.join("directors");
            subqueryDirectors.select(subqueryMovie2.get("id")).where(cb.equal(subqueryDirectorsMovie.get("firstName"), str));
            subqueryDirectors.select(subqueryMovie2.get("id")).where(cb.equal(subqueryDirectorsMovie.get("lastName"), str));
            predicates.add(cb.in(movie.get("id")).value(subqueryDirectors));

            // Producer
            Subquery<Long> subqueryProducer = cq.subquery(Long.class);
            Root<Movie> subqueryMovie3 = subqueryProducer.from(Movie.class);
            Join<Producer, Movie> subqueryProducersMovie = subqueryMovie3.join("producers");
            subqueryProducer.select(subqueryMovie3.get("id")).where(cb.equal(subqueryProducersMovie.get("firstName"), str));
            subqueryProducer.select(subqueryMovie3.get("id")).where(cb.equal(subqueryProducersMovie.get("lastName"), str));
            predicates.add(cb.in(movie.get("id")).value(subqueryProducer));

            predicates.add(cb.equal(movie.get("description"), str));
            predicates.add(cb.equal(movie.get("title"), str));

        // Use all predicates above to query
        cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Movie> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    public List<Movie> findByParameters(JSONObject obj) {
        // Initialize the query
        String fieldName1 = obj.getString("fieldName1");
        String value1 = obj.getString("value1");
        String fieldName2 = obj.getString("fieldName2");
        String value2 = obj.getString("value2").toString();
        String operator = obj.getString("operator");

        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movie = cq.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(movie.get(fieldName1), value1));
        predicates.add(cb.equal(movie.get(fieldName2), value2));
        if(operator == "OR"){
            cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
        }else if (operator == "AND"){
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }else{
            try {
                throw new Exception("Incorrect operator");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        TypedQuery<Movie> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

}
