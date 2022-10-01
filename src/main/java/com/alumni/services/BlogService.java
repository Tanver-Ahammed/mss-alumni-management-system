package com.alumni.services;

import com.alumni.dto.BlogDTO;

import java.security.Principal;
import java.util.List;

public interface BlogService {

    BlogDTO addBlog(BlogDTO blogDTO, Principal principal);

    List<BlogDTO> getAllBlogs();

    List<BlogDTO> getAllBlogsByAlumni(Long alumniId);

    BlogDTO getSingleBlogById(Long blogId);

    void deleteBlog(Long blogId);

    BlogDTO updateBlog(Long blogId, BlogDTO blogDTO);

}
