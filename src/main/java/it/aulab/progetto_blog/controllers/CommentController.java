package it.aulab.progetto_blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.PostRepository;
import it.aulab.progetto_blog.services.CommentService;
import it.aulab.progetto_blog.services.PostService;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;
@GetMapping
    public String index(Model viewModel) {
        viewModel.addAttribute("title", "Comments"); // titolo della pagina web (themeplate engine+ server side rendering)
        viewModel.addAttribute("comments", commentService.readAll()); // lista di post da passare al themeplate
        return "comments"; //nome themeplate presente nella cartella templates
    }

    @GetMapping("create")
    public String createPostView(Model viewModel) {
        viewModel.addAttribute("title","Create Comment"); //titolo pagina web
        viewModel.addAttribute("comment", new Comment());                 // istanzia un nuovo post
        viewModel.addAttribute("posts", postService.readAll()); // Passiamo la lista di tutti gli autori per poterli visualizzare nel form
        return "createComment";
    }
    

    @PostMapping
    public String createComment(@ModelAttribute("comment") Comment comment, @RequestParam("postId") Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        comment.setPost(post);
        commentService.create(comment);
        return "redirect:/comments";
    }
}
