package it.aulab.progetto_blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.aulab.progetto_blog.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
