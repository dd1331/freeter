package com.hobos.freeter.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findOneById(Long postId);

    Page<Post> findByPostCategoriesCategoryId(Long categoryId, Pageable pageable);
//	List<Post> findByUserId(Long userId);
}
