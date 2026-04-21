package it.aulab.progetto_blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.aulab.progetto_blog.models.Author;
import it.aulab.progetto_blog.services.AuthorService;




@Controller
@RequestMapping("/authors") // nome theplate presente nella cartella templates
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping
    public String index(Model viewModel) {
        viewModel.addAttribute("title", "Authors"); // titolo della pagina web (themeplate engine+ server side rendering)
        viewModel.addAttribute("authors", authorService.readAll()); // lista di autori da passare al themeplate
        return "authors"; //nome themeplate presente nella cartella templates
    }

    @GetMapping("create")
    public String createAuthorView(Model viewModel) {
        viewModel.addAttribute("title","Create Author"); //titolo pagina web
        viewModel.addAttribute("author", new Author());                 // istanzia un nuovo autore
        return "createAuthor";
    }
    

    @PostMapping
    public String createAuthor(@ModelAttribute("author") Author author){   // ModelAttribute dice al metodo di catturare l'attributo "author" dal form 
        authorService.create(author);                                      //per memorizzare il nuovo dato interroghiamo sempre il Service ed il metodo create
        return "redirect:/authors";                                        // Reindirizza alla pagina /authors
    }
    
    
}
