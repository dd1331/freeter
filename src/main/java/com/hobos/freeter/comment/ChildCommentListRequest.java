package com.hobos.freeter.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildCommentListRequest {
    Long postId;

    Long parentId;
}
