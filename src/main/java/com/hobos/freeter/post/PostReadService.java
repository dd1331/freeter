package com.hobos.freeter.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReadService {

    private final PostRepository postRepository;

    Post getOne(Long postId) {
        Post found = postRepository.findOneById(postId);
        if (found == null) {
            throw new PostNotFoundException();
        }
        return found;
    }

    public Page<Post> getPosts(Pageable pageable, PostListRequest dto) {


        return postRepository.findByPostCategoriesCategoryId(dto.getCategoryId(), pageable);
    }
}
