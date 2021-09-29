package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repos.PostRepository;
import com.codeup.springblog.repos.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao= userDao;
        this.emailService= emailService;
    }

    @GetMapping("/posts")
    public String showPosts(Model model) {
        model.addAttribute("posts",postDao.findAll());
        return "posts";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String showOnePost(@PathVariable int id) {
        return "view an individual post by id of" + id;
    }

    @GetMapping("/create")
    public String showCreatePostForm(Model model) {
         //we need to pass empty model to form
//        Post postToEdit =postDao.getById(id);
        model.addAttribute("post", new Post());

        return "create";
    }

@GetMapping("/post/edit/{id}")
public String showEditPostForm(@PathVariable long id, Model model){
        Post postToEdit=postDao.getById(id);
        model.addAttribute("postToEdit", postToEdit);
                return "redirect:/posts";
//@PostMapping("/post/edit/{id}")
}

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post postToSubmit) {
//        User userToDisplay = userDao.getByUsername("ogarcia");
// add user owner to the post
        postToSubmit.setOwner(userDao.getById(1L));
        emailService.prepareAndSend(postToSubmit, "newPost","You created a new Post");
        postDao.save(postToSubmit);
        return "redirect:/posts";
    }

}
