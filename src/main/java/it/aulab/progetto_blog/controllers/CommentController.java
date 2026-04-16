package it.aulab.progetto_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.repositories.CommentRepository;

@Controller
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @RequestMapping(value="/comments", method=RequestMethod.GET)
    public @ResponseBody List<Comment> getAllComment(){
        return commentRepository.findAll();
    }
}
