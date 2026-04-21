package it.aulab.progetto_blog.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progetto_blog.dtos.PostDTO;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<PostDTO> readAll() {
       List<PostDTO> dtos= new ArrayList <PostDTO>(); //crea una lista vuota di DTO
       for(Post post : postRepository.findAll()){   //per ogni autore nel repository
        PostDTO dto = mapper.map(post,PostDTO.class); // authorName e authorSurname non esistono in Post ma in Author,quindi li mappiamo manualmente dopo il mapping automatico
            dto.setAuthorName(post.getAuthor().getName());    
            dto.setAuthorSurname(post.getAuthor().getSurname()); 
            dtos.add(dto);
    }

    return dtos;
    }


    @Override
    public PostDTO read(Long id) {
        Optional<Post> optPost= postRepository.findById(id); //Optional gestisce la restituziuone di oggetti non presenti
        if(optPost.isPresent()){
            return mapper.map(optPost.get(), PostDTO.class);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    

    @Override
    public List<PostDTO> read(String email) {
        List<PostDTO> dtos= new ArrayList<PostDTO>();
        for(Post post : postRepository.findByAuthorEmail(email)){
            dtos.add(mapper.map(post, PostDTO.class));
        }
        return dtos;
    }

    @Override
    public List<PostDTO> read(String firstname, String lastname) {
       if(firstname==null || lastname==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        List<PostDTO> dtos= new ArrayList<PostDTO>();
        for(Post post : postRepository.findByAuthorNameAndAuthorSurname(firstname, lastname)){
            dtos.add(mapper.map(post, PostDTO.class));
        }
        return dtos;
    }
    

    @Override
    public PostDTO create(Post post) {
            if(post.getAuthor() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            return mapper.map(postRepository.save(post), PostDTO.class);
    }
    

    @Override
    public PostDTO update(Long id, Post post ) {
       if(postRepository.existsById(id)){
            post.setId(id);
            return mapper.map(postRepository.save(post), PostDTO.class);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    

        @Override
    public void delete(Long id) {
        if(postRepository.existsById(id)){
            postRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    

    }

}
