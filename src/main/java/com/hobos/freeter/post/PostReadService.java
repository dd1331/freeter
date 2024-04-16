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

    public PostListDto getPosts(Pageable pageable, PostListRequest dto) {
        Page<Post> posts = postRepository.findByPostCategoriesCategoryIdAndCommentsDeletedAtIsNull(dto.getCategoryId(), pageable);

        return new PostListDto(posts);


    }
}
