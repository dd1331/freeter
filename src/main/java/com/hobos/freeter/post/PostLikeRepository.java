package com.hobos.freeter.post;

import com.hobos.freeter.comment.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPostIdAndMemberId(Long postId, Long memberId);

    int countByPostId(Long postId);
}
