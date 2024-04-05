package com.hobos.freeter.comment;

import com.hobos.freeter.common.Likable;
import com.hobos.freeter.member.Member;
import com.hobos.freeter.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity extends Likable {
    @Id
    @GeneratedValue()
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commenter_id")
    private Member commenter;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CommentEntity> replies = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;


    public void addToPost(Post post) {
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }

    boolean isMine(Optional<Long> memberId) {
        return memberId.map(commenter.getId()::equals).orElse(false);
    }

    void addComment(CommentEntity comment) {
        comment.parent = this;
        replies.add(comment);
    }

}
