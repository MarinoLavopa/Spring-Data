package it.aulab.progetto_blog.services;

import java.util.List;

import it.aulab.progetto_blog.dtos.PostDTO;
import it.aulab.progetto_blog.models.Post;

public interface  PostService {
    List <PostDTO> readAll();
    PostDTO read(Long id);
    List <PostDTO> read(String email);
    List<PostDTO> read(String firstname, String lastname);
    PostDTO create(Post post);
    PostDTO update(Long id, Post author);
    void delete(Long id);
}
