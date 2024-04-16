package com.hobos.freeter.post;

import com.hobos.freeter.comment.CommentEntity;
import com.hobos.freeter.common.Likable;
import com.hobos.freeter.member.Member;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Post extends Likable {
    @Id
    @GeneratedValue()
    private Long id;

    @Column()
    private String title;

    @Column()
    private String content;

    //    @Column()
//    private Long posterId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poster_id")
    private Member poster;

    private int commentCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column()
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @Builder.Default
    private List<PostCategory> postCategories = new ArrayList<>();


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();


    void addCategory(Category category) {

        PostCategory postCategory = PostCategory.builder().post(this).category(category).build();
        postCategories.add(postCategory);

    }

    public void addComment(CommentEntity comment) {
        onCommentAdded();
        comments.add(comment);
        comment.addToPost(this);
    }

    Post update(UpdatePostDto dto) {

        this.content = null;
        this.title = null;
        return Post.builder().content(dto.getContent()).title(dto.getTitle()).build();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void onCommentDeleted() {
        this.commentCount--;
    }

    public void onCommentAdded() {
        this.commentCount++;
    }
}




