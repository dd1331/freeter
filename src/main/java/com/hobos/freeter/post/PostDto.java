package com.hobos.freeter.post;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private String name;

    private int commentCount;

    private int likeCount;

    private List<Long> categoryIds;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.name = post.getPoster().getName();
        this.categoryIds = post.getPostCategories().stream().map(c -> c.getCategory().getId()).toList();
        this.commentCount = post.getCommentCount();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.likeCount = post.getLikeCount();
    }
}