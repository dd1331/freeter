package com.hobos.freeter.comment;

import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void likeComment(Long commentId, Long memberId) {

        // 댓글 및 사용자 정보 조회
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        // 이미 좋아요 누른 경우 처리
        boolean liked = commentLikeRepository.findByCommentIdAndMemberId(commentId, memberId).isPresent();

        if (liked) unlikeComment(commentId, memberId);


        // 좋아요 정보 저장
        CommentLike commentLike = new CommentLike(comment, member);
        commentLikeRepository.save(commentLike);

        // 댓글 좋아요 개수 증가
        comment.increaseLikeCount();
        commentRepository.save(comment);

    }

    public void unlikeComment(Long commentId, Long memberId) {

        // 좋아요 정보 조회
        CommentLike commentLike = commentLikeRepository.findByCommentIdAndMemberId(commentId, memberId).orElseThrow();

        // 좋아요 정보 삭제
        commentLikeRepository.delete(commentLike);

        // 댓글 좋아요 개수 감소
        CommentEntity comment = commentLike.getComment();
        comment.decreaseLikeCount();
        commentRepository.save(comment);

    }

}
