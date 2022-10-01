package com.alumni.services;

import com.alumni.dto.BlogDTO;

import java.security.Principal;

public interface BlogService {

    BlogDTO addBlog(BlogDTO blogDTO, Principal principal);

    BlogDTO getSingleBlogById(Long blogId);

    void deleteBlog(Long blogId);

    BlogDTO updateBlog(Long blogId, BlogDTO blogDTO);

}
