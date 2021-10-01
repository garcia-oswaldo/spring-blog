package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repos.PostRepository;
import com.codeup.springblog.repos.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
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
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String showPosts(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String showOnePost(@PathVariable long id, Model model) {
        Post post = postDao.getById(id);
        return "post/show";
    }

    @GetMapping("/create")
    public String showCreatePostForm(Model model) {
        //we need to pass empty model to form
//        Post postToEdit =postDao.getById(id);
        model.addAttribute("post", new Post());

        return "create";
    }

    @GetMapping("/post/edit/{id}")
    public String viewEditForm(@PathVariable long id, Model model) {
        Post postToEdit = postDao.getById(id);
        model.addAttribute("postToEdit", postToEdit);
        return "edit";
    }

    @PostMapping("/post/edit/{id}")
    public String editPost(@PathVariable long id, @ModelAttribute Post postToEdit) {
//            Post post= postDao.getById(id);
//            post.setTitle(postToEdit.getTitle());
//            post.setDescription(postToEdit.getDescription());
        User currentLoggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postToEdit.setOwner(currentLoggedInUser);
        postToEdit.setId(id);
        postDao.save(postToEdit);
        return "redirect:/posts";
    }


    @PostMapping("/create")
    public String createPost(@ModelAttribute Post postToSubmit) {
        User currentLoggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postToSubmit.setOwner(currentLoggedInUser);
        postDao.save(postToSubmit);

        emailService.prepareAndSend(postToSubmit, "newPost", "You created a new Post");

        return "redirect:/posts";
    }

    @PostMapping("/post/delete/{id}")
    public String deletePost(@PathVariable long id) {
        postDao.deleteById(id);

        return "redirect:/posts";


    }
}
