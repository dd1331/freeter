package com.hobos.freeter.post;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostListDto {

    private List<PostDto> posts;

    private int totalPages;

    public PostListDto(Page<Post> posts) {
        this.posts = posts.stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
        this.totalPages = posts.getTotalPages();
    }
    // Getter and Setter
}