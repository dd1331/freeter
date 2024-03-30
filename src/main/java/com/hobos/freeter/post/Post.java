package com.hobos.freeter.post;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Post {
    @Id
    @GeneratedValue()
    private Long id;

    @Column()
    private String title;

    @Column()
    private String content;

    @Column()
    private Long posterId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column()
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @Builder.Default
    private List<PostCategory> postCategories = new ArrayList<>();


    void addCategory(Category category) {

        PostCategory postCategory = PostCategory.builder().post(this).category(category).build();
        postCategories.add(postCategory);

    }

    Post update(UpdatePostDto dto) {

        this.content = null;
        this.title = null;
        return Post.builder().content(dto.getContent()).title(dto.getTitle()).build();
    }
}




