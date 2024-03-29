package com.hobos.freeter.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    Post update(UpdatePostDto dto) {

        this.content = null;
        this.title = null;
        return Post.builder().content(dto.getContent()).title(dto.getTitle()).build();
    }
}




