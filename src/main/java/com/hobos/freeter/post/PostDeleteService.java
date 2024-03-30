package com.hobos.freeter.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostDeleteService {

    private final PostRepository postRepository;

    Post delete(Long postId) {

        Post post = postRepository.findOneById(postId);

        if (post == null) throw new PostNotFoundException();

        post.delete();

        postRepository.save(post);

        return post;


    }
}
