package com.hobos.freeter.comment;

import com.hobos.freeter.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class CommentLike {

    @Id
    @GeneratedValue()
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // ... (생성일, 수정일 등 추가 필드)


    public CommentLike(CommentEntity comment, Member member) {
        this.comment = comment;
        this.member = member;
    }

    // ... (Getter & Setter 메소드)

}

