package it.aulab.progetto_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.aulab.progetto_blog.dtos.AuthorDTO;
import it.aulab.progetto_blog.models.Author;
import it.aulab.progetto_blog.services.AuthorService;


@RestController // equivale a @Controller + @ResponseBody
@RequestMapping("/api/authors")  // prefisso dei suoi URI sarà /authors
public class AuthorRestController {
   
    @Autowired
    AuthorService authorService;

    @GetMapping
    public List<AuthorDTO> getAllAuthors(){
        return authorService.readAll();
    }

    @GetMapping("{id}")
    public AuthorDTO getAuthor(@PathVariable("id") Long id){
        return authorService.read(id);
    }

    @PostMapping // equivalente a @RequestMapping(method=RequestMethod.POST)
    public AuthorDTO createAuthor(@RequestBody Author author){
        return authorService.create(author);
    }

    @PutMapping("{id}") // abbiamo modificato Giuseppe Verdi in Giuseppe Garibaldi
    public AuthorDTO updateAuthor(@PathVariable("id") Long id, @RequestBody Author author){
        // author.setId(id);
        return authorService.update(id,author);
    }

    @DeleteMapping("{id}") // eliminiamo Giuseppe Garibaldi
    public void deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);
    }

}
