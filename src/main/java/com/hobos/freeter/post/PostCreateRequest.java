package com.hobos.freeter.post;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostCreateRequest {
    private String title;
    private String content;
    private Long posterId;


}
