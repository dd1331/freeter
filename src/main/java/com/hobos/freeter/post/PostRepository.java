package com.hobos.freeter.post;

import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findOneById(Long postId);

    //    @Query("SELECT p, COUNT(c) FROM Post p JOIN p.postCategories pc JOIN p.comments c WHERE pc.category.id = :categoryId AND c.deletedAt IS NULL GROUP BY p")
//    Page<Post> findByPostCategoriesCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
    Page<Post> findByPostCategoriesCategoryIdAndCommentsDeletedAtIsNull(Long categoryId, Pageable pageable);
//	List<Post> findByUserId(Long userId);
}
