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

import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.repositories.CommentRepository;




@RestController // @Controller + @ResponseBody
@RequestMapping("/comments") // prefisso dell URI sarà /comments
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @GetMapping // equivalente a @RequestMapping
    public  List<Comment> getAllComment(){
        return commentRepository.findAll();
    }

    @GetMapping("{id}")
    public Comment getComment(@PathVariable("id") Long id){ 
        return commentRepository.findById(id).get();
    } 

    @PostMapping // equivalente a @RequestMapping(method=RequestMethod.POST)
    public Comment createComment(@RequestBody Comment comment){
        return commentRepository.save(comment);

    }

    @PutMapping("{id}") // modifichiamo il comment/3 di /post/4
    public Comment updateComment(@PathVariable ("id") Long id, @RequestBody Comment comment) {
        comment.setId(id);
        return commentRepository.save(comment);
    }

    @DeleteMapping("{id}") // eliminiamo il comment/3 di /post/4  qui non serve la logia di distaccare il commento dal post perche' il commento e' unico per ogni post 
    public void deleteComment(@PathVariable("id") Long id){
        if(commentRepository.existsById(id)){
            Comment comment= commentRepository.findById(id).get();
            comment.setPost(null);
            commentRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }



    

    
}
