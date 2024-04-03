package com.hobos.freeter.comment;

import com.hobos.freeter.post.PostListRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReadService {
    private final CommentRepository commentRepository;

    public List<CommentEntity> getPostComments(Pageable pageable, CommentListRequest dto) {

        return commentRepository.findByPostId(pageable, dto.getPostId());


    }
}
