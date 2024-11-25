package org.springproject.coffeeshopapp.service;

import org.springframework.data.domain.Page;
import org.springproject.coffeeshopapp.entity.Post;

import java.util.List;

public interface IPostService {
    public Post savePost(Post post);

    public List<Post> getAllPost();

    public Boolean deletePost(int id);

    public Post getPostById(int id);

    public List<Post> getAllSelectPosts(String category);

    public List<Post> searchPost(String ch);

    public Page<Post> getAllPostPagination(Integer pageNo, Integer pageSize, String category);

}
