package it.aulab.progetto_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.PostRepository;


@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @RequestMapping(value="/posts", method=RequestMethod.GET)
    public @ResponseBody List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    
    

}
