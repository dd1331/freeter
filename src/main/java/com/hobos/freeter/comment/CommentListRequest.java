package com.hobos.freeter.comment;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentListRequest {
    Long postId;
}
