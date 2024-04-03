package com.hobos.freeter.comment;

import com.hobos.freeter.member.Member;
import com.hobos.freeter.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue()
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commenter_id")
    private Member commenter;

//    @Column(name = "post_id")
//    private Long postId;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;


    public void addToPost(Post post) {
        this.post = post;
    }
}
