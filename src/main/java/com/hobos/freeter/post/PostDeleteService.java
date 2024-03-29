package com.hobos.freeter.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostDeleteService {

    private final PostRepository postRepository;

    Post delete(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow();

        post.setDeletedAt(LocalDateTime.now());

        postRepository.save(post);

        return post;


    }
}
