package com.hobos.freeter.post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePostDto {

    Long postId;

    String content;
    String title;
}
