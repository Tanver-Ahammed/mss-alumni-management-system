package com.alumni.services.impl;

import com.alumni.dto.BlogDTO;
import com.alumni.entities.Alumni;
import com.alumni.entities.Blog;
import com.alumni.exception.ResourceNotFoundException;
import com.alumni.repositories.AlumniRepository;
import com.alumni.repositories.BlogRepository;
import com.alumni.services.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AlumniRepository alumniRepository;

    @Autowired
    private AlumniServiceImpl alumniService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BlogDTO addBlog(BlogDTO blogDTO, Principal principal) {
        Alumni alumni = this.alumniRepository.findByEmail(principal.getName());
        Blog blog = this.dtoToBlog(blogDTO);
        blog.setDate(new Date().toString());
        blog.setAlumni(alumni);
        return this.blogToDTO(this.blogRepository.save(blog));
    }

    @Override
    public List<BlogDTO> getAllBlogs() {
        List<BlogDTO> blogDTOS = new ArrayList<>();
        for (Blog blog : this.blogRepository.findAll()) {
            BlogDTO blogDTO = this.blogToDTO(blog);
            if (blog.getDescription().length() > 41)
                blogDTO.setDescription(blog.getDescription().substring(0, 40));
            blogDTOS.add(blogDTO);
        }
        return blogDTOS;
    }

    @Override
    public List<BlogDTO> getAllBlogsByAlumni(Long alumniId) {
        Alumni alumni = this.alumniService.getAlumniById(alumniId);
        return this.blogRepository
                .findByAlumni(alumni)
                .stream()
                .map(this::blogToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDTO getSingleBlogById(Long blogId) {
        return this.blogToDTO(this.getBlogById(blogId));
    }

    @Override
    public void deleteBlog(Long blogId) {
        Blog blog = this.getBlogById(blogId);
        this.blogRepository.delete(blog);
    }

    @Override
    public BlogDTO updateBlog(Long blogId, BlogDTO blogDTO) {
        Blog blog = this.getBlogById(blogId);
        blog.setTitle(blogDTO.getTitle());
        blog.setDate(String.valueOf(new Date()));
        blog.setDescription(blogDTO.getDescription());
        return this.blogToDTO(this.blogRepository.save(blog));
    }

    // get blog bu id
    public Blog getBlogById(Long blogId) {
        return this.blogRepository.findById(blogId).orElseThrow(() ->
                new ResourceNotFoundException("Blog", "id", blogId));
    }

    // blog to dto
    public BlogDTO blogToDTO(Blog blog) {
        return this.modelMapper.map(blog, BlogDTO.class);
    }

    // dto to blog
    public Blog dtoToBlog(BlogDTO blogDTO) {
        return this.modelMapper.map(blogDTO, Blog.class);
    }

}
