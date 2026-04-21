package it.aulab.progetto_blog.services;

import java.util.List;

import it.aulab.progetto_blog.dtos.AuthorDTO;
import it.aulab.progetto_blog.models.Author;

public interface AuthorService {
    List <AuthorDTO> readAll();
    AuthorDTO read(Long id);
    List <AuthorDTO> read(String email);
    List<AuthorDTO> read(String firstname, String lastname);
    AuthorDTO create(Author author);
    AuthorDTO update(Long id, Author author);
    void delete(Long id);
}
