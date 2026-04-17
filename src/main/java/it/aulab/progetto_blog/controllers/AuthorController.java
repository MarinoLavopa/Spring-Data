package it.aulab.progetto_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progetto_blog.models.Author;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.AuthorRepository;


@RestController // equivale a @Controller + @ResponseBody
@RequestMapping("/authors")  // prefisso dei suoi URI sarà /authors
public class AuthorController {
    @Autowired
    AuthorRepository authorRepository;

    @GetMapping
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    @GetMapping("{id}")
    public Author getAuthor(@PathVariable("id") Long id){
        return authorRepository.findById(id).get();
    }

    @PostMapping // equivalente a @RequestMapping(method=RequestMethod.POST)
    public Author createAuthor(@RequestBody Author author){
        return authorRepository.save(author);
    }

    @PutMapping("{id}") // abbiamo modificato Giuseppe Verdi in Giuseppe Garibaldi
    public Author updateAuthor(@PathVariable("id") Long id, @RequestBody Author author){
        author.setId(id);
        return authorRepository.save(author);
    }

    @DeleteMapping("{id}") // eliminiamo Giuseppe Garibaldi
    public void deleteAuthor(@PathVariable("id") Long id){
        //metodo if per controllare se l'aiutore esiste lo eliminiamo, altrimenti lanciamo il messaggio di errore.
        if(authorRepository.existsById(id)){
            Author author= authorRepository.findById(id).get();
            List<Post> authorPosts= author.getPosts();
            for(Post post: authorPosts){ // per ogni post che ha l'author, settiamo l'author a null per distaccare(sganciare) l'autore dal post e poterlo eliminare.
                post.setAuthor(null);
            }
            authorRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }
    }

}
