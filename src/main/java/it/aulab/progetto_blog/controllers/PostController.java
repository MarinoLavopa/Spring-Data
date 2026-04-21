package it.aulab.progetto_blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.aulab.progetto_blog.models.Author;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.AuthorRepository;
import it.aulab.progetto_blog.services.AuthorService;
import it.aulab.progetto_blog.services.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

     @GetMapping
    public String index(Model viewModel) {
        viewModel.addAttribute("title", "Posts"); // titolo della pagina web (themeplate engine+ server side rendering)
        viewModel.addAttribute("posts", postService.readAll()); // lista di post da passare al themeplate
        return "posts"; //nome themeplate presente nella cartella templates
    }

    @GetMapping("create")
    public String createPostView(Model viewModel) {
        viewModel.addAttribute("title","Create Post"); //titolo pagina web
        viewModel.addAttribute("post", new Post());                 // istanzia un nuovo post
        viewModel.addAttribute("authors", authorService.readAll()); // Passiamo la lista di tutti gli autori per poterli visualizzare nel form
        return "createPost";
    }
    

    @PostMapping
    public String createPost(@ModelAttribute("post") Post post, @RequestParam("authorId") Long authorId) {
        Author author = authorRepository.findById(authorId).orElse(null);
        post.setAuthor(author);
        postService.create(post);
        return "redirect:/posts";
}
    
}
