package com.hobos.freeter.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findOneById(Long postId);
//	List<Post> findByUserId(Long userId);
}
