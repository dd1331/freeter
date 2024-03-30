package com.hobos.freeter.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCreateService {
    private final PostRepository postRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public Post create(Long userId, PostCreateRequest request) {
        Category category = entityManager.find(Category.class, request.getCategoryId());
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .posterId(userId)
                .build();

        post.addCategory(category);

        postRepository.save(post);

        return post;
    }
}
