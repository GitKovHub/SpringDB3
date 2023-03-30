package com.example.springdb3.repos;

import com.example.springdb3.entity.Genre;
import com.example.springdb3.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("SELECT u FROM Movie u WHERE u.minimumAge < ?1")
    List<Movie> findMovieLowerAge(int minimumAge);

    @Query("SELECT u FROM Movie u WHERE u.minimumAge >= ?1 and u.minimumAge <= ?2")
    List<Movie> findMovieRangeAge(int fromMinimumAge, int toMinimumAge);

    @Query(value = "SELECT * FROM Movie WHERE id IN " +
            "(SELECT movie_id FROM movie_producers WHERE producers_id = ?1)",
            nativeQuery = true)
    List<Movie> findMovieBySingleProducer(Integer id);

    @Query(value = "SELECT * FROM Movie WHERE id IN " +
            "(SELECT movie_id FROM movie_directors WHERE directors_id IN :ids)",
            nativeQuery = true)
    List<Movie> findMovieByOneOfManyDirectors(List<Integer> ids);

    @Query(value = "SELECT * FROM (SELECT u.* FROM Movie u INNER JOIN ( " +
            "SELECT id FROM movie_genre " +
            "WHERE genre IN :ids "+
            "GROUP BY id " +
            "HAVING COUNT(*) = :size " +
            ") ba ON u.id = ba.id) gm WHERE gm.box_office > :minBox and gm.box_office < :maxBox",
            nativeQuery = true)
    List<Movie> findMovieByManyGenresAndRangeBoxOffice(Integer minBox, Integer maxBox, List<String> ids, Integer size);

    @Query(value = "SELECT u.* FROM Movie u INNER JOIN ( " +
            "SELECT movie_id FROM movie_directors " +
            "WHERE directors_id IN :ids "+
            "GROUP BY movie_id " +
            "HAVING COUNT(*) = :size " +
            ") mo ON u.id = mo.movie_id",
            nativeQuery = true)
    List<Movie> findMovieByManyDirectors(List<Integer> ids, Integer size);

    //пошук за будь-яким параметром
    @Query(value = "SELECT b.* FROM Movie b WHERE " +
            "b.title LIKE CONCAT('%', ?1, '%') OR b.description LIKE CONCAT('%', ?1, '%') OR " +
            "b.id IN ( " +
            "SELECT ab.movie_id FROM movie_directors ab WHERE ab.directors_id IN ( " +
            "SELECT ab.directors_id FROM director a WHERE a.first_name LIKE CONCAT('%', ?1, '%') OR a.last_name LIKE CONCAT('%', ?1, '%'))" +
            ") "+
            "OR b.id IN ( " +
            "SELECT ap.movie_id FROM movie_producers ap WHERE ap.producers_id IN ( " +
            "SELECT ap.producers_id FROM producer c WHERE c.first_name LIKE CONCAT('%', ?1, '%') OR c.last_name LIKE CONCAT('%', ?1, '%'))" +
            ") "+
            "OR b.id IN ( " +
            "SELECT aa.movie_id FROM movie_actors aa WHERE aa.actors_id IN ( " +
            "SELECT aa.actors_id FROM producer d WHERE d.first_name LIKE CONCAT('%', ?1, '%') OR d.last_name LIKE CONCAT('%', ?1, '%'))" +
            ") ",
            nativeQuery = true)
    public List<Movie> findMovieByAnySearchMatch(String search);


/*    //пошук за будь-яким параметром
    @Query(value = "SELECT b.* FROM books b WHERE " +
            "b.name LIKE CONCAT('%', ?1, '%') OR b.publisher LIKE CONCAT('%', ?1, '%') OR " +
            "b.cover_type LIKE CONCAT('%', ?1, '%') " +
            "OR b.book_id IN ( " +
            "SELECT ab.book_id FROM author_book ab WHERE ab.author_id IN ( " +
            "SELECT a.author_id FROM authors a WHERE a.full_name LIKE CONCAT('%', ?1, '%') )" +
            ") "+
            "OR b.book_id IN ( " +
            "SELECT gb.book_id FROM genre_book gb WHERE gb.genre_id IN ( " +
            "SELECT g.genre_id FROM genres g WHERE g.genre_name LIKE CONCAT('%', ?1, '%') )" +
            ")",
            nativeQuery = true)
    public List<Book> findBookByAnySearchMatch(String search);*/

}
