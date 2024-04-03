package com.hobos.freeter.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class CommentResponse {
    public String profileImg;
    private Long id;
    private String content;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isMine;
}
