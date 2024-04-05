package com.hobos.freeter.comment;

import com.hobos.freeter.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    // 댓글 ID와 사용자 ID로 좋아요 정보 조회
    Optional<CommentLike> findByCommentIdAndMemberId(Long commentId, Long memberId);

    // 댓글 ID로 좋아요 개수 조회
    int countByCommentId(Long commentId);

}
