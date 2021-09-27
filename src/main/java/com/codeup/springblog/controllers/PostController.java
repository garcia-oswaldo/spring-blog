package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repos.PostRepository;
import com.codeup.springblog.repos.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao= userDao;
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
    public String showCreatePostForm() {
        return "create";
    }

    @PostMapping("/create")
    public String createPost(@RequestParam(name="title") String title,
                             @RequestParam(name="description")String description) {
        User userToDisplay = userDao.getByUsername("ogarcia");
// add user owner to the post

        postDao.save(new Post(title,description,userToDisplay));

        return "redirect:/posts";
    }

}
