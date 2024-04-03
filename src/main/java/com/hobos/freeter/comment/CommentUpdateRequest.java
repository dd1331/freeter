package com.hobos.freeter.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUpdateRequest {

    private Long id;

    private String content;
}
