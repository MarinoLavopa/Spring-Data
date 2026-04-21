package it.aulab.progetto_blog.services;

import java.util.List;

import it.aulab.progetto_blog.dtos.CommentDTO;
import it.aulab.progetto_blog.models.Comment;

public interface CommentService {
    List<CommentDTO> readAll();
    CommentDTO read(Long id);
    List<CommentDTO> readByPost(Long postId);
    CommentDTO create(Comment comment);
    CommentDTO update(Long id, Comment comment);
    void delete(Long id);
}