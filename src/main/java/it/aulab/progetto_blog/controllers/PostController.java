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
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.PostRepository;




@RestController // @Controller + @ResponseBody
@RequestMapping("/posts") // prefisso dell'URI sarà /posts
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @GetMapping("{id}")
    public Post getPost(@PathVariable("id") Long id){
        return postRepository.findById(id).get();
    }

    @PostMapping // equivalente a @RequestMapping(method=RequestMethod.POST)
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("{id}") //modifichiamo il post/4 di /author/3 ("post di Franco Neri")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        post.setId(id);
        return postRepository.save(post);
    }

    @DeleteMapping("{id}") 
    public void deletePost(@PathVariable("id") Long id){
        if(postRepository.existsById(id)){
            Post post=postRepository.findById(id).get();
            List<Comment> postComments=post.getComments();
            for(Comment comment: postComments){ // per ogni commento che ha il post, settiamo il post a null per distaccare(sganciare) il post dal commento e poterlo eliminare.
                comment.setPost(null);
            }
            postRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");

        }
    }


    
    
    
    

}
