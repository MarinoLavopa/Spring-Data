package it.aulab.progetto_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progetto_blog.dtos.CommentDTO;
import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.repositories.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CommentDTO> readAll() {
        List<CommentDTO> dtos = new ArrayList<CommentDTO>();
        for (Comment comment : commentRepository.findAll()) {
            CommentDTO dto = mapper.map(comment, CommentDTO.class);
            dto.setPostId(comment.getPost().getId());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public CommentDTO read(Long id) {
        Optional<Comment> optComment = commentRepository.findById(id);
        if (optComment.isPresent()) {
            CommentDTO dto = mapper.map(optComment.get(), CommentDTO.class);
            dto.setPostId(optComment.get().getPost().getId());
            return dto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }

    @Override
    public List<CommentDTO> readByPost(Long postId) {
        List<CommentDTO> dtos = new ArrayList<CommentDTO>();
        for (Comment comment : commentRepository.findByPostId(postId)) {
            CommentDTO dto = mapper.map(comment, CommentDTO.class);
            dto.setPostId(postId);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public CommentDTO create(Comment comment) {
        if (comment.getPost() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post is required");
        CommentDTO dto = mapper.map(commentRepository.save(comment), CommentDTO.class);
        dto.setPostId(comment.getPost().getId());
        return dto;
    }

    @Override
    public CommentDTO update(Long id, Comment comment) {
        if (commentRepository.existsById(id)) {
            comment.setId(id);
            CommentDTO dto = mapper.map(commentRepository.save(comment), CommentDTO.class);
            dto.setPostId(comment.getPost().getId());
            return dto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }

    @Override
    public void delete(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }
}