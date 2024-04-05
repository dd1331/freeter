package com.hobos.freeter.comment;

import com.hobos.freeter.common.LikeService;
import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService implements LikeService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void like(Long targetId, Long memberId) {

        // 댓글 및 사용자 정보 조회
        CommentEntity comment = commentRepository.findById(targetId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        // 이미 좋아요 누른 경우 처리
        boolean liked = commentLikeRepository.findByCommentIdAndMemberId(targetId, memberId).isPresent();

        if (liked) {
            unlike(targetId, memberId);
            return;
        }


        // 좋아요 정보 저장
        CommentLike commentLike = new CommentLike(comment, member);
        commentLikeRepository.save(commentLike);

        // 댓글 좋아요 개수 증가
        comment.increaseLikeCount();
        commentRepository.save(comment);

    }

    public void unlike(Long targetId, Long memberId) {

        // 좋아요 정보 조회
        CommentLike commentLike = commentLikeRepository.findByCommentIdAndMemberId(targetId, memberId).orElseThrow();

        // 좋아요 정보 삭제
        commentLikeRepository.delete(commentLike);

        // 댓글 좋아요 개수 감소
        CommentEntity comment = commentLike.getComment();
        comment.decreaseLikeCount();
        commentRepository.save(comment);

    }

}
