package it.aulab.progetto_blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import it.aulab.progetto_blog.models.Author;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {
    List<Author> findByName(String firstname);
    List<Author> findBySurname(String lastname);
    List<Author> findByNameAndSurname(String firstname, String lastname);

    // Query Nativa
    @Query(value="SELECT * FROM authors a WHERE a.Firstname = 'Mario' ", nativeQuery=true)
    List<Author> authorsWithSameName();

    // Query non Nativa
    @Query("SELECT a FROM Author a WHERE a.name = 'Mario'")
    List<Author> authorsWithSameNameNoNative();
}
