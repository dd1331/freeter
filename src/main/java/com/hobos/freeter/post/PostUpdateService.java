package com.hobos.freeter.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUpdateService {
    private final PostRepository postRepository;

    Post update(UpdatePostDto dto) {


        Post post = postRepository.findOneById(dto.getPostId());

        Post updated = post.update(dto);

        postRepository.save(updated);


        return updated;
    }
}
