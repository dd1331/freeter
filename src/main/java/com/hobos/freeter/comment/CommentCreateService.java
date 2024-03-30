package com.hobos.freeter.comment;

import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.MemberNotFoundException;
import com.hobos.freeter.member.MemberRepository;
import com.hobos.freeter.post.Post;
import com.hobos.freeter.post.PostNotFoundException;
import com.hobos.freeter.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCreateService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentEntity createComment(Long postId, String content, Long memberId) {
        // 1. 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        Member commenter = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        CommentEntity comment = CommentEntity.builder().content(content).commenter(commenter).build();

        // 3. 생성된 댓글을 게시글에 추가
        post.addComment(comment);

        // 4. 게시글 저장 (댓글은 CascadeType.ALL로 인해 자동으로 저장됨)
        postRepository.save(post);


        return comment;
    }
}
