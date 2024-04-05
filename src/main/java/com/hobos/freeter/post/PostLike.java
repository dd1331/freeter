package com.hobos.freeter.post;

import com.hobos.freeter.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue()
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // ... (생성일, 수정일 등 추가 필드)

}
