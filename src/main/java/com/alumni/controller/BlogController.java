package com.alumni.controller;

import com.alumni.dto.AlumniDTO;
import com.alumni.dto.BlogDTO;
import com.alumni.services.impl.AlumniServiceImpl;
import com.alumni.services.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(path = "/blog")
public class BlogController {

    @Autowired
    private BlogServiceImpl blogService;

    @Autowired
    private AlumniServiceImpl alumniService;

    @GetMapping(path = "/add")
    public String addBlog(Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            AlumniDTO alumni = this.alumniService.getAlumniDTOIfLoggedIn(principal);
            model.addAttribute("name", alumni.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }

        model.addAttribute("blogDTO", new BlogDTO());
        model.addAttribute("message", "");
        return "authenticated/blog/add-blog";
    }

    @PostMapping(path = "/save")
    public String saveBlog(@Valid @ModelAttribute("BlogDTO") BlogDTO blogDTO, BindingResult result,
                           Model model, Principal principal) {

        if (principal != null) {
            // get logged-in username
            AlumniDTO alumni = this.alumniService.getAlumniDTOIfLoggedIn(principal);
            model.addAttribute("name", alumni.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }

        BlogDTO resultBlogDTO = this.blogService
                .addBlog(blogDTO, principal);

        model.addAttribute("blogDTO", new BlogDTO());
        model.addAttribute("message", "blog is successfully added...");
        return "authenticated/blog/add-blog";
    }

    // get all blog
    @GetMapping(path = "/all")
    public String getAllBlog(Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            AlumniDTO alumni = this.alumniService.getAlumniDTOIfLoggedIn(principal);
            model.addAttribute("name", alumni.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        model.addAttribute("blogDTOS", this.blogService.getAllBlogs());
        return "authenticated/blog/show-all-blog";
    }

    // get blog by id
    @GetMapping(path = "/get/{blogId}")
    public String getAllBlogByBlogId(@PathVariable("blogId") Long blogId,
                              Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            AlumniDTO alumni = this.alumniService.getAlumniDTOIfLoggedIn(principal);
            model.addAttribute("name", alumni.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        model.addAttribute("blogDTO", this.blogService.getSingleBlogById(blogId));
        return "authenticated/blog/show-single-blog";
    }

    // get all blog by alumni
    @GetMapping(path = "/get/alumni/{alumniId}")
    public String getAllBlogsAlumniId(@PathVariable("alumniId") Long alumniId,
                             Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            AlumniDTO alumni = this.alumniService.getAlumniDTOIfLoggedIn(principal);
            model.addAttribute("name", alumni.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        model.addAttribute("blogDTOS", this.blogService.getAllBlogs());
        return "authenticated/blog/show-all-blog";
    }

}
