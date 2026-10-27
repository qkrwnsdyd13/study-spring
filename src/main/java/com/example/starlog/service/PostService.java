package com.example.starlog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.starlog.dto.PostForm;
import com.example.starlog.entity.Post;
import com.example.starlog.entity.User;
import com.example.starlog.repository.PostRepository;
import com.example.starlog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long write(PostForm form, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        Post post = new Post(form.getTitle(), form.getContent(), user);
        Post saved = postRepository.save(post);

        return saved.getId();
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAllByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다"));
    }

    @Transactional
    public Post viewPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다"));
        post.increaseViewCount();      // save() 안 함!
        return post;
    }
}
