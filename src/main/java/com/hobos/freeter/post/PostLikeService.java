package com.hobos.freeter.post;

import com.hobos.freeter.common.LikeService;
import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService implements LikeService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Override
    public void like(Long targetId, Long memberId) {
        // 댓글 및 사용자 정보 조회
        Post post = postRepository.findById(targetId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        // 이미 좋아요 누른 경우 처리
        boolean liked = postLikeRepository.findByPostIdAndMemberId(targetId, memberId).isPresent();
        System.out.println(liked);

        if (liked) {
            unlike(targetId, memberId);
            return;
        }


        // 좋아요 정보 저장
        PostLike postLike = PostLike.builder().post(post).member(member).build();
        postLikeRepository.save(postLike);

        post.increaseLikeCount();
        postRepository.save(post);


    }

    @Override
    public void unlike(Long targetId, Long memberId) {

        // 좋아요 정보 조회
        PostLike postLike = postLikeRepository.findByPostIdAndMemberId(targetId, memberId).orElseThrow();

        // 좋아요 정보 삭제
        postLikeRepository.delete(postLike);

        // 댓글 좋아요 개수 감소
        Post post = postLike.getPost();
        post.decreaseLikeCount();
        System.out.println(post.getId() + " " + post.getLikeCount());
        postRepository.save(post);


    }
}
