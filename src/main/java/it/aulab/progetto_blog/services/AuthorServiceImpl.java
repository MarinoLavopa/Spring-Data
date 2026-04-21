package it.aulab.progetto_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progetto_blog.dtos.AuthorDTO;

import it.aulab.progetto_blog.models.Author;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
     private AuthorRepository authorRepository;

     @Autowired
     private ModelMapper mapper;

    @Override
    public List<AuthorDTO> readAll() {
        List<AuthorDTO> dtos= new ArrayList <AuthorDTO>(); //crea una lista vuota di DTO
        for(Author author : authorRepository.findAll()){  //per ogni autore nel repository 
            dtos.add(mapper.map(author, AuthorDTO.class)); //Per ogni Author lo converte in AuthorDto e lo aggiunge alla lista
            
        }
        return dtos;
    }

    @Override
    public AuthorDTO read(Long id) {
        Optional<Author> optAuthor= authorRepository.findById(id); //Optional gestisce la restituziuone di oggetti non presenti
        if(optAuthor.isPresent()){
            return mapper.map(optAuthor.get(), AuthorDTO.class);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author id: "+id+" not found");
        }
    }

    @Override
    public List<AuthorDTO> read(String email) {
        if(email==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null");
        List<AuthorDTO> dtos= new ArrayList <AuthorDTO>();
        for(Author author : authorRepository.findByEmail(email)){

            dtos.add(mapper.map(author, AuthorDTO.class));
        }
        return dtos;
    }

    @Override
    public List<AuthorDTO> read(String firstname, String lastname) {
        if(firstname==null || lastname==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        List<AuthorDTO> dtos= new ArrayList<AuthorDTO>();
        for(Author author : authorRepository.findByNameAndSurname(firstname, lastname)){
            dtos.add(mapper.map(author, AuthorDTO.class));
        }
        return dtos;
    }

    @Override
    public AuthorDTO create(Author author) {
        //Validation
        if(author.getEmail() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            return mapper.map(authorRepository.save(author), AuthorDTO.class);
    }

    @Override
    public AuthorDTO update(Long id, Author author) {
        if(authorRepository.existsById(id)){
            author.setId(id);
            return mapper.map(authorRepository.save(author), AuthorDTO.class);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void delete(Long id) {
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
