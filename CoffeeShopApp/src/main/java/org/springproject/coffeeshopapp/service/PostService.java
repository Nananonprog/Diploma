package org.springproject.coffeeshopapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springproject.coffeeshopapp.entity.Post;
import org.springproject.coffeeshopapp.repository.PostRepository;

import java.util.List;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Boolean deletePost(int id) {
        Post post = postRepository.findById(id).orElse(null);
        if(post != null) {
            postRepository.delete(post);
            return true;
        }
        return false;
    }

    @Override
    public Post getPostById(int id) {
        Post post = postRepository.findById(id).orElse(null);
        return post;
    }

    @Override
    public List<Post> getAllSelectPosts(String category) {
        List<Post> posts = null;
        if(ObjectUtils.isEmpty(category)){
            posts = postRepository.findAll();
        } else{
            posts = postRepository.findByCategory(category);
        }
        return posts;
    }

    @Override
    public List<Post> searchPost(String ch) {
        return postRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch,ch);
    }

    @Override
    public Page<Post> getAllPostPagination(Integer pageNo, Integer pageSize, String category) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> pagePosts = null;

        if(ObjectUtils.isEmpty(category)){
            pagePosts = postRepository.findAll(pageable);
        }else{
            pagePosts = postRepository.findByCategory(pageable, category);
        }
        return pagePosts;
    }
}
